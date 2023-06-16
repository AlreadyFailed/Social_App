package com.cy.app.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cy.app.pojo.Telphone;
import com.cy.app.service.TelService;
import com.cy.app.pojo.Result;
import com.cy.app.service.UserService;
import com.cy.app.utils.JwtUtils;
import com.cy.app.utils.SmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 关于登陆的接口：
 * 1.登陆请求，通过电话和密码，判断是否已经注册过
 * 2.发送验证码请求，根据电话使用阿里云的短信服务，向该手机号发送短信验证码（目前为测试阶段，阿里云服务没有充值，所以验证码还是只保存在数据库）
 * 3.注册请求，当验证码正确且处于有效时限内，则讲该用户保存到数据库，同时录入关键信息
 */
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private TelService telService;
    @Autowired
    private UserService userService;

//    @Log
//    @PostMapping("/login")
//    public Result login(@RequestBody Telphone tel){
//        log.info("User login: {}", tel);
//        telService.generateVerifyCode(tel);
//        Telphone telnum = telService.login(tel);
//        if(telnum != null){
////            Map<String, Object> claims = new HashMap<>();
////            claims.put("id", telnum.getId());
////            claims.put("telephone", telnum.getTelephone());
////            String jwt = JwtUtils.generateJwt(claims);
//            return Result.success(telnum.getVerifyCode());
////            return Result.success(jwt);
//        }
//        return Result.error("输入ID有误");
//    }

//    @PostMapping("/login")
//    public Result sign_up(@RequestBody Telphone phoneNum){
//        log.info("Sign up Telephone number is : {}", phoneNum.getPhoneNum());
//        Telphone telnum = telService.query(phoneNum);
//        if(telnum == null){
//            telService.signUp(phoneNum);
//            telService.genVerifyCode(phoneNum);
//            telnum = telService.query(phoneNum);
//            return Result.success(telnum);
//        }else{
//            telService.genVerifyCode(phoneNum);
//            return Result.success(telnum);
//        }
//
//    }


    // 登录接口，前端会传入两个参数，分别是电话号码和密码
    @PostMapping("/signin")
    public Result sign_in(@RequestBody Telphone phoneNum){
        // 判断电话号码和密码是否为空（其实前端已经判断过一次，以防万一后端也对其进行简单判断）
        if(phoneNum.getPhoneNum().length() != 11 || phoneNum.getPassword().length() <= 0){
            log.info("Phone Number or Password is error ");
            return Result.error("Phone Number or Password is Error");
        }else{
            log.info("Sign in Telephone number is : {}", phoneNum.getPhoneNum());
            // 根据电话号码去数据库拿到该用户的信息。
            Telphone telnum = telService.query(phoneNum);
            // 如果返回对象为空，则该用户不存在
            if(telnum != null){
                // 对密码进行校验，如果为真（输入密码和数据库密码相等），则更新JWT令牌，里面包含该用户的电话号码和特权等级
                if(telnum.getPassword().equals(phoneNum.getPassword())){
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("phoneNum", telnum.getPhoneNum());
                    claims.put("privilege", telnum.getPrivilege());
                    String jwt = JwtUtils.generateJwt(claims);
                    return Result.success(jwt);
                }
                // Password incorrect
                else{
                    return Result.error("Username or Password is Error");
                }
            }
            // Custom isn't exist
            else{
                return Result.error("Username or Password is error");
            }

        }
    }

    // 发送验证码接口，该接口会使用到阿里云的短信服务
    @PostMapping("/signup/send")
    public Result sendCode(@RequestBody Telphone phoneNum){
        log.info("Phone number is : {}", phoneNum.getPhoneNum());
        // 生成验证码
        String str = "0123456789";
        Random random = new Random();
        StringBuffer vericode = new StringBuffer();
        for(int i = 0; i < 6; i++){
            int number = random.nextInt(10);
            vericode.append(str.charAt(number));
        }
        String vericodeNew = vericode.toString();

        // 使用短信服务，将手机号和验证码传入
        SendSmsResponse sendSmsResponse = SmsUtil.toSendMes(phoneNum.getPhoneNum(), vericodeNew);
        log.info("The vericode is {}",vericode);

        // 因为并没有购买短信验证发送条数，所以这里会用取反的条件，使验证码能一直成功保存。
        if(!(sendSmsResponse.getCode() != "OK")){
            return Result.error(sendSmsResponse.getMessage());
        }else{
            Telphone telnum = telService.query(phoneNum);
            if(telnum != null){
                // 保存验证码
                telService.save_vericode(phoneNum.getPhoneNum(), vericodeNew);
            }else{
                // 如果是重复发送验证码，则更新验证码。
                phoneNum.setVeriCode(vericodeNew);
                telService.save_TelCode(phoneNum);
            }
            return Result.success("OK");
        }
    }

    // 注册接口，该接口有三个参数：电话号码，密码，验证码
    @PostMapping("/signup")
    public Result sign_up(@RequestBody Telphone phoneNum){
        Telphone telnum = telService.query(phoneNum);
        // 判断前端输入的验证码和后端保存的验证码是否一致
        boolean judge = telnum.getVeriCode().equals(phoneNum.getVeriCode());
        log.info("The result of compare verify code is : {}", judge);
        if(judge && telnum.getVeriCode()!= null){
            // 保存密码，并生成jwt令牌
            telService.signUp(phoneNum);
            Map<String, Object> claims = new HashMap<>();
            claims.put("phoneNum", telnum.getPhoneNum());
            claims.put("privilege", telnum.getPrivilege());
            log.info("Privilege is : {}",telnum.getPrivilege());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }else{
            return Result.error("Verify code is Error");
        }

    }

}
