package com.WM.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "菜品信息")
public class CategoryDTO implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("分类（1：菜品分类，2：套餐分类）")
    private Integer type;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("排序字段")
    private Integer sort;

}
