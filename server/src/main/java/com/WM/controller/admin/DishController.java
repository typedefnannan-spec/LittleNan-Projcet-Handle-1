package com.WM.controller.admin;

import com.WM.dto.DishDTO;
import com.WM.dto.DishPageQueryDTO;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理API")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("菜品新增")
    public Result<Void> addDish(@RequestBody DishDTO dishDTO){
        log.info("菜品新增：{}",dishDTO);
        dishService.add(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询：{}",dishPageQueryDTO);
        PageResult pageResult=dishService.selectPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result<Void> delete(@RequestParam List<Long> ids){
        log.info("菜品删除：{}",ids);
        dishService.delete(ids);
        return Result.success();
    }


}
