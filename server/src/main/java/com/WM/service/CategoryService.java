package com.WM.service;

import com.WM.dto.CategoryDTO;
import com.WM.dto.CategoryPageQueryDTO;
import com.WM.result.PageResult;

public interface CategoryService {
    public void add(CategoryDTO categoryDTO);

    public PageResult selectPage(CategoryPageQueryDTO categoryPageQueryDTO);

    public void updateStatus(Long id,Integer status);

    public void updateInfo(CategoryDTO categoryDTO);

    public void delete(Long id);

}
