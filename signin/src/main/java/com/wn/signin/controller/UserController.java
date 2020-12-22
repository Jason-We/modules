package com.wn.signin.controller;

import com.wn.signin.common.RespCode;
import com.wn.signin.common.RespResult;
import com.wn.signin.entity.User;
import com.wn.signin.service.ResultHandler;
import com.wn.signin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 用户控制器类
 * @date 2020-12-22-11-00
 **/
@RestController
@RequestMapping("/module")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ResultHandler resultHandler;

    @GetMapping(value = "/getAllUser" , produces = "application/json;charset=utf-8")
    public RespResult getAllUser(){
        List<User> users = userService.getAllUser();
        if(users.isEmpty()){
            log.info("users: {} " , users.toString() );
            return resultHandler.handleResult(RespCode.FAIL,users);
        }else {
            log.info("users: {} " , users.toString() );
            return resultHandler.handleResult(RespCode.SUCCESS,users);
        }
    }


}
