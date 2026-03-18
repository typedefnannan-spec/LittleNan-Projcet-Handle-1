package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.dto.DishPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.enumeration.OperationType;
import com.WM.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishDao {
    @Select("select count(*) from dish where category_id=#{category_id}")
    public Integer countByCategoryId(Long category_id);

    @Select("select * from dish where id=#{id}")
    public Dish selectById(Long id);

    public Page<DishVO> selectPage(DishPageQueryDTO dishPageQueryDTO);

    @AutoFill(value = OperationType.INSERT)
    public Integer insert(Dish dish);

    @Delete("delete from dish where id=#{id}")
    public void deleteById(Long id);


}
