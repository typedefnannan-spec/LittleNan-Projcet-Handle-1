package com.WM.dao;

import com.WM.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    public void insert(Orders orders);



}
