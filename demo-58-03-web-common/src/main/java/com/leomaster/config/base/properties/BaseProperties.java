package com.leomaster.config.base.properties;

import com.leomaster.config.base.properties.nested.CommunityFlywayProperties;
import com.leomaster.config.base.properties.nested.LocaleProperties;
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
     * 国际化配置，语言以及国家
     */
    @NestedConfigurationProperty
    @Valid
    private LocaleProperties[] locales = {};
}
