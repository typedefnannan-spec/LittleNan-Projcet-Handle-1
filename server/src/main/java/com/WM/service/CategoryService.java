package com.WM.service;

import com.WM.dto.CategoryDTO;
import com.WM.dto.CategoryPageQueryDTO;
import com.WM.entity.Category;
import com.WM.result.PageResult;

import java.util.List;

public interface CategoryService {
    public void add(CategoryDTO categoryDTO);

    public List<Category> select(Integer type);

    public PageResult selectPage(CategoryPageQueryDTO categoryPageQueryDTO);

    public void updateStatus(Long id,Integer status);

    public void update(CategoryDTO categoryDTO);

    public void delete(Long id);

}
