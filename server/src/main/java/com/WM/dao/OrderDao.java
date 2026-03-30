package com.WM.dao;

import com.WM.dto.OrdersPageQueryDTO;
import com.WM.entity.Orders;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDao {

    public void insert(Orders orders);

    @Select("select * from orders where number = #{number}")
    public Orders selectByNumber(String number);

    @Select("select * from orders where id=#{id}")
    public Orders selectById(Long id);

    public Page<Orders> selectPage(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where status=#{status} and order_time<#{orderTime}")
    List<Orders> selectByStatusAndOrderTime(Integer status,LocalDateTime orderTime);

    public void update(Orders orders);

    @Update("update orders " +
            "set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} " +
            "where number = #{orderNumber}")
    public void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

    @Select("select count(id) from orders where status = #{status}")
    public Integer countStatus(Integer status);

}
