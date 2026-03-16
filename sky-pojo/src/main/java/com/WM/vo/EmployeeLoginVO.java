package com.WM.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Return by employee login")
public class EmployeeLoginVO implements Serializable {

    @ApiModelProperty("Primary key")
    private Long id;

    @ApiModelProperty("Username")
    private String userName;

    @ApiModelProperty("Name")
    private String name;

    @ApiModelProperty("JWT code")
    private String token;

}
