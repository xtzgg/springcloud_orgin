package com.imooc.order.controller;

import com.imooc.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//这里就不用写scope刷新了
@RestController
public class GirlController {

    @Autowired
    GirlConfig girlConfig;

    @GetMapping("/girl")
    public String girl(){
        return "name:"+ girlConfig.getName() +" age:" + girlConfig.getAge();
    }
}
