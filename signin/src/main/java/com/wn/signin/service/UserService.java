package com.wn.signin.service;

import com.wn.signin.entity.User;
import com.wn.signin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 用户服务类
 * @date 2020-12-22-10-59
 **/
@Service
public class UserService implements UserMapper {

    @Autowired
    UserMapper userMapper;


    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}
