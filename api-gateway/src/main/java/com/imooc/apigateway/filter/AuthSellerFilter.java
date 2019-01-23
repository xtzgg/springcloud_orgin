package com.imooc.apigateway.filter;

import com.imooc.apigateway.constants.CookieConstants;
import com.imooc.apigateway.constants.RedisContant;
import com.imooc.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限拦截
 * 这样改造更加清晰，将AuthFilter复制一份
 * 实际：api获取用户信息，不可能直接从数据库取
 *          需要调用用户服务；但是调用用户服务，也不可能再从数据库中读取，
 *          所以方案一：从redis缓存中读取
 *                  考虑同步策略：如果用户登录，或者修改信息，这可以使用rabbitMQ异步的进行更新缓存用户数据，做到一致性
 */
//常量看FilterConstants类，数字越小过滤器优先级越高
@Component
public class AuthSellerFilter extends ZuulFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;//越小优先级越高
    }

    @Override
    public boolean shouldFilter() { //需要使用该过滤器，则true。否则为false
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        if(requestURI.equals("/order/order/create")){
            return true;//如果是创建订单则为过滤
        }
        return false;//放行
    }

    @Override
    public Object run() throws ZuulException {//自定义逻辑:可以根据数据库或者redis进行校验登陆或者其他认证信息等
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        /**
         * /order/create 只能买家访问(cookie有openid)
         * /order/finish 只能卖家访问(cookie里有token，并且redis里面有值)
         * /product/list 都可以访问
         */
        //买家校验
        Cookie cookie = CookieUtil.get(request, CookieConstants.OPENID);
        if(cookie==null || StringUtils.isBlank(cookie.getValue())){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }
        return null;
    }
}
