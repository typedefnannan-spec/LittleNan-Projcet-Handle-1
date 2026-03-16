package com.WM.controller.admin;

import com.WM.dto.EmployeeAddDTO;
import com.WM.result.Result;
import com.WM.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("插入员工信息："+employeeAddDTO);
        employeeService.addEmployee(employeeAddDTO);
        return Result.success();
    }

}
