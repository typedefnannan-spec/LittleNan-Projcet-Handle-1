package com.WM.service;

import com.WM.dto.EmployeeAddDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.entity.Employee;

public interface EmployeeService {

    //用户登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工信息
    void addEmployee(EmployeeAddDTO employeeAddDTO);

}
