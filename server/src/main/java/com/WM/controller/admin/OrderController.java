package com.WM.controller.admin;

import com.WM.dto.OrdersCancelDTO;
import com.WM.dto.OrdersConfirmDTO;
import com.WM.dto.OrdersPageQueryDTO;
import com.WM.dto.OrdersRejectionDTO;
import com.WM.result.PageResult;
import com.WM.result.Result;
import com.WM.service.OrderService;
import com.WM.vo.OrderStatisticsVO;
import com.WM.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单管理API")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("分页查询")
    public Result<PageResult> selectPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("分页查询：{}",ordersPageQueryDTO);
        PageResult pageResult = orderService.Admin_selectPage(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("订单数量统计")
    public Result<OrderStatisticsVO> countOrder() {
        log.info("订单数量统计");
        OrderStatisticsVO orderStatisticsVO = orderService.countOrder();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("订单详情查询")
    public Result<OrderVO> details(@PathVariable Long id) {
        log.info("订单详情查询：{}",id);
        OrderVO orderVO = orderService.select(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result<Void> confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("接单");
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("拒单");
        orderService.reject(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result<Void> cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        log.info("取消订单");
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result<Void> delivery(@PathVariable Long id) {
        log.info("派送订单");
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result<Void> complete(@PathVariable Long id) {
        log.info("完成订单");
        orderService.complete(id);
        return Result.success();
    }

}
