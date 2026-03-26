package com.WM.service.impl;

import com.WM.constant.AddressBookConstant;
import com.WM.dao.AddressBookDao;
import com.WM.entity.AddressBook;
import com.WM.service.AddressBookService;
import com.WM.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookDao addressBookDao;

    @Override
    public void add(AddressBook addressBook) {
        addressBook.setUserId(ThreadLocalUtil.getCurrentId());
        addressBook.setIsDefault(AddressBookConstant.NON_DEFAULT);
        addressBookDao.insert(addressBook);
    }

    @Override
    public List<AddressBook> select(Long userId) {
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(userId);
        return addressBookDao.select(addressBook);
    }

    @Override
    public AddressBook selectById(Long id) {
        return addressBookDao.selectById(id);
    }

    @Override
    public AddressBook selectDefault(Long UserId) {
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(UserId);
        addressBook.setIsDefault(AddressBookConstant.DEFAULT);
        List<AddressBook> addressBookList=addressBookDao.select(addressBook);
        if(addressBookList.isEmpty()) return null;
        else return addressBookList.get(0);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookDao.update(addressBook);
    }

    @Override
    public void updateDefault(AddressBook addressBook) {
        addressBookDao.updateAllDefaultByUserId(ThreadLocalUtil.getCurrentId(),AddressBookConstant.NON_DEFAULT);
        addressBook.setIsDefault(AddressBookConstant.DEFAULT);
        addressBookDao.update(addressBook);
    }

    @Override
    public void deleteById(Long id) {
        addressBookDao.deleteById(id);
    }
}
