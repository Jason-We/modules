package com.wn.signin.controller;

import cn.hutool.json.JSONObject;
import com.wn.signin.common.RespCode;
import com.wn.signin.common.RespResult;
import com.wn.signin.service.MailService;
import com.wn.signin.service.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: modules
 * @description: mail send
 * @author: JasonWe
 * @create: 2020-12-27 18:59
 **/
@RestController
@RequestMapping("/module")
@Slf4j
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private ResultHandler resultHandler;

    @GetMapping(value = "/mailCode" ,produces = "application/json;charset=utf-8")
    public RespResult getMailCode(@RequestParam("mailNo")String mailNo, HttpServletRequest request){
        String code = mailService.sendVertifyCode(mailNo);
        JSONObject object = new JSONObject();
        object.set("mailCode",code);
        object.set("ctime",System.currentTimeMillis());
        object.set("mailNo",mailNo);
        request.getSession().setAttribute("mailCodeVerify",object);
        return resultHandler.handleResult(RespCode.SUCCESS,"验证码已发送",null);
    }
}
