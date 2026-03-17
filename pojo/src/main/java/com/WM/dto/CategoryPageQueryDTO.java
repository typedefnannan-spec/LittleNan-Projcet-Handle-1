package com.WM.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分页查询信息")
public class CategoryPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("每页记录数")
    private Integer pageSize;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("分类（1：菜品分类，2：套餐分类）")
    private Integer type;

}
