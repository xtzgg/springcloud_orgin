package com.imooc.user.service.impl;

import com.imooc.user.entity.UserInfo;
import com.imooc.user.repository.UserInfoRepository;
import com.imooc.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
