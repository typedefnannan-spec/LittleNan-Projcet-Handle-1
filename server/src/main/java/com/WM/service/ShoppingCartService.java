package com.WM.service;

import com.WM.dto.ShoppingCartDTO;
import com.WM.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    public void add(ShoppingCartDTO shoppingCartDTO);

    public void sub(ShoppingCartDTO shoppingCartDTO);

    public List<ShoppingCart> select(Long id);

    public void deleteAll(Long id);

}
