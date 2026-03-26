package com.WM.service;

import com.WM.dto.OrdersSubmitDTO;
import com.WM.vo.OrderSubmitVO;

public interface OrderService {

    public OrderSubmitVO add(OrdersSubmitDTO ordersSubmitDTO);

}
