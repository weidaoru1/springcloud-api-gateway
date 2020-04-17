package com.springcloud.common.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PostFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(PostFilter.class);
    @Override
    public String filterType() {
        return "post";
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
        log.info("=====================PostFilter==============");
       // int a = 6/0;
        // 获取全局对象 RequestContext
        RequestContext rc = RequestContext.getCurrentContext();
        String filterName = (String)rc.get("filterName");
        String filterName2 = (String)rc.get("filterName2");
        log.info("filterName:" + filterName + ",filterName2:" + filterName2);
        return null;
    }
}
