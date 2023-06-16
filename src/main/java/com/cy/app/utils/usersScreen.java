package com.cy.app.utils;

import com.cy.app.pojo.Users;
import lombok.extern.slf4j.Slf4j;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.ArrayList;
import java.util.List;


/**
 *  工具类：筛选性别和距离
 */
@Slf4j
public class usersScreen {

    /**
     *
     * @param usersList 第一次从数据库筛选出来的用户列表
     * @param gender    所需要的性别
     * @return          筛选过后的新列表
     */
    public static List<Users> screenGender(List<Users> usersList, Short gender){
        List<Users> newUserslist = new ArrayList<>();
        for(int i = 0; i < usersList.size(); i ++){
            if(usersList.get(i).getGender() == gender){
                newUserslist.add(usersList.get(i));
            }
        }
        return newUserslist;
    }

    /**
     *
     * @param userFrom      当前用户
     * @param usersList     经过第一次筛选的用户列表
     * @param dis_range     所需要的范围
     * @return              筛选过后的新列表
     */

    public static List<Users> getDistance(Users userFrom, List<Users> usersList, Integer dis_range) {
        List<Users> newUserslist = new ArrayList<>();
        for(int i = 0; i < usersList.size(); i ++){
            Double dis_FTT = calDistance.getDistance(userFrom.getCoordinateLong(), userFrom.getCoordinateLat(), usersList.get(i).getCoordinateLong(), usersList.get(i).getCoordinateLat());
            log.info("From Source location to destination laction distance is : {} m",dis_FTT.intValue());
            if(dis_FTT.intValue() < dis_range){
                newUserslist.add(usersList.get(i));
            }
        }
        return newUserslist;
    }
}
