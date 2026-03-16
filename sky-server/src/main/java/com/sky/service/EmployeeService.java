package com.sky.service;

import com.sky.dto.EmployeeAddDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    //login interface
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //add interface
    void addEmployee(EmployeeAddDTO employeeAddDTO);

}
