package com.sky.controller.admin;

import com.sky.dto.EmployeeAddDTO;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
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
@Api(tags="Employee API about manage")
public class ManageController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiOperation("Add employee")
    public Result<Void> addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO){
        log.info("Add employee: "+employeeAddDTO);
        employeeService.addEmployee(employeeAddDTO);
        return Result.success();
    }


}
