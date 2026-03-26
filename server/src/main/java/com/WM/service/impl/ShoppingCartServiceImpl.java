package com.WM.service.impl;

import com.WM.dao.DishDao;
import com.WM.dao.SetmealDao;
import com.WM.dao.ShoppingCartDao;
import com.WM.dto.ShoppingCartDTO;
import com.WM.entity.Dish;
import com.WM.entity.Setmeal;
import com.WM.entity.ShoppingCart;
import com.WM.service.ShoppingCartService;
import com.WM.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        //调用工具类转换成ShoppingCart对象
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(ThreadLocalUtil.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartDao.select(shoppingCart);
        //判断是否存在该商品
        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
            ShoppingCart shoppingCart0 = shoppingCartList.get(0);
            shoppingCart0.setNumber(shoppingCart0.getNumber() + 1);
            shoppingCartDao.update(shoppingCart0);
        } else {
            if (shoppingCartDTO.getDishId() != null) {
                Dish dish = dishDao.selectById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealDao.selectById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCartDao.insert(shoppingCart);
        }
    }

    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //调用工具类转换成ShoppingCart对象
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(ThreadLocalUtil.getCurrentId());
        List<ShoppingCart> shoppingCartList = shoppingCartDao.select(shoppingCart);
        ShoppingCart shoppingCart0 = shoppingCartList.get(0);
        //判断商品信息是否删除
        if (shoppingCart0.getNumber() == 1) shoppingCartDao.deleteById(shoppingCart0.getId());
        else {
            shoppingCart0.setNumber(shoppingCart0.getNumber() - 1);
            shoppingCartDao.update(shoppingCart0);
        }
    }

    @Override
    public List<ShoppingCart> select(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(id);
        return shoppingCartDao.select(shoppingCart);
    }

    @Override
    public void deleteAll(Long id) {
        shoppingCartDao.deleteAll(id);
    }

}
