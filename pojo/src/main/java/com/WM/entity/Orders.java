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

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单信息")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("订单状态（1：待付款，2：待接单，3：已接单，4：派送中，5：已完成，6：已取消，7：退款）")
    private Integer status;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("地址id")
    private Long addressBookId;

    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty("付款时间")
    private LocalDateTime checkoutTime;

    @ApiModelProperty("支付方式（1：微信，2：支付宝）")
    private Integer payMethod;

    @ApiModelProperty("支付状态（0：未支付，1：已支付，2：退款）")
    private Integer payStatus;

    @ApiModelProperty("实收金额")
    private BigDecimal amount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单取消原因")
    private String cancelReason;

    @ApiModelProperty("订单拒绝原因")
    private String rejectionReason;

    @ApiModelProperty("订单取消时间")
    private LocalDateTime cancelTime;

    @ApiModelProperty("预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty("配送状态（0：预订，1：送出）")
    private Integer deliveryStatus;

    @ApiModelProperty("送达时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty("打包费")
    private int packAmount;

    @ApiModelProperty("餐具数量")
    private int tablewareNumber;

    @ApiModelProperty("餐具状态（0：选择，1：餐量）")
    private Integer tablewareStatus;

}
