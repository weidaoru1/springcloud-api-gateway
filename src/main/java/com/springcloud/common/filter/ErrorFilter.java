package com.springcloud.common.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class ErrorFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(ErrorFilter.class);
    @Override
    public String filterType() {
        return "error";
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
        log.info("=============ErrorFilter==============");
        // 异常信息可以记录到数据库
        try {
            RequestContext context = RequestContext.getCurrentContext();
            ZuulException exception = (ZuulException)context.getThrowable();
            log.error("进入系统异常拦截：" + exception);
            HttpServletResponse response = context.getResponse();
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(exception.nStatusCode);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("{\"success\":0" + ",\"code\":" + exception.nStatusCode + ",\"message\":\"" + exception.getMessage() + "\"}");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (writer != null){
                    writer.close();
                }
            }
        } catch (Exception e) {
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }
}
