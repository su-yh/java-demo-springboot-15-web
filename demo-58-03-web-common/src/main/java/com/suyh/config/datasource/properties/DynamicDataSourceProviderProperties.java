package com.suyh.config.datasource.properties;

import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.suyh.constant.DataSourceNames;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suyh
 * @since 2024-03-20
 */
@ConfigurationProperties(prefix = "spring.datasource.hikari")
@Data
@Validated
public class DynamicDataSourceProviderProperties implements DynamicDataSourceProvider {
    @NotNull
    @NestedConfigurationProperty
    private HikariDataSource cdsMysql;

    private Map<String, DataSource> mapDatasource = new HashMap<>();

    @PostConstruct
    public void init() {
        mapDatasource.put(DataSourceNames.CDS_MYSQL, cdsMysql);
    }

    @Override
    public synchronized Map<String, DataSource> loadDataSources() {
        return mapDatasource;
    }
}
