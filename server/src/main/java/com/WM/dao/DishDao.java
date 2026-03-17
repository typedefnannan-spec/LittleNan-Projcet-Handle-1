package com.WM.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishDao {
    @Select("select count(*) from dish where category_id=#{category_id}")
    public Integer countByCategoryId(Long category_id);
}
