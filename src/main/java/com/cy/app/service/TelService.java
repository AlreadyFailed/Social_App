package com.cy.app.service;

import com.cy.app.pojo.Telphone;
import com.cy.app.pojo.Users;

/**
 * 关于临时表的数据库接口
 */
public interface TelService {
    Telphone login(Telphone tel);

    Telphone query(Telphone tel);


    int signUp(Telphone phoneNum);

    int querySingle(String phoneNum);

    void save_vericode(String phoneNum,String vericode);

    void save_TelCode(Telphone phoneNum);

    void del_vericode(String phoneNum);

    void upload(Users users);

}
