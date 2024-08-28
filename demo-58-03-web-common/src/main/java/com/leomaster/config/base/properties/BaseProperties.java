package com.leomaster.config.base.properties;

import com.leomaster.business.vo.UserInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suyh
 * @since 2024-08-28
 */
@ConfigurationProperties(prefix = BaseProperties.PREFIX)
@Validated
@Data
public class BaseProperties {
    public static final String PREFIX = "data.query.base";

    @Valid
    @NotEmpty
    private List<UserInfo> users = new ArrayList<>();
}
