package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.dto.DishPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.enumeration.OperationType;
import com.WM.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishDao {

    @Select("select * from dish where id=#{id}")
    public Dish selectById(Long id);

    @Select("select * from dish where category_id=#{categoryId}")
    public List<Dish> selectBycategoryId(Long categoryId);

    public Page<DishVO> selectPage(DishPageQueryDTO dishPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    public Integer insert(Dish dish);

    @AutoFill(value = OperationType.UPDATE)
    public void update(Dish dish);

    public void delete(List<Long> ids);

    @Select("select count(*) from dish where category_id=#{category_id}")
    public Integer countByCategoryId(Long category_id);

}
