package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * 1 2 3 4 代表讲课的顺序
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback") //3 不用每一个方法都写该@HystrixCommand注解，提供有默认的回调函数
public class HystrixController {
    //@HystrixCommand(fallbackMethod = "fallback") //1 将product停掉，这个时候调用回调函数
//    @HystrixCommand(commandProperties = {                                           //该超时时间设置具体业务来看
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
//    })
    //配置断路器 -->转移至配置文件
    @HystrixCommand(commandProperties ={ //postman测试100个请求
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")
    })
    //@HystrixCommand
    @GetMapping("getProductList")
    public String getProductList(@RequestParam("num") Integer num){
        if(num %2 == 0 ){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.postForObject("http://localhost:2002/product/listForOrder", Arrays.asList("13243nfweigwe32323"), String.class);
        return s;
        //throw new RuntimeException();//2 抛出一个异常：这个可以用做内部的服务熔断机制，比如数据库连接数过多抛出异常等信息
    }
    private String fallback(){
        return "访问太过频繁，请稍后重试！";
    }
    private String defaultFallback(){ //3 默认回调函数，该类每个有hystrix注解的都会回调该函数
        return "默认提示：访问太过频繁，请稍后重试！";
    }
    //4 在product工程调用的Controller接口中休眠2秒，看是否会调用回调方法
    //始终会调用默认的回调函数：
    //原因：看源码包：Hystrix包，查看下边的所有类，可以看到超时时间不设置时，默认1秒超时
}
