package com.imooc.user.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    LOGIN_ERROR(1,"登陆失败"),
    LOGIN_SUCCESS(3,"登陆成功"),
    ROLE_ERROR(2,"角色权限不足"),
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code,String message){
        this .code = code;
        this.message = message;
    }

}
