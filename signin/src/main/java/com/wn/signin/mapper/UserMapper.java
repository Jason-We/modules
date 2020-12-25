package com.wn.signin.mapper;

import com.wn.signin.entity.User;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("insert into muser (nickname,phone,mail,password) values(#{nickname},#{phone},#{mail},#{password})")
    int addUser(User user);

    @Select("select * from muser where phone= #{phone}")
    User getUserByPhone(String phone);

    @Select("select * from muser where mail= #{mail}")
    User getUserByMail(String mail);

    @Select("select password from muser where phone = #{phone}")
    String getPassByPhone(String phone);

    @Select("select password from muser where mail = #{mail}")
    String getPassByMail(String mail);





}
