package com.WM.service;

import com.WM.dto.OrdersPaymentDTO;
import com.WM.dto.OrdersSubmitDTO;
import com.WM.vo.OrderPaymentVO;
import com.WM.vo.OrderSubmitVO;

public interface OrderService {

    public OrderSubmitVO add(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO pay(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

}
