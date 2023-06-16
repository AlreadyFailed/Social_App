package com.cy.app.aop;

import com.alibaba.fastjson.JSONObject;
import com.cy.app.mapper.OperateLogMapper;
import com.cy.app.pojo.OperateLog;
import com.cy.app.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


/**
 * Log 实现
 */
@Slf4j
@Component
//@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.cy.app.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
//        String jwt = request.getHeader("token");
//        Claims claims = JwtUtils.parseJWT(jwt);

//        String operateUser = (String) claims.get("id");
        // 记录操作时间
        LocalDateTime operateTime = LocalDateTime.now();

        // 操作的类名
        String className = joinPoint.getTarget().getClass().getName();

        // 操作的方法名
        String methodName = joinPoint.getSignature().getName();

        // 生成一个数组
        Object[] args = joinPoint.getArgs();

        // 计算整个操作的花费时间
        Long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long end = System.currentTimeMillis();
        Long costTime = end - begin;

        // 接口的返回值
        String returnValue = JSONObject.toJSONString(result);

        // 将所有的记录存入数据库
        OperateLog operateLog = new OperateLog(null, null, operateTime, className, methodName, returnValue, costTime);
        operateLogMapper.insert(operateLog);
        log.info("AOP operate log :{}", operateLog);
        return result;
    }
}
