package com.cy.app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 定义临时表的后端对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telphone {
    private Integer id;
    private String phoneNum;
    private String username;
    private String password;
    private String nickname;
    private Short gender;
    private String birthday;
    private Integer age;
    private String avatarSrc;
    private Double coordinate_lat;
    private Double coordinate_long;
    private String veriCode;
    private Short privilege;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
