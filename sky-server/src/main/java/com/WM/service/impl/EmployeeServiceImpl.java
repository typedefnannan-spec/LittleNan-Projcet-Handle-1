package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.PasswordConstant;
import com.WM.constant.StatusConstant;
import com.WM.dto.EmployeeAddDTO;
import com.WM.dto.EmployeeLoginDTO;
import com.WM.entity.Employee;
import com.WM.exception.AccountLockedException;
import com.WM.exception.AccountNotFoundException;
import com.WM.exception.PasswordErrorException;
import com.WM.dao.EmployeeDao;
import com.WM.service.EmployeeService;
import com.WM.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        //获取DTO的用户名和密码
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //将密码转换成MD5加密形式
        password=DigestUtils.md5DigestAsHex(password.getBytes());

        //调用Dao的查询方法
        Employee employee = employeeDao.getByUsername(username);

        //如果账号不存在
        if (employee == null) {
            //抛异常（账号不存在）
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //和存储的密码比较（MD5形式）
        if (!password.equals(employee.getPassword())) {
            //抛异常（密码错误）
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //如果密码正确但是存储状态是锁定
        if (employee.getStatus() == StatusConstant.DISABLE) {
            //抛异常（账号锁定）
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //通过
        return employee;
    }

    @Override
    public void addEmployee(EmployeeAddDTO employeeAddDTO) {
        //调用工具类转换成Employee对象
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeAddDTO,employee);

        //设置其他变量
        employee.setStatus(StatusConstant.ENABLE);
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(ThreadLocalUtil.getCurrentId());
        employee.setUpdateUser(ThreadLocalUtil.getCurrentId());

        //添加进数据库
        employeeDao.insertEmployee(employee);
    }

}
