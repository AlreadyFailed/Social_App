package com.cy.app.mapper;

import com.cy.app.pojo.Cres;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 好友列表的数据库控制语句
 */
@Mapper
public interface CresMapper {
    @Select("select * from cres where phoneNum=#{phoneNum}")
    Cres queryByTel(String phoneNum);

    @Insert("insert into cres (phoneNum,create_time,update_time) values (#{phoneNum}, now(), now())")
    void createFriendList(String phoneNum);
}
