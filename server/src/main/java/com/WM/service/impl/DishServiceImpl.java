package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.StatusConstant;
import com.WM.dao.DishDao;
import com.WM.dao.DishFlavorDao;
import com.WM.dao.SetmealDishDao;
import com.WM.dto.DishDTO;
import com.WM.dto.DishPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.entity.DishFlavor;
import com.WM.exception.DeletionNotAllowedException;
import com.WM.result.PageResult;
import com.WM.service.DishService;
import com.WM.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;

    @Autowired
    private DishFlavorDao dishFlavorDao;

    @Autowired
    private SetmealDishDao setmealDishDao;


    @Transactional
    @Override
    public void add(DishDTO dishDTO) {
        //调用工具类转换成Dish对象
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //获取菜品口味集合
        List<DishFlavor> dishFlavorList=dishDTO.getFlavors();

        dishDao.insert(dish);
        dishFlavorDao.insert(dishFlavorList,dish.getId());
    }

    @Override
    public PageResult selectPage(DishPageQueryDTO dishPageQueryDTO) {
        //获取DTO字段信息
        int page=dishPageQueryDTO.getPage();
        int pageSize=dishPageQueryDTO.getPageSize();
        //使用分页插件
        PageHelper.startPage(page,pageSize);
        Page<DishVO> result=dishDao.selectPage(dishPageQueryDTO);
        return new PageResult(result.getTotal(),result.getResult());
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //遍历当前菜品
        for (Long aLong : ids) {
            Dish dish = dishDao.selectById(aLong);
            //如果是起售状态
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //抛不能删除异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        Boolean flag = setmealDishDao.selectByDishId(ids);

        //如果套餐包含该菜品
        if(flag){
            //抛不能删除异常
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            dishDao.deleteById(id);
            dishFlavorDao.deleteById(id);
        }

    }


}
