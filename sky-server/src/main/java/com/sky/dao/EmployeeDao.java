package com.sky.dao;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeDao {

    //Get employee class by username
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    //Insert employee class
    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime})")
    void insertEmployee(Employee employee);

}
