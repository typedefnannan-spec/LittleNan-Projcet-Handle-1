package com.WM.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description="微信用户信息")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("唯一标识")
    private String openid;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("性别（0：女，1：男）")
    private String sex;

    @ApiModelProperty("身份证")
    private String idNumber;

    @ApiModelProperty("头像")
    private String avatar;

    private LocalDateTime createTime;
}
