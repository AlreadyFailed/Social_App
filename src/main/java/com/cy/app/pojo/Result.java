package com.cy.app.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  定义返回值的后端对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        return new Result(1, "success", null);
    }

    public static Result success(Object data){
        return  new Result(1, "success", data);
    }

    public static Result error(String msg){
        return new Result(0, "error", msg);
    }
}
