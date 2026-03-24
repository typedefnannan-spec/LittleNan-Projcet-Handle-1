package com.WM.controller.user;

import com.WM.constant.ShopConstant;
import com.WM.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags="用户店铺API")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("状态查询")
    public Result<Integer> getStatus(){
        Integer status=(Integer)redisTemplate.opsForValue().get(ShopConstant.REDIS_SHOP_NAME);
        log.info("状态查询：{}",status==1?"营业":"打烊");
        return Result.success(status);
    }
}
