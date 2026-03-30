package com.WM.service.impl;

import com.WM.WebSocket.WebSocketServer;
import com.WM.constant.MessageConstant;
import com.WM.constant.OrdersConstant;
import com.WM.dao.*;
import com.WM.dto.*;
import com.WM.entity.*;
import com.WM.exception.AddressBookBusinessException;
import com.WM.exception.OrderBusinessException;
import com.WM.exception.ShoppingCartBusinessException;
import com.WM.result.PageResult;
import com.WM.service.OrderService;
import com.WM.utils.ThreadLocalUtil;
import com.WM.utils.WeChatPayUtil;
import com.WM.vo.OrderPaymentVO;
import com.WM.vo.OrderStatisticsVO;
import com.WM.vo.OrderSubmitVO;
import com.WM.vo.OrderVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    private WebSocketServer webSocketServer;

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
    public PageResult User_selectPage(int page, int pageSize, Integer status) {
        //使用分页插件
        PageHelper.startPage(page, pageSize);
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(ThreadLocalUtil.getCurrentId());
        ordersPageQueryDTO.setStatus(status);
        Page<Orders> result = orderDao.selectPage(ordersPageQueryDTO);
        List<OrderVO> list = new ArrayList<>();
        //查询出订单明细，并封装入OrderVO进行响应
        if (result != null && result.getTotal() > 0) {
            for (Orders orders : result) {
                Long orderId = orders.getId();
                //查询订单明细
                List<OrderDetail> orderDetails = orderDetailDao.selectByOrderId(orderId);
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);
                list.add(orderVO);
            }
        }
        return new PageResult(result.getTotal(), list);
    }

    @Override
    public PageResult Admin_selectPage(OrdersPageQueryDTO ordersPageQueryDTO) {
        //使用分页插件
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> page = orderDao.selectPage(ordersPageQueryDTO);
        List<OrderVO> orderVOList = getOrderVOList(page);
        return new PageResult(page.getTotal(), orderVOList);
    }

    @Override
    public OrderVO select(Long id) {
        Orders orders = orderDao.selectById(id);
        List<OrderDetail> orderDetailList = orderDetailDao.selectByOrderId(orders.getId());
        //调用工具类转换成OrderVO对象
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);
        return orderVO;
    }

    @Override
    public void repetition(Long id) {
        //根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailDao.selectByOrderId(id);
        //转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            //将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(ThreadLocalUtil.getCurrentId());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());
        //将购物车对象批量添加到数据库
        shoppingCartDao.insertList(shoppingCartList);
    }

    @Override
    public void cancelById(Long id) throws Exception {
        Orders ordersDB = orderDao.selectById(id);
        //校验订单是否存在
        if (ordersDB == null)
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        //判断订单状态
        if (ordersDB.getStatus() > 2)
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        //订单处于待接单状态下取消，需要进行退款
        if (ordersDB.getStatus().equals(OrdersConstant.TO_BE_CONFIRMED)) {
//            //调用支付退款接口
//            weChatPayUtil.refund(
//                    ordersDB.getNumber(), //商户订单号
//                    ordersDB.getNumber(), //商户退款单号
//                    new BigDecimal(0.01),//退款金额，单位 元
//                    new BigDecimal(0.01));//原订单金额
//            //支付状态修改
            orders.setPayStatus(OrdersConstant.REFUND);
        }
        //更新订单状态、取消原因、取消时间
        orders.setStatus(OrdersConstant.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderDao.update(orders);
    }

    @Override
    public OrderPaymentVO pay(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("用户下单");
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
        //发现没有将支付时间 check_out属性赋值，所以在这里更新
        LocalDateTime check_out_time = LocalDateTime.now();
        orderDao.updateStatus(OrdersConstant.TO_BE_CONFIRMED, OrdersConstant.PAID, check_out_time, ordersPaymentDTO.getOrderNumber());
        Map map = new HashMap();
        map.put("type", OrdersConstant.HAVE_NEW_ORDER);
        map.put("orderId", ThreadLocalUtil.getCurrentId());
        map.put("content", "订单号：" + ordersPaymentDTO.getOrderNumber());
        // 通过WebSocket实现来单提醒，向客户端浏览器推送消息
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
        return vo;
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));
//        return vo;
    }

    @Override
    public void paySuccess(String outTradeNo) {
        //根据订单号查询订单
        Orders ordersDB = orderDao.selectByNumber(outTradeNo);
        //根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(OrdersConstant.TO_BE_CONFIRMED)
                .payStatus(OrdersConstant.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();
        orderDao.update(orders);
    }

    @Override
    public OrderStatisticsVO countOrder() {
        //根据状态，分别查询出待接单、待派送、派送中的订单数量
        Integer toBeConfirmed = orderDao.countStatus(OrdersConstant.TO_BE_CONFIRMED);
        Integer confirmed = orderDao.countStatus(OrdersConstant.CONFIRMED);
        Integer deliveryInProgress = orderDao.countStatus(OrdersConstant.DELIVERY_IN_PROGRESS);
        //将查询出的数据封装到orderStatisticsVO中响应
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }

    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(OrdersConstant.CONFIRMED)
                .build();
        orderDao.update(orders);
    }

    @Override
    public void reject(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        Orders ordersDB = orderDao.selectById(ordersRejectionDTO.getId());
        //订单只有存在且状态为2（待接单）才可以拒单
        if (ordersDB == null || !ordersDB.getStatus().equals(OrdersConstant.TO_BE_CONFIRMED))
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        Orders orders = new Orders();
        if (payStatus == OrdersConstant.PAID) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            //拒单需要退款，根据订单id更新订单状态、拒单原因、取消时间
            orders.setId(ordersDB.getId());
            orders.setStatus(OrdersConstant.CANCELLED);
            orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
            orders.setCancelTime(LocalDateTime.now());
        }
        orderDao.update(orders);
    }

    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception {
        Orders ordersDB = orderDao.selectById(ordersCancelDTO.getId());
        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        Orders orders = new Orders();
        if (payStatus == 1) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            //管理端取消订单需要退款，根据订单id更新订单状态、取消原因、取消时间
            orders.setId(ordersCancelDTO.getId());
            orders.setStatus(OrdersConstant.CANCELLED);
            orders.setCancelReason(ordersCancelDTO.getCancelReason());
            orders.setCancelTime(LocalDateTime.now());
        }
        orderDao.update(orders);
    }

    @Override
    public void delivery(Long id) {
        //根据id查询订单
        Orders ordersDB = orderDao.selectById(id);
        //校验订单是否存在，并且状态为3
        if (ordersDB == null || !ordersDB.getStatus().equals(OrdersConstant.CONFIRMED))
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        //更新订单状态,状态转为派送中
        orders.setStatus(OrdersConstant.DELIVERY_IN_PROGRESS);
        orderDao.update(orders);
    }

    @Override
    public void complete(Long id) {
        Orders ordersDB = orderDao.selectById(id);
        //校验订单是否存在，并且状态为4
        if (ordersDB == null || !ordersDB.getStatus().equals(OrdersConstant.DELIVERY_IN_PROGRESS))
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        //更新订单状态,状态转为完成
        orders.setStatus(OrdersConstant.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());
        orderDao.update(orders);
    }

    @Override
    public void reminder(Long id) {
        log.info("用户催单");
        Orders ordersDB = orderDao.selectById(id);
        //校验订单是否存在
        if (ordersDB == null)
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        Map map = new HashMap();
        map.put("type", OrdersConstant.USER_REMINDER);
        map.put("orderId", id);
        map.put("content", "订单号：" + ordersDB.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        // 需要返回订单菜品信息，自定义OrderVO响应结果
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Orders> ordersList = page.getResult();
        if (!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                //调用工具类转换成OrderVO对象
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);
                //将订单菜品信息封装到orderVO中，并添加到orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    private String getOrderDishesStr(Orders orders) {
        // 查询订单菜品详情信息
        List<OrderDetail> orderDetailList = orderDetailDao.selectByOrderId(orders.getId());
        // 将每一条订单菜品信息拼接为字符串
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());
        // 将该订单对应的所有菜品信息拼接在一起
        return String.join("", orderDishList);
    }


}
