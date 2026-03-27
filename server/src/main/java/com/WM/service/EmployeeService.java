package com.WM.service;

import com.WM.dto.EmployeeDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.result.PageResult;

public interface EmployeeService {

    public Employee login(EmployeeLoginDTO employeeLoginDTO);

    public void add(EmployeeDTO employeeAddDTO);

    public Employee select(Long id);

    public PageResult selectPage(EmployeePageQueryDTO employeePageQueryDTO);

    public void updateStatus(Long id,Integer status);

    public void update(EmployeeDTO employeeDTO);
}
