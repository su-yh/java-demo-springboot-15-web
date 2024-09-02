package com.suyh.config.base.properties;

import com.suyh.config.base.properties.nested.CommunityFlywayProperties;
import com.suyh.config.base.properties.nested.LocaleProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * @author suyh
 * @since 2024-08-28
 */
@ConfigurationProperties(prefix = BaseProperties.PREFIX)
@Validated
@Data
public class BaseProperties {
    public static final String PREFIX = "community.base";

    /**
     * token 的有效时间
     */
    private Integer tokenSeconds = 30 * 60;

    @NestedConfigurationProperty
    @Valid
    private CommunityFlywayProperties flywayCdsMysql = new CommunityFlywayProperties();

    /**
     * 国际化配置，语言以及国家，这个主要是跟前端需要保持一致，如果默认情况下没有使用国际惯例，那还可以自己定义一套。
     */
    @NestedConfigurationProperty
    @Valid
    private LocaleProperties[] locales = {};
}
