package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.StatusConstant;
import com.WM.dao.DishDao;
import com.WM.dao.SetmealDao;
import com.WM.dao.SetmealDishDao;
import com.WM.dto.SetmealDTO;
import com.WM.dto.SetmealPageQueryDTO;
import com.WM.entity.Dish;
import com.WM.entity.Setmeal;
import com.WM.entity.SetmealDish;
import com.WM.exception.DeletionNotAllowedException;
import com.WM.result.PageResult;
import com.WM.service.SetmealService;
import com.WM.vo.SetmealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private SetmealDishDao setmealDishDao;

    @Autowired
    private DishDao dishDao;

    @Override
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        //调用工具类转换成Setmeal对象
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //获取菜品集合
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();

        setmealDao.insert(setmeal);
        setmealDishDao.insert(setmealDishes,setmeal.getId());
    }

    @Override
    public SetmealVO selectById(Long id) {
        //调用工具类转换成SetmealVO对象
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmealDao.selectById(id),setmealVO);

        //设置包含菜品信息
        setmealVO.setSetmealDishes(setmealDishDao.selectBysetmealId(id));

        return setmealVO;
    }

    @Override
    public PageResult selectPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        //获取DTO字段信息
        int page=setmealPageQueryDTO.getPage();
        int pageSize=setmealPageQueryDTO.getPageSize();

        //使用分页插件
        PageHelper.startPage(page,pageSize);
        Page<SetmealVO> pages=setmealDao.selectPage(setmealPageQueryDTO);

        return new PageResult(pages.getTotal(),pages.getResult());
    }

    @Override
    public void updateStatus(Long id, Integer status) {

        //如果要起售
        if(status==StatusConstant.ENABLE) {
            List<SetmealDish> setmealDishes = setmealDishDao.selectBysetmealId(id);
            for (SetmealDish setmealDish : setmealDishes) {
                //获取当前菜品
                Dish dish = dishDao.selectById(setmealDish.getDishId());

                //如果是停售状态
                if (dish.getStatus() == StatusConstant.DISABLE) {
                    //抛不能删除异常
                    throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
        }

        Setmeal setmeal=new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);

        setmealDao.update(setmeal);
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        //调用工具类转换成Setmeal对象
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //获取菜品关系集合
        List<SetmealDish> setmealDishList=setmealDTO.getSetmealDishes();

        //获取要删除的套餐id
        List<Long> ids= new ArrayList<>();
        ids.add(setmealDTO.getId());

        setmealDao.update(setmeal);
        setmealDishDao.delete(ids);
        setmealDishDao.insert(setmealDTO.getSetmealDishes(),setmeal.getId());
    }

    @Override
    public void delete(List<Long> ids) {


        //遍历当前套餐
        for (Long id : ids) {

            log.info("delete:{}",id);

            Setmeal setmeal = setmealDao.selectById(id);

            //如果是起售状态
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                //抛不能删除异常
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        setmealDishDao.delete(ids);
        setmealDao.delete(ids);
    }

}
