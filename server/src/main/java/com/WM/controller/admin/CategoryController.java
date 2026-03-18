package com.WM.controller.admin;

import com.WM.dto.CategoryDTO;
import com.WM.dto.CategoryPageQueryDTO;
import com.WM.entity.Category;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags="分类管理API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("分类插入")
    public Result<Void> addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("插入数据：{}",categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("分类查询")
    public Result<List<Category>> selectCategory(){
        log.info("分类查询");
        return Result.success(categoryService.select());
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("查询信息：{}",categoryPageQueryDTO);
        PageResult pageResult=categoryService.selectPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("权限修改")
    public Result<Void> updateStatus(@PathVariable Integer status,Long id){
        log.info("权限修改（菜品id：{}，菜品状态：{}）",id,status);
        categoryService.updateStatus(id,status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("分类修改")
    public Result<Void> updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("分类修改：{}",categoryDTO);
        categoryService.updateInfo(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("分类删除")
    public Result<Void> deleteCategory(Long id){
        log.info("分类删除：{}",id);
        categoryService.delete(id);
        return Result.success();
    }

}
