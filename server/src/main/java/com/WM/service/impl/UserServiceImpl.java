package com.WM.service.impl;

import com.WM.constant.MessageConstant;
import com.WM.constant.WXConstant;
import com.WM.dao.UserDao;
import com.WM.dto.UserLoginDTO;
import com.WM.entity.User;
import com.WM.exception.LoginFailedException;
import com.WM.properties.WeChatProperties;
import com.WM.service.UserService;
import com.WM.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private WeChatProperties weChatProperties;

    //获取openid方法
    private String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put(WXConstant.WX_APPID, weChatProperties.getAppid());
        map.put(WXConstant.WX_SECRET, weChatProperties.getSecret());
        map.put(WXConstant.WX_CODE, code);
        map.put(WXConstant.WX_GRANT_TYPE, WXConstant.WX_GRANT_TYPE_VALUE);
        String json = HttpClientUtil.doGet(WXConstant.WX_LOGIN_URL, map);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString(WXConstant.WX_OPEN_ID);
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        //获取openid
        String openid = getOpenid(userLoginDTO.getCode());
        //如果openid为空
        if (openid == null)
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        User user = userDao.selectByopenid(openid);
        //如果是新用户
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            userDao.insert(user);
        }
        return user;
    }

}
