package com.cy.app.exception;

import com.cy.app.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 对全局的异常进行监控
 */
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("Sorry, Operation Failed, Please contact administrator");
    }
}
