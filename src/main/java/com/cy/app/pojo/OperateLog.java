package com.cy.app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 定义监控日志的后端对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog {
    private Integer id;
    private String operateUser;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private String returnValue;
    private Long costTime;
}
