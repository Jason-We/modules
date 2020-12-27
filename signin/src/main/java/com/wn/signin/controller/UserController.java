package com.wn.signin.controller;

import cn.hutool.json.JSONObject;
import com.wn.signin.common.RespCode;
import com.wn.signin.common.RespResult;
import com.wn.signin.entity.User;
import com.wn.signin.service.ResultHandler;
import com.wn.signin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private UserService userService;

    @Autowired
    private ResultHandler resultHandler;

    @Autowired
    private StringEncryptor stringEncryptor;

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

    @PostMapping(value = "/register" ,produces = "application/json;charset=utf-8")
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
                return resultHandler.handleResult(RespCode.SUCCESS,"注册成功",null);
            }else{
                return resultHandler.handleResult(RespCode.FAIL,"注册用户失败",user);
            }
        }
    }

    @GetMapping(value = "/regis" ,produces = "application/json;charset=utf-8")
    public RespResult regis(@RequestParam("regType")String regType, @RequestParam("regNum")String regNum,@RequestParam("code")String code,
                            @RequestParam("nickname")String nickname, @RequestParam("password")String password, HttpServletRequest request){
        User u;
        JSONObject object = (JSONObject) request.getSession().getAttribute("mailCodeVerify");

        if(regType.equals("mail")){
            u = userService.getUserByMail(regNum);
        }else{
            u = userService.getUserByPhone(regNum);
        }
        if(Objects.nonNull(u)){
            return resultHandler.handleResult(RespCode.FAIL,"此号已被注册",regNum);
        }else if(regType.equals("mail") && !object.getStr("mailNo").equals(regNum)){
            return resultHandler.handleResult(RespCode.FAIL,"邮箱号错误",regNum);
        }else if(!code.equals(object.getStr("mailCode"))){
            return resultHandler.handleResult(RespCode.FAIL,"验证码错误",code);
        }else if((System.currentTimeMillis()-object.getLong("ctime")) > 30*60*1000){
            return resultHandler.handleResult(RespCode.FAIL,"验证码已过期",code);
        }else{
            String passEncode = stringEncryptor.encrypt(password);
            User user = new User();
            user.setNickname(nickname);
            if(regType.equals("mail")){
                user.setMail(regNum);
            }else{
                user.setPhone(regNum);
            }
            user.setPassword(passEncode);
            if(userService.addUser(user) == 1){
                return resultHandler.handleResult(RespCode.SUCCESS,"注册成功",null);
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
