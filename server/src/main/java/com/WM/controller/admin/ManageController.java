package com.WM.controller.admin;

import com.WM.dto.EmployeeAddDTO;
import com.WM.dto.EmployeePageQueryDTO;
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
@Slf4j
@Api(tags="员工管理API")
public class ManageController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiOperation("插入员工")
    public Result<Void> addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO){
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
        log.info("修改权限：（员工id：{}，员工状态：{}）",id,status);
        employeeService.updateStatus(id,status);
        return Result.success();
    }


}
