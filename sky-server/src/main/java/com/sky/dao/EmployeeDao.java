package com.sky.dao;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeDao {

    //通过用户名去查找对应员工信息
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    //插入员工信息
    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime})")
    void insertEmployee(Employee employee);

}
