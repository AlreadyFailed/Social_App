package com.cy.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *  工具类： 根据String类型的生日，计算出年龄，并返回
 */

public class calAge {

    /**
     *
     * @param birthday      字符串类型的生日
     * @return              返回Integer类型的年龄
     */
    public static Integer getAgeByBirth(String birthday){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = dateFormat.parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        获取当前时间
        Calendar cal = Calendar.getInstance();

//        获取出生日期的Calendar对象
        Calendar bir = Calendar.getInstance();
        bir.setTime(date);

        if(cal.before(birthday)){
            return 0;
        }

//        取出当前年月日
        Integer nowYear = cal.get(Calendar.YEAR);
        Integer nowMonth = cal.get(Calendar.MONTH);
        Integer nowDay = cal.get(Calendar.DAY_OF_MONTH);

//        取出出生日期的年月日
        Integer birthYear = bir.get(Calendar.YEAR);
        Integer birthMonth = bir.get(Calendar.MONTH);
        Integer birthDay = bir.get(Calendar.DAY_OF_MONTH);

        // 对是否满周岁进行计算
        Integer age = nowYear - birthYear;
        if(nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)){
            age -- ;
        }
        return age;
    }
}
