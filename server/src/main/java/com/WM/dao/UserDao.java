package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.entity.User;
import com.WM.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    public void insert(User user);

    @Select("select * from user where openid=#{openid}")
    public User selectByopenid(String openid);

}
