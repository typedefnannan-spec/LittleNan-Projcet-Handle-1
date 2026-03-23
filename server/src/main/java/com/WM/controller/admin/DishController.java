package com.WM.controller.admin;

import com.WM.dto.DishDTO;
import com.WM.dto.DishPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.DishService;
import com.WM.vo.DishVO;
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

    @GetMapping("/list")
    @ApiOperation("菜品查询（分类id）")
    public Result<List<Dish>> selectDishBycategoryId(Long categoryId){
        log.info("菜品查询：{}",categoryId);
        List<Dish> res=dishService.selectBycategoryId(categoryId);
        return Result.success(res);
    }


    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询：{}",dishPageQueryDTO);
        PageResult pageResult=dishService.selectPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("菜品回显（主键id）")
    public Result<DishVO> selectDishById(@PathVariable Long id){
        log.info("菜品回显：{}",id);
        DishVO dishVo=dishService.selectById(id);
        return Result.success(dishVo);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("状态修改")
    public Result<Void> updateStatus(@PathVariable Integer status,Long id){
        log.info("状态修改（菜品id：{}，菜品状态：{}）",id,status==1?"开放":"不开放");
        dishService.updateStatus(id,status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("菜品修改")
    public Result<Void> updateDish(@RequestBody DishDTO dishDTO){
        log.info("菜品修改：{}",dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result<Void> deleteDish(@RequestParam List<Long> ids){
        log.info("菜品删除：{}",ids);
        dishService.delete(ids);
        return Result.success();
    }

}
