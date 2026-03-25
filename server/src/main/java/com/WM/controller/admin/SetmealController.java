package com.WM.controller.admin;

import com.WM.dto.SetmealDTO;
import com.WM.dto.SetmealPageQueryDTO;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.SetmealService;
import com.WM.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理API")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("套餐新增")
    @CacheEvict(cacheNames = "SETMEAL",key="#setmealDTO.categoryId")
    public Result<Void> addSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("套餐新增：{}",setmealDTO);
        setmealService.add(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询：{}",setmealPageQueryDTO);
        PageResult pageResult=setmealService.selectPage(setmealPageQueryDTO);
        log.info("{}",pageResult);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("套餐回显（主键id）")
    public Result<SetmealVO> selectSetmealById(@PathVariable Long id){
        log.info("菜品回显：{}",id);
        SetmealVO setmealVO=setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("状态修改")
    @CacheEvict(cacheNames = "SETMEAL",allEntries = true)
    public Result<Void> updateStatus(@PathVariable Integer status,Long id){
        log.info("状态修改（套餐id：{}，套餐状态：{}）",id,status==1?"开放":"不开放");
        setmealService.updateStatus(id,status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("套餐修改")
    @CacheEvict(cacheNames = "SETMEAL",allEntries = true)
    public Result<Void> updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("套餐修改：{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("套餐删除")
    @CacheEvict(cacheNames = "SETMEAL",allEntries = true)
    public Result<Void> deleteSetmeal(@RequestParam List<Long> ids){
        log.info("套餐删除：{}",ids);
        setmealService.delete(ids);
        return Result.success();
    }

}
