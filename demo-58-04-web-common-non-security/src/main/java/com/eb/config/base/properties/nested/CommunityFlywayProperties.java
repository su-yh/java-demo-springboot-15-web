package com.eb.config.base.properties.nested;

import lombok.Data;

/**
 * @author suyh
 * @since 2024-02-23
 */
@Data
public class CommunityFlywayProperties {
    private boolean enabled = false;

    private String[] locations;
}
