package com.WM.controller.user;

import com.WM.entity.Category;
import com.WM.result.Result;
import com.WM.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserCategoryController")
@RequestMapping("/user/category")
@Api(tags = "分类API")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation("分类查询")
    public Result<List<Category>> selectCategory(Integer type){
        log.info("分类查询：{}",type==null?"全部":(type==0?"菜品":"套餐"));
        List<Category> list = categoryService.select(type);
        return Result.success(list);
    }
}
