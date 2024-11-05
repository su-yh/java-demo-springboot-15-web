package com.eb.mvc.filter;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eb.constant.CommunityConstants;
import com.eb.mp.handler.SqlHandler;
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
import java.util.ArrayList;

@Component
@WebFilter("/**")
public class TraceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Long traceId = IdWorker.getId();
        // 生成或获取 traceId，这里使用 UUID 作为示例
        // 将 traceId 放入 MDC
        MDC.put(CommunityConstants.TRACE_ID, traceId + "");
        response.setHeader(CommunityConstants.TRACE_ID, traceId + "");
        request.setAttribute(CommunityConstants.TRACE_ID, traceId);
        SqlHandler.AUDIT_SQL_LIST.set(new ArrayList<>());

        try {
            filterChain.doFilter(request, response);
        } finally {
            SqlHandler.AUDIT_SQL_LIST.remove();
            MDC.remove(CommunityConstants.TRACE_ID);
        }
    }
}
