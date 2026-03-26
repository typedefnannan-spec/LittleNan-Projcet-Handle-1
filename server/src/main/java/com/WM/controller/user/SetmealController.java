package com.WM.controller.user;

import com.WM.constant.StatusConstant;
import com.WM.dao.SetmealDishDao;
import com.WM.entity.Setmeal;
import com.WM.result.Result;
import com.WM.service.SetmealService;
import com.WM.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags="套餐API")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("套餐查询")
    @Cacheable(cacheNames = "SETMEAL", key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("套餐查询：{}", categoryId);
        Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);
        List<Setmeal> list = setmealService.select(setmeal);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("菜品查询")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id) {
        log.info("菜品查询：{}", id);
        List<DishItemVO> list = setmealService.selectDishItemById(id);
        return Result.success(list);
    }

}
