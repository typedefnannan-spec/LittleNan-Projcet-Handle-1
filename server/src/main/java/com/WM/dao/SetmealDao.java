package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.dto.SetmealPageQueryDTO;
import com.WM.entity.Setmeal;
import com.WM.enumeration.OperationType;
import com.WM.vo.DishItemVO;
import com.WM.vo.DishVO;
import com.WM.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface SetmealDao {

    @AutoFill(value = OperationType.INSERT)
    public void insert(Setmeal setmeal);

    public List<Setmeal> select(Setmeal setmeal);

    @Select("select * from setmeal where id=#{id}")
    public Setmeal selectById(Long id);

    public Page<SetmealVO> selectPage(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    public void update(Setmeal setmeal);

    public void delete(List<Long> ids);

    @Select("select count(*) from setmeal where category_id=#{category_id}")
    public Integer countBycategoryId(Long category_id);

    public Integer countBydishId(Long dishId,Integer status);

    public Integer countSelectMethod(Setmeal setmeal);

}
