package com.WM.controller.user;

import com.WM.constant.RedisConstant;
import com.WM.constant.StatusConstant;
import com.WM.entity.Dish;
import com.WM.result.Result;
import com.WM.service.DishService;
import com.WM.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserDishController")
@RequestMapping("/user/dish")
@Api(tags = "菜品API")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("菜品查询（分类id）")
    public Result<List<DishVO>> selectDishBycategoryId(Long categoryId){
        log.info("菜品查询：{}",categoryId);
        String key = RedisConstant.DISH_NAME_PREFIX+categoryId;
        List<DishVO> object = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(object!=null && !object.isEmpty())
            return Result.success(object);
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);
        List<DishVO> list = dishService.selectWithFlavor(dish);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }

}
