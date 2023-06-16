package com.cy.app.service.impl;

import com.cy.app.mapper.UserMapper;
import com.cy.app.pojo.PageBean;
import com.cy.app.pojo.Users;
import com.cy.app.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户表数据库接口的功能实现
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Users> getUser() {
        List<Users> usersList = userMapper.getUser();
        return usersList;
    }

    @Override
    public List<Users> getUsers(String friendList) {
        List<Users> usersList = userMapper.getUsers(friendList);
        return usersList;
    }

    @Override
    public Users getSingleUser(String phoneNum) {
        Users users = userMapper.getSingleUser(phoneNum);
        return users;
    }


    @Override
    public Integer update(Users users) {
        users.setUpdateTime(LocalDateTime.now());
        Integer sign = userMapper.update(users);
        return sign;
    }

    @Override
    public List<Users> screen(Integer ageMax, Integer ageMin, Integer salaryMax, Integer salaryMin, Integer privilege) {
        List<Users> usersList = userMapper.screen(ageMax, ageMin, salaryMax, salaryMin, privilege);
        return usersList;
    }

    @Override
    public void synchronizeToMain(String phoneNum) {
        userMapper.synchronizeToMain(phoneNum);
    }
}
