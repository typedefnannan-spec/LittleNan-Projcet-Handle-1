package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.entity.ShoppingCart;
import com.WM.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartDao {

    public void insert(ShoppingCart shoppingCart);

    public List<ShoppingCart> select(ShoppingCart shoppingCart);

    public void update(ShoppingCart shoppingCart);

}
