package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeDao {

    @Insert("insert into employee(name,username,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user) " +
            "values(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    public void insert(Employee employee);

    @Select("select * from employee where username = #{username}")
    public Employee selectByUsername(String username);

    @Select("select * from employee where id=#{id}")
    public Employee selectById(Long id);

    public Page<Employee> selectPage(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    public void update(Employee employee);

}
