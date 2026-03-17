package com.WM.dao;

import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeDao {

    //通过用户名去查找对应员工信息
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    //插入员工信息
    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime})")
    void insertEmployee(Employee employee);

    //查询分页信息
    Page<Employee> selectPage(EmployeePageQueryDTO employeePageQueryDTO);

    //修改员工信息
    void update(Employee employee);

}
