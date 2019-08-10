package com.destiny.elephant;

import java.util.HashMap;
import java.util.Map;

public enum  TestEnum {

    CHECK_STATUS_NONE(0, "尚未进入审单流程"),
    PENDING(1, "订单审核中"),
    SHIPPING(2, "配送中"),
    FINISH(3, "已完成"),
    INVALID(4, "取消"),
    DELETE(5, "删除"),
    SEPARATE(6, "已拆单"),
    OUTBOUND(7, "出库中"),
    CHECK_STATUS_FINISH(9, "审单流程结束"),
    WAITBUYERPAY(10, "未支付"),
    // STATUS_TRADE_SUCCESS(11, "已支付"),
    STATUS_TRADE_SUCCESS(1, "已支付"),
    DELIVERED(20, "已送达"),
    REJECTION(21, "已拒签"),
    REFUNDPENDING(90, "退款审核中"),
    REFUNDCOMPLETE(91, "已退款");

    private int id;
    private String value;

    TestEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public static Map<Integer, String> maps = new HashMap<>();

    static {
        for (TestEnum orderStatus : TestEnum.values()) {
            maps.put(orderStatus.getId(), orderStatus.getValue());
        }
    }

    public String getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public static void main(String[] args) {

        Map<Integer, String> map =  TestEnum.maps;
        for (Map.Entry<Integer,String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "---------" + entry.getValue());
        }


    }



}

