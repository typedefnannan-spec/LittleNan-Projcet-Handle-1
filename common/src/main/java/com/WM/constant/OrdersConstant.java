package com.WM.constant;

public class OrdersConstant {

    //订单状态（1：待付款，2：待接单，3：已接单，4：派送中，5：已完成，6：已取消）
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    //支付状态（0：未支付，1：已支付，2：退款）
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    //提醒（1：来单提醒，2：用户催单）
    public static final Integer HAVE_NEW_ORDER=1;
    public static final Integer USER_REMINDER=2;

}
