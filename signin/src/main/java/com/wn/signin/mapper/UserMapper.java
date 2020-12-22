package com.wn.signin.mapper;

import com.wn.signin.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Jason-we
 * @program: modules
 * @Description: 用户mapper映射
 * @date 2020-12-22-10-55
 **/
public interface UserMapper {

    @Select("select * from muser")
    List<User> getAllUser();

}
