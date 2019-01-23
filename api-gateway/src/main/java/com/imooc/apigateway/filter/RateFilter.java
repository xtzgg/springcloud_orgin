package com.imooc.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.imooc.apigateway.exception.AuthenticaionException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@Component
public class RateFilter extends ZuulFilter {

    private static final RateLimiter RATELIMITER = RateLimiter.create(1);
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER-1; //限流优先级最高，所以要比-3还要小
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {//具体实现
        if(!RATELIMITER.tryAcquire()){
//            throw new AuthenticaionException("访问太多了");
            RequestContext currentContext = RequestContext.getCurrentContext();
            currentContext.setResponseStatusCode(HttpStatus.SC_BAD_GATEWAY);
            currentContext.setSendZuulResponse(false);
        }
        return null;
    }
}
