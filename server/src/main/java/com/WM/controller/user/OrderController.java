package com.WM.controller.user;

import com.WM.dto.OrdersPaymentDTO;
import com.WM.dto.OrdersSubmitDTO;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.OrderService;
import com.WM.vo.OrderPaymentVO;
import com.WM.vo.OrderSubmitVO;
import com.WM.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
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

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> selectPage(int page, int pageSize, Integer status) {
        log.info("历史订单查询：（page：{}，pageSize：{}，status：{}）",page,pageSize,status);
        PageResult pageResult = orderService.User_selectPage(page, pageSize, status);
        return Result.success(pageResult);
    }

    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result<Void> repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("订单取消")
    public Result<Void> cancelOrder(@PathVariable Long id) throws Exception {
        log.info("订单取消：{}",id);
        orderService.cancelById(id);
        return Result.success();
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("订单查询（主键id）")
    public Result<OrderVO> selectOrderById(@PathVariable Long id) {
        log.info("订单查询：{}",id);
        OrderVO orderVO = orderService.select(id);
        return Result.success(orderVO);
    }

    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.pay(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }

}
