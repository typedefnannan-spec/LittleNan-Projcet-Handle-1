package com.WM.service;

import com.WM.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    public void add(AddressBook addressBook);

    public List<AddressBook> select(Long userId);

    public AddressBook selectById(Long id);

    public AddressBook selectDefault(Long userId);

    public void update(AddressBook addressBook);

    public void updateDefault(AddressBook addressBook);

    public void deleteById(Long id);

}
