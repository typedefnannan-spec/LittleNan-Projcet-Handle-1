package com.WM.controller.admin;

import com.WM.constant.RedisConstant;
import com.WM.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "客户端店铺API")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("状态修改")
    public Result<Void> setStatus(@PathVariable Integer status) {
        log.info("状态修改：{}", status == 1 ? "营业" : "打烊");
        redisTemplate.opsForValue().set(RedisConstant.SHOP_NAME, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("状态查询")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(RedisConstant.SHOP_NAME);
        log.info("状态查询：{}", status == 1 ? "营业" : "打烊");
        return Result.success(status);
    }

}
