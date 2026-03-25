package com.WM.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "套餐信息")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    @ApiModelProperty("销售状态（0：停售，1：起售）")
    private Integer status;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("图片")
    private String image;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
