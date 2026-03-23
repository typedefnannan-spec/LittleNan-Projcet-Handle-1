package com.WM.service;

import com.WM.dto.DishDTO;
import com.WM.dto.DishPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.result.PageResult;
import com.WM.vo.DishVO;

import java.util.List;

public interface DishService {

    public void add(DishDTO dishDTO);

    public DishVO selectById(Long id);

    public List<Dish> selectBycategoryId(Long categoryId);

    public PageResult selectPage(DishPageQueryDTO dishPageQueryDTO);

    public void updateStatus(Long id,Integer status);

    public void update(DishDTO dishDTO);

    public void delete(List<Long> ids);
}
