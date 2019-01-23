package com.imooc.order;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
@SpringCloudApplication //springCloud包含了以上三个注解
@EnableFeignClients
@EnableHystrixDashboard
//启动config-client不需要设置启动配置
public class OrderApplication {
		public static void main(String[] args) {
				SpringApplication.run(OrderApplication.class, args);
		}
}

