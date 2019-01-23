package com.imooc.user.repository;

import com.imooc.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    /**
     * 通过openid查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);

}
