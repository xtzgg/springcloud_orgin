package com.imooc.order.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //加上该注解才能刷新该变量
public class EnvController {
    @Value("${env}")
    private String env;
    @GetMapping("/print")
    public String print(){
        return env;
    }
}
