package com.imooc.eurka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 1   3-2
 * 添加注解到启动类上，作为服务注册中心，启动即可
 */
@SpringBootApplication
@EnableEurekaServer
public class EurkaApplication {
		public static void main(String[] args) {
				SpringApplication.run(EurkaApplication.class, args);
		}

}

