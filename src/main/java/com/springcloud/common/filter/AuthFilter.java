package com.springcloud.common.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义过滤器
 */
@Component
public class AuthFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(AuthFilter.class);
    /**
     * 过滤器的类型
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序 数值越小，执行优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        // 多个同一类型过滤器，根据返回整数值优先级执行
        return 0;
    }

    /**
     * 过滤器是否有效
     * false : 无效
     * true : 有效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        // 获取全局对象 RequestContext
        RequestContext rc = RequestContext.getCurrentContext();
        // 可以根据请求参数来决定过滤器是否执行，即执行以下的 run 方法
        return true;
    }

    /**
     * 自定义处理逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取全局对象 RequestContext
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        log.info("====================PreFilter=====================");
        log.info("访问路径：" + request.getRequestURI() + ",访问方式：" + request.getMethod());

        // 验证请求的数据
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)){
            rc.setSendZuulResponse(false);  // 表示不对该请求进行路由
            rc.setResponseStatusCode(401);
            rc.addZuulResponseHeader("content-type","application/json;charset=utf-8");
            rc.setResponseBody("{\"success\":0" + ",\"code\":401" + ",\"message\":\"" + "非法访问\"}");
        }
        // 向 RequestContext 对象中添加数据
        rc.set("filterName","PreFilter");

        return null;
    }
}
