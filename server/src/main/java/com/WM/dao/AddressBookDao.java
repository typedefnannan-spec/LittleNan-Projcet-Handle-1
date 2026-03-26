package com.WM.dao;

import com.WM.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface AddressBookDao {

    public void insert(AddressBook addressBook);

    public List<AddressBook> select(AddressBook addressBook);

    @Select("select * from address_book where id=#{id}")
    public AddressBook selectById(Long id);

    public void update(AddressBook addressBook);

    @Update("update address_book set is_default=#{status} where user_id=#{userId}")
    public void updateAllDefaultByUserId(Long userId,Integer status);

    @Delete("delete from address_book where id=#{id}")
    public void deleteById(Long id);

}
