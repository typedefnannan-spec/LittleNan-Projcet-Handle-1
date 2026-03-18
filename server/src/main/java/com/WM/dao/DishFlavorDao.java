package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorDao {

    void insert(List<DishFlavor> dishFlavorList,Long dishId);

    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    void deleteById(Long dishId);
}
