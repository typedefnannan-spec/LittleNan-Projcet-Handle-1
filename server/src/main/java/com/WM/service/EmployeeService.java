package com.WM.service;

import com.WM.dto.EmployeeDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.result.PageResult;

public interface EmployeeService {

    //用户登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //添加员工信息
    void add(EmployeeDTO employeeAddDTO);

    //根据id查询员工
    Employee select(Long id);

    //查询分页员工信息
    PageResult selectPage(EmployeePageQueryDTO employeePageQueryDTO);

    //修改员工状态
    void updateStatus(Long id,Integer status);

    //修改员工信息
    void updateInfo(EmployeeDTO employeeDTO);
}
