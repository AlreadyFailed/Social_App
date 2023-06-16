package com.cy.app.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Description 短信发送服务
 * @Author 小乌龟
 * @Date 2022/9/12 20:19
 */
//@Component
public class SmsUtil {


    public static SendSmsResponse toSendMes(String phoneNumber, String code) {
        //1.连接阿里云
        /**
         "<your-region-id>",           // The region ID 地区标识
         "<your-access-key-id>",       // The AccessKey ID of the RAM account RAM账户的AccessKey ID 阿里云账号可查
         "<your-access-key-secret>",   // The AccessKey Secret of the RAM account RAM 账户的 AccessKey Secret 阿里云账号可查
         **/

        String accessKeyId = "LTAI5tRgdDRon9SzRezSuEZj";
        String accessKeySecret = "atq9i4lVVF27NHafWM8rilIwYxtGqr";
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);

        IAcsClient client = new DefaultAcsClient(profile);
        //2.构建请求 自定义参数
        SendSmsRequest request = new SendSmsRequest();
        //接收短信的手机号码
        request.setPhoneNumbers(phoneNumber);
        //短信签名名称
        request.setSignName("黑与白");
        //短信模板CODE
        request.setTemplateCode("SMS_460970264");
        //模版内容:您正在使用阿里云短信测试服务，体验验证码是：${code}，如非本人操作，请忽略本短信！
        //短信模板变量对应的实际值
        //("{\"code\":\"1234\"}");
        HashMap<String,String> param=new HashMap<>();
        param.put("code",code);
        request.setTemplateParam(JSONObject.toJSONString(param));
        SendSmsResponse response = new SendSmsResponse();
        try {
            //3.发送请求
            response = client.getAcsResponse(request);
//            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        //短信成功返回json {  "RequestId": "614048FB-0619-4439-A1D5-AA8B218A****",  "Message": "OK",  "BizId": "386715418801811068^0",  "Code": "OK"}
        return response;
    }
}
