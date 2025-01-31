package com.suyh.config.datasource;

import com.suyh.config.datasource.properties.DynamicDataSourceProviderProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author suyh
 * @since 2024-03-20
 */
@EnableConfigurationProperties(DynamicDataSourceProviderProperties.class)
@Configuration
public class DynamicDataSourceConfiguration {
}
