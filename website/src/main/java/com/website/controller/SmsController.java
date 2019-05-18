package com.website.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.website.dao.UserDao;
import com.website.domain.User;
import com.website.util.Result;
import com.website.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @email :459114302@qq.com
 * @author: Zhu Liangfu
 * @date: 2019/5/5
 * @time: 15:33
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("sms")
public class SmsController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDao userdao;

    @RequestMapping("/sendSms")
    public Result sendSms(String phone) {

        try {
           if(userdao.findUser(phone) != null){
               return new Result(true, StatusCode.ERROR, "手机号："+phone+"已被注册！");
           }
            System.setProperty("sun.net.client.defaultConnectTimeout", "100000");
            System.setProperty("sun.net.client.defaultReadTimeout", "100000");
            //必填阿里手机短信服务配置
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIca9mjmL42o", "MV3zkaapSc6ZG4QQejkgxxWHyGfF");
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("万维科技");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_151835446");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为

            //生成六位的伪随机数
            int code = (int) (Math.random() * 1000000);
            Map m = new HashMap();
            String flag = String.valueOf(code);
            if (flag.length() == 6 && "9".equals(flag.substring(0, 1))) {
                m.put("code", code);
            } else {
                code = code + 100000;
                m.put("code", code);
            }
            System.out.println("验证码=" + code); //生成的随机验证码

            request.setTemplateParam(JSON.toJSONString(m));
            redisTemplate.boundValueOps(code + "").set(code + "", 5, TimeUnit.MINUTES);
            redisTemplate.boundValueOps(phone).set(phone, 5, TimeUnit.MINUTES);
            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
         /*   SendSmsResponse response = acsClient.getAcsResponse(request);
            System.out.println("Code=" + response.getCode());
            System.out.println("Message=" + response.getMessage());
            System.out.println("RequestId=" + response.getRequestId());
            System.out.println("BizId=" + response.getBizId());*/

            return new Result(true, StatusCode.SUCCESS, "验证码已发送到您的手机，有效期五分钟！");
        } catch (Exception e) {
            return new Result(false, StatusCode.ERROR, "验证码已发送到您的手机，有效期五分钟!");
        }
    }

}
