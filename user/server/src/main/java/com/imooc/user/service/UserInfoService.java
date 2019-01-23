package com.imooc.user.service;

import com.imooc.user.entity.UserInfo;

public interface UserInfoService {
    UserInfo findByOpenid(String openid);
}
