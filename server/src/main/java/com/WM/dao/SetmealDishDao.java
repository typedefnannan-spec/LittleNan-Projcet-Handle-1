package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.entity.SetmealDish;
import com.WM.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishDao {

    public void insert(List<SetmealDish> dishes,Long Id);

    public Boolean selectBydishId(List<Long> ids);

    @Select("select * from setmeal_dish where setmeal_id=#{setmealId}")
    public List<SetmealDish> selectBysetmealId(Long setmealId);

    public void delete(List<Long> ids);

}
