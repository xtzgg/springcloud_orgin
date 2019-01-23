package com.imooc.user.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
     ROLE_BUYER(1,"买家"),
    ROLE_SELLER(2,"卖家"),
    ;
    private Integer code;
    private String message;

    RoleEnum(Integer code,String message){
        this .code = code;
        this.message = message;
    }
}
