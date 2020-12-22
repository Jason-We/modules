package com.wn.signin.entity;

import lombok.Data;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 用户类
 * @date 2020-12-22-10-46
 **/
@Data
public class User {

    private String nickname;
    private String password;
    private String phone;
    private String mail;
    /* "男" "女" "保密"*/
    private String sex;

}
