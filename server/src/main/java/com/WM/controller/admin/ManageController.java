package com.WM.controller.admin;

import com.WM.dto.EmployeeDTO;
import com.WM.dto.EmployeePageQueryDTO;
import com.WM.entity.Employee;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Api(tags="员工管理API")
@Slf4j
public class ManageController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiOperation("员工插入")
    public Result<Void> addEmployee(@RequestBody EmployeeDTO employeeAddDTO){
        log.info("插入员工信息：{}", employeeAddDTO);
        employeeService.add(employeeAddDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("查询信息：{}", employeePageQueryDTO);
        PageResult pageResult=employeeService.selectPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("权限修改")
    public Result<Void> updateStatus(@PathVariable Integer status,Long id){
        log.info("修改权限：（员工id：{}，员工状态：{}）",id,status==1?"开启":"禁用");
        employeeService.updateStatus(id,status);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("信息回显")
    public Result<Employee> returnInfo(@PathVariable Long id){
        Employee employee=employeeService.select(id);
        log.info("信息回显：（员工id：{}，回显信息：{}）",id,employee);
        return Result.success(employee);
    }

    @PutMapping
    @ApiOperation("信息修改")
    public Result<Void> updateInfo(@RequestBody EmployeeDTO employeeDTO){
        log.info("信息修改：{}",employeeDTO);
        employeeService.updateInfo(employeeDTO);
        return Result.success();
    }

}
