package com.imooc.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("girl")
@RefreshScope//其实就是范围刷新，如果要刷新某个参数，则需要加到参数所在的方法或者类上
//对比value = ${}
public class GirlConfig {
    private Integer age;
    private String name;
}
