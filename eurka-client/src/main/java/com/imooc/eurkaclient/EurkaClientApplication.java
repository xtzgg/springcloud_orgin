package com.imooc.eurkaclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient  //这个两个配置作用是一样的，取决于注册中心，
						  // 如果是eureka注册中心可以用上边的，如果不是用discovery
public class EurkaClientApplication {

		public static void main(String[] args) {
				SpringApplication.run(EurkaClientApplication.class, args);
		}

}
/**
 * 总结：
 * 	服务端：application配置文件和启动类注解
 * 	客户端：application配置文件和启动类注解
 * 	一些注意问题
 * 	所有需要注意的都标注了清除
 */
