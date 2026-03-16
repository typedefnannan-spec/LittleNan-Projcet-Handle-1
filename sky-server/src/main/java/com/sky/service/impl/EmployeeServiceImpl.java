package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        //Get username and password
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //To MD5
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //Select class by username
        Employee employee = employeeMapper.getByUsername(username);

        //The Account isn't Exist
        if (employee == null) {
            //Throw exception(Account Not Found)
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //Compare the password in MD5
        if (!password.equals(employee.getPassword())) {
            //Throw exception(Password Error)
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //The password is correct but the account is locked
        if (employee.getStatus() == StatusConstant.DISABLE) {
            //Throw exception(Account Locked)
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //Accept
        return employee;
    }

}
