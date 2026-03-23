package com.WM.service;

import com.WM.dto.SetmealDTO;
import com.WM.dto.SetmealPageQueryDTO;
import com.WM.result.PageResult;
import com.WM.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    public void add(SetmealDTO setmealDTO);

    public SetmealVO selectById(Long id);

    public PageResult selectPage(SetmealPageQueryDTO setmealPageQueryDTO);

    public void updateStatus(Long id,Integer status);

    public void update(SetmealDTO setmealDTO);

    public void delete(List<Long> ids);

}
