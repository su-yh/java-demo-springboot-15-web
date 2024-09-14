package com.eb.mvc.filter;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter("/**")
public class LogTraceFilter implements Filter {
    /**
     * 日志追踪
     */
    public static final String LOG_TRACE_TAG = "log-trace-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String logTraceId = IdWorker.getIdStr();
        // 生成或获取 traceId，这里使用 UUID 作为示例
        // 将 traceId 放入 MDC
        MDC.put(LOG_TRACE_TAG, logTraceId);
        response.setHeader(LOG_TRACE_TAG, logTraceId);
        filterChain.doFilter(request, response);
        MDC.remove(LOG_TRACE_TAG);
    }
}
