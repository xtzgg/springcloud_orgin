package com.imooc.apigateway.config;

import com.imooc.apigateway.constants.CookieConstants;
import com.imooc.apigateway.utils.CookieUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * zuul跨域配置：
 * C - Cross O - Origin R - Resource S - Sharing  跨域资源表
 * ajax跨域完全讲解
 *  具体看慕课网上的ajax跨域完全讲解
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
       final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);//允许cookie跨域
        config.setAllowedOrigins(Arrays.asList("*"));//设置原始域名 如http://www.a.com
        config.setAllowedHeaders(Arrays.asList("*"));//允许所有请求头
        config.setAllowedMethods(Arrays.asList("*"));//get或者post请求等
        config.setMaxAge(200L);//设置缓存时间：这段时间对于相同的请求就不会再次进行检查了
        source.registerCorsConfiguration("/*",config);//对所有路径有效
        return new CorsFilter(source);
    }
}
