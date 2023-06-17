package com.cy.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.cy.app.pojo.Cres;
import com.cy.app.pojo.Result;
import com.cy.app.pojo.Users;
import com.cy.app.service.CresService;
import com.cy.app.service.UserService;
import com.cy.app.utils.*;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 用户请求的接口：
 * 1.首页请求，获取其他用户信息
 * 2/3.附近的人请求，通过坐标获取附近的人，包括根据条件筛选附近的人
 * 4.消息页的请求，通过好友字段，获取好友信息
 * 5.个人中心请求，通过JWT判断用户是否为新用户，如果是，则返回请求失败，否则就返回用户信息。
 * 6. 资料更新请求，将前端传输过来的资料进行更新。
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CresService cresService;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @GetMapping
    public Result users(){
        List<Users> usersList = userService.getUser();
        if(usersList != null){
            return Result.success(usersList);
        }else{
            return Result.error("Data Empty");
        }
    }
    
    @GetMapping("/vic")
    public Result vicinity(HttpServletRequest req, Integer distance) {
        // 获取jwt，并取出保存其中的电话号码。
        String token = req.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        String phoneNum = claims.get("phoneNum", String.class);

        // 输出电话号码
        log.info("Phone number is : {}",claims.get("phoneNum", String.class));
        log.info("Distance is : {}",distance);

        // 通过电话号码，用user接口，查询数据库中对应的用户。
        Users userNum = userService.getSingleUser(phoneNum);

        // 将数据库中，所有的用户取出（因为现在还没有解决，如何在查数据库时就将定位缩小到5000m）
        List<Users> usersList= userService.getUser();

        // 调用userScreen（筛查）类中的方法，getDistance，将当前发起请求的用户,所有用户,距离一起传入函数。
        List<Users> users_Screen = usersScreen.getDistance(userNum, usersList, distance);

        // 如果返回的user列表长度为0，证明该用户附近没有使用改app的人
        if(users_Screen.size() > 0){
            return Result.success(users_Screen);
        }
        return Result.error("Condition error!");
    }

    // 根据条件筛查附近的人
    @PostMapping("/screen")
    public Result screen(HttpServletRequest req) throws IOException {
        // 通过hsr_To_br将req对象转为json对象，并从中将需要的数据取出
        JSONObject jsonObject = hsr_To_br.convert(req);
        Integer gender = Integer.valueOf(jsonObject.getString("gender"));
        Integer ageMax = Integer.valueOf(jsonObject.getString("ageMax"));
        Integer ageMin = Integer.valueOf(jsonObject.getString("ageMin"));
        Integer distance = Integer.valueOf(jsonObject.getString("distance"));
        Integer salaryMax = Integer.valueOf(jsonObject.getString("salaryMax"));
        Integer salaryMin = Integer.valueOf(jsonObject.getString("salaryMin"));
        log.info("The received data is :{}, {}, {}, {}, {}, {}",gender, ageMax, ageMin, distance, salaryMax, salaryMin);

        // 解析JWT，并拿到电话号码和该用户的特权等级
        String token = req.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer privilege = claims.get("privilege", Integer.class);
        String phoneNum = claims.get("phoneNum", String.class);
        log.info("Claims is : {}",claims.get("phoneNum", String.class));

        // 去数据库，根据筛查条件，将符合的用户拿到
        List<Users> usersList = userService.screen(ageMax, ageMin, salaryMax, salaryMin, privilege);
        log.info("Member : {}",usersList.size());

        // 只要有一个就进行比对
        if(usersList != null){
            // 拿到当前用户的信息
            Users userNum = userService.getSingleUser(phoneNum);
            log.info("Source user: {}",userNum);
            if(userNum != null){
                List<Users> users_Screen = new ArrayList<>();
                // 对数据库筛选出来的用户，进行第二次筛选，去掉不符合性别条件的用户
                users_Screen = usersScreen.screenGender(usersList, gender.shortValue());
                // 然后在第三次筛选，去掉和当前用户相距过远的其他用户
                users_Screen = usersScreen.getDistance(userNum, users_Screen, distance);
                // 最后返回符合条件的用户列表
                return Result.success(users_Screen);
            }else{
                return Result.error("Screen Failed");
            }
        }else{
            return Result.error("Screen Failed");
        }
    }

    // 消息接口，该接口因为还没有捋清逻辑，所以暂时没有做过多开发
    @GetMapping("/msg")
    public Result message(HttpServletRequest req){
        String token = req.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        String phoneNum = claims.get("phoneNum", String.class);
        Cres res = cresService.queryByTel(phoneNum);
        List<Users> usersList = userService.getUsers(res.getFriendList());
        log.info("Phone Number is : {}",usersList);
        if(usersList != null){
            return Result.success(usersList);
        }else{
            return Result.error("Friend list is Empty");
        }
    }


    // 个人资料接口
    @GetMapping("/personal")
    public Result personal(HttpServletRequest req){
        // 获取JWT令牌
        String token = req.getHeader("token");
        log.info("token: {}", token);

        // 如果JWT令牌为空，则表明该用户还没有注册，返回false
        if(token == null || token == ""){
            return Result.error("This is a new user");
        }else{
            // 解析JWT，如果clamis为空，则表明JWT为空，或者出错
            Claims claims = JwtUtils.parseJWT(token);
            if(claims == null){
                return Result.error("Jwt is Error");
            }else{
                // 拿到电话号码，去user、cres数据库调信息
                // cres保存的是每个用户的好友列表
                String phoneNum = claims.get("phoneNum", String.class);
                Users users = userService.getSingleUser(phoneNum);
                Cres cres = cresService.queryByTel(phoneNum);
                // 如果cres对象为空，则表明改用户没有好友，否则返回cres对象的长度
                if(cres.getFriendList() != null){
                    Integer friendNum = cres.getFriendList().split(",").length;
                    cres.setFriendList(friendNum.toString());

                }else{
                    cres.setFriendList("0");
                }
                // 创建一个新的对象list，保存cres和users对象，返回到前端。
                List<Object> list = new ArrayList<>();
                list.add(users);
                list.add(cres);
                return Result.success(list);
            }

        }
    }


    // 资料更新接口
    @PostMapping("/update")
    public Result update(HttpServletRequest req) throws IOException {

        // 转换req对象的类型
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) req);
        // 并将里面保存的所有数据，和key用一个map对象封装起来
        String[] key = {"phoneNum", "nickname", "birthday", "gender", "weight", "heigth", "hobby", "education", "marry", "salary"};
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < key.length; i++){
            map.put(key[i], params.getParameter(key[i]));
        }
        log.info("User : {}", map);

        // 然后再将user对象中的数据更新为map
        Users users = new Users();
        users.setPhoneNum(map.get("phoneNum"));
        users.setNickname(map.get("nickname"));
        users.setBirthday(map.get("birthday"));
        Integer age = calAge.getAgeByBirth(map.get("birthday"));
        users.setAge(age);
        users.setGender(Short.valueOf(map.get("gender")));
        users.setWeight(map.get("weight"));
        users.setHeigth(map.get("heigth"));
        users.setHobby(map.get("hobby"));
        users.setEducation(map.get("education"));
        users.setMarry(map.get("marry"));
        users.setSalary(Integer.valueOf(map.get("salary")));

        // 判断前端是否对头像进行了更新，如果avatarSrc为空，或者类型为html，则表明本次资料修改没有涉及头像
        MultipartFile avatarSrc = params.getFile("avatarSrc");
        if(avatarSrc != null && params.getFile("avatarSrc").getContentType().split("/")[1] == "jpeg"){
            // 通过阿里云，对象oss服务，将头像存储在云端。并返回该头像的访问地址（url），将改地址保存到user中
            String avatar_src = aliOSSUtils.upload(avatarSrc);
            users.setAvatarSrc(avatar_src);

        }
        log.info("Users : {}", users);
        // 更新数据库
        Integer sign = userService.update(users);
        if(sign == 1){
            return Result.success("Update Success");
        }else{
            return Result.error("Update Failed");
        }
    }
}

