package com.cy.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.cy.app.pojo.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  工具类： 转换req对象的类型
 *  但是经过后面的探索，发现其实将其转换为MultipartHttpServletRequest类型其实也可行
 */
public class hsr_To_br {

    /**
     *
     * @param req   前端传来的数据包
     * @return      返回该数据包的json对象
     * @throws IOException
     */

    public static JSONObject convert(HttpServletRequest req) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            // Create StringBuffer object sb
            StringBuffer sb = new StringBuffer();
            String s = null;
            // Add all data to SB
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

            // Save each data separatly
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            return jsonObject;
        }catch (IOException e){
            // Throw Exception
            e.printStackTrace();
            System.out.println("Bad");
            return null;
        }
    }
}
