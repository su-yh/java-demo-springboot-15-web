/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 */

package com.leomaster.config.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;


@SpringBootConfiguration
@RequiredArgsConstructor
public class MultiDbFlywayConfiguration {

//    @ConditionalOnProperty(name = "cds.base.flyway-web.enabled", havingValue = "true")
//    @Bean("cdsWebFlyway")
//    public FlywayMigrationInitializer cdsWebFlyway(
//            DynamicDataSourceProviderProperties dynamicDataSourceProviderProperties,
//            CdsProperties cdsProperties) {
//        String[] locations = cdsProperties.getFlywayWeb().getLocations();
//        FluentConfiguration cdsWebFlywayConfig = new FluentConfiguration();
//        cdsWebFlywayConfig.baselineOnMigrate(true)
//                .dataSource(dynamicDataSourceProviderProperties.getCdsWeb())
//                .locations(locations)
//                .table("flyway_schema_history")
//                .validateOnMigrate(true)
//                .ignoreFutureMigrations(true)
//                .outOfOrder(true);
//        Flyway cdsWebFlyway = cdsWebFlywayConfig.load();
//        return new FlywayMigrationInitializer(cdsWebFlyway, null);
//    }
//
//    @ConditionalOnProperty(name = "cds.base.flyway-cds2.enabled", havingValue = "true")
//    @Bean("cds2Flyway")
//    public FlywayMigrationInitializer cds2Flyway(
//            DynamicDataSourceProviderProperties dynamicDataSourceProviderProperties,
//            CdsProperties cdsProperties) {
//        String[] locations = cdsProperties.getFlywayCds2().getLocations();
//        FluentConfiguration cds2FlywayConfig = new FluentConfiguration();
//        cds2FlywayConfig.baselineOnMigrate(true)
//                .dataSource(dynamicDataSourceProviderProperties.getCds2())
//                .locations(locations)
//                .table("flyway_schema_history")
//                .validateOnMigrate(true)
//                .ignoreFutureMigrations(true)
//                .outOfOrder(true);
//
//        Flyway cds2Flyway = cds2FlywayConfig.load();
//        return new FlywayMigrationInitializer(cds2Flyway, null);
//    }
//
//    @ConditionalOnProperty(name = "cds.base.flyway-pg.enabled", havingValue = "true")
//    @Bean("pgFlyway")
//    public FlywayMigrationInitializer pgFlyway(
//            DynamicDataSourceProviderProperties dynamicDataSourceProviderProperties,
//            CdsProperties cdsProperties) {
//        String[] locations = cdsProperties.getFlywayPg().getLocations();
//        FluentConfiguration cds2FlywayConfig = new FluentConfiguration();
//        cds2FlywayConfig.baselineOnMigrate(true)
//                .dataSource(dynamicDataSourceProviderProperties.getCdsPg())
//                .locations(locations)
//                .table("flyway_schema_history")
//                .validateOnMigrate(true)
//                .ignoreFutureMigrations(true)
//                .outOfOrder(true);
//
//        Flyway cds2Flyway = cds2FlywayConfig.load();
//        return new FlywayMigrationInitializer(cds2Flyway, null);
//    }


}
