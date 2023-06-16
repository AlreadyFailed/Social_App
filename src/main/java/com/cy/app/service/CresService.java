package com.cy.app.service;

import com.cy.app.pojo.Cres;

/**
 * 关于好友列表的数据库接口
 */
public interface CresService {
    Cres queryByTel(String phoneNum);

    void createFriendList(String phoneNum);
}
