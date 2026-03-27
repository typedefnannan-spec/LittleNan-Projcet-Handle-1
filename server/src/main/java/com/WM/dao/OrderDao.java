package com.WM.dao;

import com.WM.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface OrderDao {

    public void insert(Orders orders);

    @Select("select * from orders where number = #{number}")
    public Orders selectByNumber(String number);

    void update(Orders orders);

    @Update("update orders " +
            "set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} " +
            "where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

}
