package com.cy.app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定义用户的后端对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Integer id;
    private String phoneNum;
    private String username;
    private String password;
    private String nickname;
    private Short gender;
    private String birthday;
    private Integer age;
    private String avatarSrc;
    private String weight;
    private String heigth;
    private String hobby;
    private String education;
    private String marry;
    private String address;
    private Integer salary;
    private Double coordinateLat;
    private Double coordinateLong;
    private Integer privilege;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
