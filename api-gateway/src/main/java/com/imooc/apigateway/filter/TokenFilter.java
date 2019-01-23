package com.imooc.apigateway.filter;

import com.imooc.apigateway.exception.AuthenticaionException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 利用pre过滤器，进行权限校验
 */
//常量看FilterConstants类，数字越小过滤器优先级越高
@Component
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;//越小优先级越高
    }

    @Override
    public boolean shouldFilter() { //开启该过滤器
        return true;
    }

    @Override
    public Object run() throws ZuulException {//自定义逻辑:可以根据数据库或者redis进行校验登陆或者其他认证信息等
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        Cookie[] cookies = request.getCookies();
//        String token = request.getParameter("token");
//        if(StringUtils.isBlank(token)){
////            throw new AuthenticaionException("无权限访问！");
//            currentContext.setSendZuulResponse(false);//不通过
//            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);//这个地方有提供的常量码 xue_xi
//        }
        return "this is a value";
    }
}
