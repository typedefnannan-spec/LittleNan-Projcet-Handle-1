package com.WM.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description ="获取查询分页信息")
public class EmployeePageQueryDTO implements Serializable {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("每页显示记录数")
    private Integer pageSize;
}
