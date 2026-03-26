package com.WM.controller.user;

import com.WM.dto.ShoppingCartDTO;
import com.WM.entity.ShoppingCart;
import com.WM.result.Result;
import com.WM.service.ShoppingCartService;
import com.WM.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车API")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("菜品/套餐添加")
    public Result<Void> addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("菜品/套餐添加：{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("菜品/套餐查询")
    public Result<List<ShoppingCart>> selectShoppingCart(){
        log.info("菜品/套餐查询");
        List<ShoppingCart> shoppingCartList=shoppingCartService.select(ThreadLocalUtil.getCurrentId());
        return Result.success(shoppingCartList);
    }

    @PostMapping("/sub")
    @ApiOperation("菜品/套餐删除")
    public Result<Void> delete(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("菜品/套餐删除：{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    @ApiOperation("购物车清空")
    public Result<Void> deleteAll(){
        log.info("购物车清空");
        shoppingCartService.deleteAll(ThreadLocalUtil.getCurrentId());
        return Result.success();
    }


}
