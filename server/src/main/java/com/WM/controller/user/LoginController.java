package com.WM.controller.user;

import com.WM.constant.JwtClaimsConstant;
import com.WM.dto.UserLoginDTO;
import com.WM.entity.User;
import com.WM.properties.JwtProperties;
import com.WM.result.Result;
import com.WM.service.UserService;
import com.WM.utils.JwtUtil;
import com.WM.vo.EmployeeLoginVO;
import com.WM.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("userLoginController")
@RequestMapping("/user/user")
@Api(tags = "用户登录API")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信登录：{}",userLoginDTO);
        User user=userService.login(userLoginDTO);
        //创建JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}
