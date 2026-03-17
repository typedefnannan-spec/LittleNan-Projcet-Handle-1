package com.WM.dao;

import com.WM.annotation.AutoFill;
import com.WM.dto.CategoryPageQueryDTO;
import com.WM.entity.Category;
import com.WM.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDao {
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values(#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    public void insert(Category category);

    public Page<Category> selectPage(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    public void update(Category category);

    @Delete("delete from category where id=#{id}")
    public void deleteById(Long id);

}
