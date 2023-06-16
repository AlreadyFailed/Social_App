package com.cy.app.service.impl;

import com.cy.app.mapper.CresMapper;
import com.cy.app.pojo.Cres;
import com.cy.app.service.CresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 好友列表数据库接口的功能实现
 */
@Service
public class CresServiceImpl implements CresService {
    @Autowired
    private CresMapper cresMapper;
    @Override
    public Cres queryByTel(String phoneNum) {
        Cres cres = cresMapper.queryByTel(phoneNum);
        return cres;
    }

    @Override
    public void createFriendList(String phoneNum) {
        cresMapper.createFriendList(phoneNum);
    }
}
