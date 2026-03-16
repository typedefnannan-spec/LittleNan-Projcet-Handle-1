package com.sky.service;

import com.sky.dto.EmployeeAddDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    //用户登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工信息
    void addEmployee(EmployeeAddDTO employeeAddDTO);

}
