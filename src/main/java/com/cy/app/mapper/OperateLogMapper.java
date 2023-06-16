package com.cy.app.mapper;

import com.cy.app.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 监控日志的数据库语句
 */
@Mapper
public interface OperateLogMapper {
    @Insert("insert into operate_log(operate_user, operate_time, class_name, method_name, return_value, cost_time) " +
            "values (#{operateUser}, #{operateTime}, #{className}, #{methodName}, #{returnValue}, #{costTime})")
    void insert(OperateLog operateLog);
}
