package com.WM.service;

import com.WM.dto.UserLoginDTO;
import com.WM.entity.User;

public interface UserService {
    public User login(UserLoginDTO userLoginDTO);
}
