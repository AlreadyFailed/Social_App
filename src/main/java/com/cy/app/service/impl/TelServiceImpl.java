package com.cy.app.service.impl;

import com.cy.app.mapper.TelMapper;
import com.cy.app.pojo.Telphone;
import com.cy.app.pojo.Users;
import com.cy.app.service.TelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 临时表数据库接口的功能实现
 */

@Service
public class TelServiceImpl implements TelService {
    @Autowired
    private TelMapper telMapper;

    @Override
    public Telphone login(Telphone tel) {
        return telMapper.select(tel);
    }


    @Override
    public Telphone query(Telphone tel) {
        return telMapper.query(tel);
    }

    @Override
    public int querySingle(String phoneNum) {
        return telMapper.querySingle(phoneNum);
    }

    @Override
    public void save_vericode(String phoneNum, String vericode) {
        telMapper.save_vericode(phoneNum, vericode);
    }

    @Override
    public void save_TelCode(Telphone phoneNum) {
        telMapper.save_TelCode(phoneNum);
    }

    @Override
    public void del_vericode(String phoneNum) {
        telMapper.del_vericode(phoneNum);
    }

    @Override
    public int signUp(Telphone phoneNum) {
        return telMapper.signup(phoneNum);
    }

    @Override
    public void upload(Users users) {
        telMapper.upload(users);
    }

}
