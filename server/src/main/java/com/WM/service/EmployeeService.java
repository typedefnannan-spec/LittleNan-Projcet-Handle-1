package com.WM.service;

import com.WM.dto.EmployeeDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.result.PageResult;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void add(EmployeeDTO employeeAddDTO);

    Employee select(Long id);

    PageResult selectPage(EmployeePageQueryDTO employeePageQueryDTO);

    void updateStatus(Long id,Integer status);

    void update(EmployeeDTO employeeDTO);
}
