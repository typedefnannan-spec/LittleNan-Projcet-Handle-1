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

@RestController("AdminCategoryController")
@RequestMapping("/admin/category")
@Api(tags="分类管理API")
@Slf4j
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
    public Result<List<Category>> selectCategory(Integer type) {
        log.info("分类查询：{}", type == null ? "全部" : (type == 0 ? "菜品" : "套餐"));
        return Result.success(categoryService.select(type));
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("查询信息：{}",categoryPageQueryDTO);
        PageResult pageResult=categoryService.selectPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("状态修改")
    public Result<Void> updateStatus(@PathVariable Integer status,Long id){
        log.info("状态修改（分类id：{}，分类状态：{}）",id,status==1?"启用":"停用");
        categoryService.updateStatus(id,status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("分类修改")
    public Result<Void> updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("分类修改：{}",categoryDTO);
        categoryService.update(categoryDTO);
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
