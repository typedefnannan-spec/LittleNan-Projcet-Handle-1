package com.WM.dao;

import com.WM.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailDao {

    public void insert(List<OrderDetail> orderDetailList);

}
