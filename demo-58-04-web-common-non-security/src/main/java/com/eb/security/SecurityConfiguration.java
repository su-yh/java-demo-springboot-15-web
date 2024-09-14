package com.eb.security;

import com.eb.security.permission.PermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author suyh
 * @since 2024-09-14
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean("ss")
    public PermissionService ss() {
        return new PermissionService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().disable();

        http.logout().disable();

        // 不使用spring security 的认证系统
        http.authorizeRequests().antMatchers("/**").permitAll()
                .and().csrf().disable();   // 关闭csrf 防护

    }
}
