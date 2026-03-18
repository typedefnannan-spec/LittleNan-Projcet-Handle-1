package com.WM.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealDao {

    @Select("select count(*) from setmeal where category_id=#{category_id}")
    public Integer countByCategoryId(Long category_id);

}
