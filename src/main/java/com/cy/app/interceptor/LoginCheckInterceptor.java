package com.cy.app.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cy.app.pojo.Result;
import com.cy.app.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器的功能实现
 */
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    // 在接口进行工作之前，开始拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle....");

        // 查看本次请求的来源（url）
        String url = request.getRequestURL().toString();
        log.info("Request url :{}", url);

        // 如果该来源中包含signin或者signup，判断为登陆和注册请求，放行
        if(url.contains("signin") || url.contains("signup")){
            log.info("SignIn or SignUp operate, Pass");
            return true;
        }

        // 如果不是登录或者注册请求，则对其JWT进行解析，确认其jwt的有效时间
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            log.info("Token is empty, please login...");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }


        try{
            // JWT有效，则放行，否则拦截
            JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("Parse Error");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        log.info("Token is legal");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
