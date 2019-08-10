package com.destiny.elephant.constant;

public enum  UserEnum {

   PARENT_ID(0,"一级菜单父id为0");

    UserEnum(Integer code,String detail) {
        this.code = code;
        this.detail = detail;
    }

    private Integer code;
    private String detail;

}
