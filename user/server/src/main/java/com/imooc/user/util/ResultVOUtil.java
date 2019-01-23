package com.imooc.user.util;

import com.imooc.user.VO.ResultVO;
import com.imooc.user.enums.ResultEnum;

import javax.xml.crypto.Data;

public class ResultVOUtil {
    public static ResultVO error(ResultEnum resultEnum){
        ResultVO tResultVO = new ResultVO();
        tResultVO.setCode(resultEnum.getCode());
        tResultVO.setMsg(resultEnum.getMessage());
        return tResultVO;
    }
    public static ResultVO success(Object data){
        ResultVO tResultVO = new ResultVO<>();
        tResultVO.setData(data);
        tResultVO.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
        tResultVO.setMsg(ResultEnum.LOGIN_SUCCESS.getMessage());
        return tResultVO;
    }
    public static ResultVO success(){
        ResultVO tResultVO = new ResultVO<>();
        tResultVO.setCode(ResultEnum.LOGIN_SUCCESS.getCode());
        tResultVO.setMsg(ResultEnum.LOGIN_SUCCESS.getMessage());
        return tResultVO;
    }
}
