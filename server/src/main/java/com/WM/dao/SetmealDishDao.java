package com.WM.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishDao {
    public Boolean selectByDishId(List<Long> ids);
}
