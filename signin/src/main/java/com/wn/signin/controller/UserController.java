package com.wn.signin.controller;

import com.wn.signin.common.RespCode;
import com.wn.signin.common.RespResult;
import com.wn.signin.entity.User;
import com.wn.signin.service.ResultHandler;
import com.wn.signin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    StringEncryptor stringEncryptor;

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

    @PostMapping(value = "/regis" ,produces = "application/json;charset=utf-8")
    public RespResult regisUser(@RequestBody User user){
        User u;
        if(user.getPhone() == null){
            u = userService.getUserByMail(user.getMail());
        }else{
            u = userService.getUserByPhone(user.getPhone());
        }
        if(Objects.nonNull(u)){
            return resultHandler.handleResult(RespCode.FAIL,"此号已被注册",user);
        }else{
            String passEncode = stringEncryptor.encrypt(user.getPassword());
            user.setPassword(passEncode);
            if(userService.addUser(user) == 1){
                return resultHandler.handleResult(RespCode.SUCCESS,null);
            }else{
                return resultHandler.handleResult(RespCode.FAIL,"注册用户失败",user);
            }
        }
    }

    @PostMapping(value = "/login" ,produces = "application/json;charset=utf-8")
    public RespResult loginByPassword(@RequestBody User user){
        String pwdEncoded = "";
        if(user.getPhone() == null){
            pwdEncoded = userService.getPassByMail(user.getMail());
        }else{
            pwdEncoded = userService.getPassByPhone(user.getPhone());
        }
        if(pwdEncoded.isEmpty()){
            return resultHandler.handleResult(RespCode.FAIL,"账号不存在",user);
        }else{
            String pwdDecoded = stringEncryptor.decrypt(pwdEncoded);
            if(pwdDecoded.equals(user.getPassword())){
                return resultHandler.handleResult(RespCode.SUCCESS,null);
            }else{
                return resultHandler.handleResult(RespCode.FAIL,"密码错误",user);
            }
        }
    }

}
