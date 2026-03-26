package com.WM.controller.user;

import com.WM.dto.OrdersSubmitDTO;
import com.WM.result.Result;
import com.WM.service.OrderService;
import com.WM.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Api(tags = "订单API")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("订单新增")
    public Result<OrderSubmitVO> addOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("订单新增：{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO=orderService.add(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

}
