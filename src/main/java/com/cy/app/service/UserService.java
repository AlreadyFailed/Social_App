package com.cy.app.service;

import com.cy.app.pojo.PageBean;
import com.cy.app.pojo.Users;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 关于用户表的数据库接口
 */
public interface UserService {
    List<Users> getUser();
    List<Users> getUsers(String friendList);
    Users getSingleUser(String phoneNum);
    Integer update(Users users);
    List<Users> screen(Integer ageMax, Integer ageMin, Integer salaryMax, Integer salaryMin, Integer privilege);
    void synchronizeToMain(String phoneNum);

}
