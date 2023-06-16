package com.cy.app.mapper;

import com.cy.app.pojo.Users;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 用户主表的数据库控制语句
 */
@Mapper
public interface UserMapper {
    @Select("select * from users ")
    List<Users> getUser();
    @Select("select phoneNum, nickname, avatar_src from users where find_in_set(phoneNum, #{friendList})")
    List<Users> getUsers(String friendList);
    @Select("select  phoneNum, username, nickname, gender, birthday, age, avatar_src, weight, heigth, hobby, education, marry, address, salary, privilege, coordinate_long, coordinate_lat from users where phoneNum=#{phoneNum}")
    Users getSingleUser(String phoneNum);

    Integer update(Users users);

    @Select("select nickname, age, gender, coordinate_lat, coordinate_long from users where (age >= #{ageMin} and age < #{ageMax}) or (salary>=#{salaryMin} and salary < #{salaryMax}) and privilege=#{privilege}")
    List<Users> screen(Integer ageMax, Integer ageMin, Integer salaryMax, Integer salaryMin, Integer privilege);

    @Insert("insert into users (phoneNum, password, nickname, birthday, age,avatar_src,gender, coordinate_lat, coordinate_long, update_time, create_time)\n" +
            "select phoneNum, password, nickname, birthday, age,avatar_src,gender, coordinate_lat, coordinate_long,update_time, create_time from telephone where phoneNum=#{phoneNum};")
    void synchronizeToMain(String phoneNum);



}
