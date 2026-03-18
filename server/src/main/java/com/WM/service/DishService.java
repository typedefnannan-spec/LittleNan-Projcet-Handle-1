package com.WM.service;

import com.WM.dto.DishDTO;
import com.WM.dto.DishPageQueryDTO;
import com.WM.result.PageResult;

import java.util.List;

public interface DishService {

    public void add(DishDTO dishDTO);

    public PageResult selectPage(DishPageQueryDTO dishPageQueryDTO);

    public void delete(List<Long> ids);
}
