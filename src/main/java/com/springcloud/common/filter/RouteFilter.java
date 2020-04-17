package com.springcloud.common.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RouteFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(RouteFilter.class);
    @Override
    public String filterType() {
        // 路由类型过滤器
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("==================RouteFilter===================");
        // 获取全局对象 RequestContext
        RequestContext rc = RequestContext.getCurrentContext();
        String filterName = (String) rc.get("filterName");
        log.info("filterName:" + filterName);

        // 向 RequestContext 对象中添加数据
        rc.set("filterName2","RouteFilter");
        return null;
    }
}
