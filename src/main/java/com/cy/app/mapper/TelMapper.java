package com.cy.app.mapper;

import com.cy.app.pojo.Telphone;
import com.cy.app.pojo.Users;
import org.apache.ibatis.annotations.*;


/**
 * 临时的数据保存语句，该接口用于第一次保存用户数据：电话，密码，头像，昵称
 * 当用户注册成功以后，会将该表的数据转存至主表。
 */
@Mapper
public interface TelMapper {
    @Select("select * from telephone where phoneNum=#{phoneNum};")
    Telphone select(Telphone tel);

    @Update("update telephone set vericode=#{code};")
    void update(Telphone tel, String code);

    @Update("update telephone set vericode=#{code}  where phoneNum=#{phone};")
    void update_ver(Telphone tel, String code, String phone);

    @Select("select phoneNum,password, privilege, veriCode from telephone where phoneNum=#{phoneNum};")
    Telphone query(Telphone tel);

    @Update("update telephone set password = #{password} where phoneNum=#{phoneNum};")
    int signup(Telphone phoneNum);

    @Select("select * from telephone where phoneNum=#{phoneNum};")
    int querySingle(String phoneNum);

    @Update("update telephone set veriCode=#{vericode} where phoneNum=#{phoneNum};")
    void save_vericode(String phoneNum, String vericode);

    @Insert("insert into telephone (phoneNum, veriCode, create_time, update_time) values (#{phoneNum}, #{veriCode}, now(), now());")
    void save_TelCode(Telphone phoneNum);

    @Update("update telephone set veriCode=null where phoneNum=#{phoneNum};")
    void del_vericode(String phoneNum);

    @Update("update  telephone set nickname=#{nickname}, gender=#{gender}, birthday=#{birthday}, age=#{age}, avatar_src=#{avatarSrc}, coordinate_lat=#{coordinateLat}, coordinate_long=#{coordinateLong} where phoneNum=#{phoneNum};")
    void upload(Users users);
}
