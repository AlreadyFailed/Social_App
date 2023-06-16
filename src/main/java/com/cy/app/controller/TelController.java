package com.cy.app.controller;

import com.cy.app.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *  测试类，暂时没有功效
 */
@Slf4j
@RestController
public class TelController {


    @GetMapping("/get")
    public Result test(){

        return Result.success("Test Success");
    }

}
