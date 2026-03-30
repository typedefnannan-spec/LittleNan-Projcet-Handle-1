package com.WM.service;

import com.WM.dto.*;
import com.WM.result.PageResult;
import com.WM.vo.OrderPaymentVO;
import com.WM.vo.OrderStatisticsVO;
import com.WM.vo.OrderSubmitVO;
import com.WM.vo.OrderVO;

public interface OrderService {

    public OrderSubmitVO add(OrdersSubmitDTO ordersSubmitDTO);

    public PageResult User_selectPage(int page, int pageSize, Integer status);

    public PageResult Admin_selectPage(OrdersPageQueryDTO ordersPageQueryDTO);

    public OrderVO select(Long id);

    public void repetition(Long id);

    public void cancelById(Long id) throws Exception;

    public OrderPaymentVO pay(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    public void paySuccess(String outTradeNo);

    public OrderStatisticsVO countOrder();

    public void confirm(OrdersConfirmDTO ordersConfirmDTO);

    public void reject(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    public void delivery(Long id);

    public void complete(Long id);

    public void reminder(Long id);

}
