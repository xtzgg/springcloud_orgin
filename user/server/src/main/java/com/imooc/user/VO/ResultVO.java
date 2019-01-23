package com.imooc.user.VO;

import com.imooc.user.enums.ResultEnum;
import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;

}
