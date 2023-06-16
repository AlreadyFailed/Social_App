package com.cy.app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 定义好友列表的后端对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cres {
    private Integer id;
    private String phoneNum;
    private String friendList;
    private Integer care;
    private Integer fans;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
