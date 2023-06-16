package com.cy.app.controller;

import com.cy.app.mapper.CresMapper;
import com.cy.app.pojo.Result;
import com.cy.app.pojo.Users;
import com.cy.app.service.CresService;
import com.cy.app.service.TelService;
import com.cy.app.service.UserService;
import com.cy.app.utils.AliOSSUtils;
import com.cy.app.utils.calAge;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件接口
 */

@Slf4j
@RestController
public class UploadController {

    // 注入接口
    @Autowired
    private TelService telService;
    @Autowired
    private UserService userService;
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Autowired
    private CresService cresService;

    @PostMapping("/upload")
    public Result upload(HttpServletRequest req) throws Exception {

        // 转换req对象的类型
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) req);
        String[] key = {"phoneNum", "nickname", "birthday", "gender", "coordinateLat", "coordinateLong"};
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < key.length; i++){
            map.put(key[i], params.getParameter(key[i]));
        }
        log.info("Map : {}", map);

        // 将封装好的map对象，存入到user对象
        Users users = new Users();
        users.setPhoneNum(map.get("phoneNum"));
        users.setNickname(map.get("nickname"));
        users.setBirthday(map.get("birthday"));
        Integer age = calAge.getAgeByBirth(map.get("birthday"));
        users.setAge(age);
        users.setGender(Short.valueOf(map.get("gender")));
        users.setCoordinateLat(Double.valueOf(map.get("coordinateLat")));
        users.setCoordinateLong(Double.valueOf(map.get("coordinateLong")));
//        log.info("Users : {}", users);

        // 通过阿里云，对象oss服务获取头像的url，然后存入user
        MultipartFile image = params.getFile("image");
        String avatar_src = aliOSSUtils.upload(image);
        users.setAvatarSrc(avatar_src);

        // 更新数据库，删除验证码，并将表telephone的数据存入到主表users当中，同时为改电话号码初始化一个好友列表
        telService.upload(users);
        telService.del_vericode(users.getPhoneNum());
        userService.synchronizeToMain(users.getPhoneNum());
        cresService.createFriendList(users.getPhoneNum());

        return Result.success("OK");
    }
}
