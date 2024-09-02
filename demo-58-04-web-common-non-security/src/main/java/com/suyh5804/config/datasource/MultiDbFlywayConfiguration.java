/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.suyh5804.config.datasource;

import com.suyh5804.config.base.properties.BaseProperties;
import com.suyh5804.config.datasource.properties.DynamicDataSourceProviderProperties;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootConfiguration
@RequiredArgsConstructor
public class MultiDbFlywayConfiguration {

    @ConditionalOnProperty(name = "community.base.flyway-cds-mysql.enabled", havingValue = "true")
    @Bean("cdsMysqlFlyway")
    public FlywayMigrationInitializer cdsMysqlFlyway(
            DynamicDataSourceProviderProperties dynamicDataSourceProviderProperties,
            BaseProperties baseProperties) {
        String[] locations = baseProperties.getFlywayCdsMysql().getLocations();
        FluentConfiguration cdsWebFlywayConfig = new FluentConfiguration();
        cdsWebFlywayConfig.baselineOnMigrate(true)
                .dataSource(dynamicDataSourceProviderProperties.getCdsMysql())
                .locations(locations)
                .table("flyway_schema_history")
                .validateOnMigrate(true)
                .ignoreFutureMigrations(true)
                .outOfOrder(true);
        Flyway cdsWebFlyway = cdsWebFlywayConfig.load();
        return new FlywayMigrationInitializer(cdsWebFlyway, null);
    }

    @ConditionalOnProperty(name = "community.base.flyway-cds-pgsql.enabled", havingValue = "true")
    @Bean("cdsPgsqlFlyway")
    public FlywayMigrationInitializer pgFlyway(
            DynamicDataSourceProviderProperties dynamicDataSourceProviderProperties,
            BaseProperties baseProperties) {
        String[] locations = baseProperties.getFlywayCdsPgsql().getLocations();
        FluentConfiguration cds2FlywayConfig = new FluentConfiguration();
        cds2FlywayConfig.baselineOnMigrate(true)
                .dataSource(dynamicDataSourceProviderProperties.getCdsPgsql())
                .locations(locations)
                .table("flyway_schema_history")
                .validateOnMigrate(true)
                .ignoreFutureMigrations(true)
                .outOfOrder(true);

        Flyway cds2Flyway = cds2FlywayConfig.load();
        return new FlywayMigrationInitializer(cds2Flyway, null);
    }
}
