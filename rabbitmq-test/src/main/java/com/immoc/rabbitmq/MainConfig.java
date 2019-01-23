package com.immoc.rabbitmq;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 两个order的包名一定要一致，否则就会报错，找不到order对象
 */
@Configuration
@ComponentScan({"com.immoc.rabbitmq.*"})
public class MainConfig {

}
