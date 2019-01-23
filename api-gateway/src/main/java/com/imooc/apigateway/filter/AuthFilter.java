///*package com.imooc.apigateway.filter;
//
//import com.imooc.apigateway.constants.CookieConstants;
//import com.imooc.apigateway.constants.RedisContant;
//import com.imooc.apigateway.utils.CookieUtil;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang.StringUtils;
//import org.apache.http.HttpStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//*//**
// * 权限拦截(区分买家和卖家)
// *//*
////常量看FilterConstants类，数字越小过滤器优先级越高
//@Component
//public class AuthFilter extends ZuulFilter {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;//越小优先级越高
//    }
//
//    @Override
//    public boolean shouldFilter() { //开启该过滤器
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {//自定义逻辑:可以根据数据库或者redis进行校验登陆或者其他认证信息等
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        *//**
//         * /order/create 只能买家访问(cookie有openid)
//         * /order/finish 只能卖家访问(cookie里有token，并且redis里面有值)
//         * /product/list 都可以访问
//         *//*
//        String requestURI = request.getRequestURI();
//        if(requestURI.equals("/order/order/create")){
//            //买家校验
//            Cookie cookie = CookieUtil.get(request, CookieConstants.OPENID);
//            if(cookie==null || StringUtils.isBlank(cookie.getValue())){
//                //无权限
//                currentContext.setSendZuulResponse(false);
//                currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
//            }
//        }else if(requestURI.equals("/order/order/finish")){
//            //卖家校验
//            Cookie cookie = CookieUtil.get(request, CookieConstants.TOKEN);
//            if(cookie==null || StringUtils.isBlank(cookie.getValue())
//              || StringUtils.isBlank(stringRedisTemplate.opsForValue().get(String.format(RedisContant.TOKEN_KEY,cookie.getValue())))){
//                //无权限
//                currentContext.setSendZuulResponse(false);
//                currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
//            }
//        }else{
//            //所有都可以访问
//        }
//        return null;
//    }
//}*/
