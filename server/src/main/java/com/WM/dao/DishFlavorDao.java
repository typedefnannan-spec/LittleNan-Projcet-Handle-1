package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorDao {

    public void insert(List<DishFlavor> dishFlavorList,Long dishId);

    @Select("select * from dish_flavor where dish_id=#{dishId}")
    public List<DishFlavor> select(Long dishId);

    public void delete(List<Long> ids);

}
