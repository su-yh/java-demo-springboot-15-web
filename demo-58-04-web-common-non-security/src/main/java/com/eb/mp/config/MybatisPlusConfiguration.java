package com.eb.mp.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.eb.mp.handler.SqlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 * 
 * @author ruoyi
 */
@Configuration
public class MybatisPlusConfiguration {
    @Bean
    public SqlHandler sqlHandler() {
        return new SqlHandler();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
