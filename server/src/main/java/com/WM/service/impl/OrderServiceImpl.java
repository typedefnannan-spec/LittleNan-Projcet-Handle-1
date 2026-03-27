package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.OrdersConstant;
import com.WM.dao.*;
import com.WM.dto.OrdersPaymentDTO;
import com.WM.dto.OrdersSubmitDTO;
import com.WM.entity.*;
import com.WM.exception.AddressBookBusinessException;
import com.WM.exception.OrderBusinessException;
import com.WM.exception.ShoppingCartBusinessException;
import com.WM.service.OrderService;
import com.WM.utils.ThreadLocalUtil;
import com.WM.utils.WeChatPayUtil;
import com.WM.vo.OrderPaymentVO;
import com.WM.vo.OrderSubmitVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private WeChatPayUtil weChatPayUtil;


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

    @Override
    public OrderPaymentVO pay(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        User user = userDao.selectById(ThreadLocalUtil.getCurrentId());
//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "小楠外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "ORDERPAID");
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
        //为替代微信支付成功后的数据库订单状态更新，多定义一个方法进行修改
        Integer OrderPaidStatus = OrdersConstant.PAID; //支付状态，已支付
        Integer OrderStatus = OrdersConstant.TO_BE_CONFIRMED;  //订单状态，待接单
        //发现没有将支付时间 check_out属性赋值，所以在这里更新
        LocalDateTime check_out_time = LocalDateTime.now();
        //获取订单号码
        String orderNumber = ordersPaymentDTO.getOrderNumber();
        orderDao.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, orderNumber);
        return vo;
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));
//        return vo;
    }

    @Override
    public void paySuccess(String outTradeNo) {
        // 根据订单号查询订单
        Orders ordersDB = orderDao.selectByNumber(outTradeNo);
        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(OrdersConstant.TO_BE_CONFIRMED)
                .payStatus(OrdersConstant.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();
        orderDao.update(orders);
    }

}
