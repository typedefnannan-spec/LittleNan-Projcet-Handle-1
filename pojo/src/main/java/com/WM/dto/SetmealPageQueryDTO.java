package com.WM.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "获取查询分页信息")
public class SetmealPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页显示记录数")
    private int pageSize;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("销售状态（0：停售，1：起售）")
    private Integer status;

}
