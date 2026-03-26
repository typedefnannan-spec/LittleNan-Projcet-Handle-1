package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.OrdersConstant;
import com.WM.dao.AddressBookDao;
import com.WM.dao.OrderDao;
import com.WM.dao.OrderDetailDao;
import com.WM.dao.ShoppingCartDao;
import com.WM.dto.OrdersSubmitDTO;
import com.WM.entity.AddressBook;
import com.WM.entity.OrderDetail;
import com.WM.entity.Orders;
import com.WM.entity.ShoppingCart;
import com.WM.exception.AddressBookBusinessException;
import com.WM.exception.ShoppingCartBusinessException;
import com.WM.service.OrderService;
import com.WM.utils.ThreadLocalUtil;
import com.WM.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private AddressBookDao addressBookDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Override
    @Transactional
    public OrderSubmitVO add(OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        Orders orders = new Orders();
        AddressBook addressBook = addressBookDao.selectById(ordersSubmitDTO.getAddressBookId());
        //判断地址信息是否为空
        if (addressBook == null)
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        orders.setAddressBookId(addressBook.getId());
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(ThreadLocalUtil.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartDao.select(shoppingCart);
        //判断购物车信息是否为空
        if (shoppingCartList.isEmpty())
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        //调用工具类转换成Orders对象
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(OrdersConstant.UN_PAID);
        orders.setStatus(OrdersConstant.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(LocalDateTime.now()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(ThreadLocalUtil.getCurrentId());
        orderDao.insert(orders);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            //调用工具类转换成OrderDetail对象
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailDao.insert(orderDetailList);
        shoppingCartDao.deleteAll(ThreadLocalUtil.getCurrentId());
        return new OrderSubmitVO(orders.getId(), orders.getNumber(), orders.getAmount(), orders.getOrderTime());
    }

}
