package com.WM.controller.user;

import com.WM.constant.MessageConstant;
import com.WM.entity.AddressBook;
import com.WM.result.Result;
import com.WM.service.AddressBookService;
import com.WM.utils.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "地址API")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation("地址查询")
    public Result<List<AddressBook>> selectAddress() {
        log.info("地址查询");
        List<AddressBook> addressBookList = addressBookService.select(ThreadLocalUtil.getCurrentId());
        return Result.success(addressBookList);
    }

    @PostMapping
    @ApiOperation("地址新增")
    public Result<Void> addAdress(@RequestBody AddressBook addressBook){
        log.info("地址新增：{}",addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("地址查询（id）")
    public Result<AddressBook> selectAddresssById(Long id){
        log.info("地址查询：{}",id);
        AddressBook addressBook=addressBookService.selectById(id);
        return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("地址修改（id）")
    public Result<Void> updateAddress(@RequestBody AddressBook addressBook){
        log.info("地址修改：{}",addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    @PutMapping("/default")
    @ApiOperation("默认地址设置")
    public Result<Void> updateDefaultAddress(@RequestBody AddressBook addressBook){
        log.info("默认地址设置：{}",addressBook);
        addressBookService.updateDefault(addressBook);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("地址删除（id）")
    public Result<Void> deleteAddressById(Long id){
        log.info("地址删除：{}",id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation("默认地址查询")
    public Result<AddressBook> selectDefaultAddress(){
        log.info("默认地址查询");
        AddressBook addressBook=addressBookService.selectDefault(ThreadLocalUtil.getCurrentId());
        if(addressBook==null) return Result.error(MessageConstant.ADDRESS_BOOK_DEFAULT_EMPTY);
        else return Result.success(addressBook);
    }

}
