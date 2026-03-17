package com.WM.service;

import com.WM.dto.EmployeeAddDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.result.PageResult;

public interface EmployeeService {

    //用户登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工信息
    void add(EmployeeAddDTO employeeAddDTO);

    //查询分页员工信息
    PageResult selectPage(EmployeePageQueryDTO employeePageQueryDTO);


}
