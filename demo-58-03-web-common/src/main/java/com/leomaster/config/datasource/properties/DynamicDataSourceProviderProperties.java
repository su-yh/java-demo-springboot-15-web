package com.leomaster.config.datasource.properties;

import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.leomaster.constant.DataSourceNames;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suyh
 * @since 2024-03-20
 */
@ConfigurationProperties(prefix = "spring.datasource.hikari")
@Data
public class DynamicDataSourceProviderProperties implements DynamicDataSourceProvider {
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
