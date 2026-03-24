package com.WM.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录信息")
public class UserLoginDTO implements Serializable {

    @ApiModelProperty("微信授权码")
    private String code;

}
