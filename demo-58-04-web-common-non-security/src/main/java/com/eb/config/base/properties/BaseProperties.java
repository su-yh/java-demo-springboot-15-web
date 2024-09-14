package com.eb.config.base.properties;

import com.eb.config.base.properties.nested.CaptchaProperties;
import com.eb.config.base.properties.nested.CommunityFlywayProperties;
import com.eb.config.base.properties.nested.FileUploadProperties;
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
    public static final String PREFIX = "eb.base";

    /**
     * token 的有效时间
     */
    private Integer tokenSeconds = 30 * 60;

    @NestedConfigurationProperty
    @Valid
    private CommunityFlywayProperties flywayCdsMysql = new CommunityFlywayProperties();

    @NestedConfigurationProperty
    @Valid
    private CommunityFlywayProperties flywayCdsPgsql = new CommunityFlywayProperties();

    @NestedConfigurationProperty
    @Valid
    private FileUploadProperties fileUpload = new FileUploadProperties();

    @NestedConfigurationProperty
    @Valid
    private CaptchaProperties captcha = new CaptchaProperties();
}
