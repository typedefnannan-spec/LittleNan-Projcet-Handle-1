package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.StatusConstant;
import com.WM.dao.CategoryDao;
import com.WM.dao.DishDao;
import com.WM.dao.SetmealDao;
import com.WM.dto.CategoryDTO;
import com.WM.dto.CategoryPageQueryDTO;
import com.WM.entity.Category;
import com.WM.exception.DeletionNotAllowedException;
import com.WM.result.PageResult;
import com.WM.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DishDao dishDao;

    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(CategoryDTO categoryDTO) {
        //调用工具类转换成Category对象
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //设置其他变量
        category.setStatus(StatusConstant.DISABLE);

        categoryDao.insert(category);
    }

    @Override
    public PageResult selectPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        //获取DTO字段信息
        int page=categoryPageQueryDTO.getPage();
        int pageSize=categoryPageQueryDTO.getPageSize();

        //使用分页插件
        PageHelper.startPage(page,pageSize);
        Page<Category> currPage=categoryDao.selectPage(categoryPageQueryDTO);

        //将Page对象处理成PageResult
        List<Category> result=currPage.getResult();
        long total=currPage.getTotal();

        return new PageResult(total,result);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Category category=new Category();
        category.setStatus(status);
        category.setId(id);

        categoryDao.update(category);
    }

    @Override
    public void updateInfo(CategoryDTO categoryDTO) {
        //调用工具类转换为Category对象
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        categoryDao.update(category);
    }

    @Override
    public void delete(Long category_id) {
        //计算是否存在属于该分类的套餐/菜品
        int countSetmeal=setmealDao.countByCategoryId(category_id);
        int countDish=dishDao.countByCategoryId(category_id);

        //如果存在
        if(countDish>0)
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        if(countSetmeal>0)
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);

        categoryDao.deleteById(category_id);
    }
}
