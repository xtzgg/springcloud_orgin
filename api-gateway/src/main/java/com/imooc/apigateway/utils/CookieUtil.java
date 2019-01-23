package com.imooc.apigateway.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    /**
     * 从cookie中获取
     */
    public static Cookie get(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName() == name){
                    return cookie;
                }
            }
        }
        return null;
    }
}
