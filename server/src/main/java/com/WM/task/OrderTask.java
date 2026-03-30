package com.WM.task;

import com.WM.constant.MessageConstant;
import com.WM.constant.OrdersConstant;
import com.WM.dao.OrderDao;
import com.WM.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderDao orderDao;


    @Scheduled(cron="0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("处理超时订单：{}", new Date());
        List<Orders> ordersList = orderDao.selectByStatusAndOrderTime(OrdersConstant.PENDING_PAYMENT, LocalDateTime.now().plusMinutes(-15));
        for (Orders order : ordersList) {
            order.setStatus(OrdersConstant.CANCELLED);
            order.setCancelReason(MessageConstant.ORDER_TIME_ERROR);
            order.setCancelTime(LocalDateTime.now());
            orderDao.update(order);
        }
    }

    @Scheduled(cron="0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("处理派送中订单：{}", new Date());
        List<Orders> ordersList=orderDao.selectByStatusAndOrderTime(OrdersConstant.DELIVERY_IN_PROGRESS,LocalDateTime.now().plusHours(-1));
        for (Orders order : ordersList) {
            order.setStatus(OrdersConstant.COMPLETED);
            orderDao.update(order);
        }
    }

}
