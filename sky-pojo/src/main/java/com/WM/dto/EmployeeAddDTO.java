package com.WM.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Received by add employee")
public class EmployeeAddDTO implements Serializable {

    @ApiModelProperty("Primary key")
    private Long id;

    @ApiModelProperty("Username")
    private String username;

    @ApiModelProperty("Name")
    private String name;

    @ApiModelProperty("Phone number")
    private String phone;

    @ApiModelProperty("Sex")
    private String sex;

    @ApiModelProperty("Id Code")
    private String idNumber;

}
