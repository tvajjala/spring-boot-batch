package com.obp.pnc.datastore.loc.config;

import com.obp.pnc.datastore.customer.config.CustomerDbConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;


@Profile("loc")
@Configuration
public class LocDbConfig {


    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDbConfig.class);


    @Autowired
    Environment env;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource locDataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(locDataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("scripts/pnc_openapi_loc_schema.sql"),
                                     new ClassPathResource("scripts/pnc_openapi_loc_schema_data.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }


    @Bean
    public DataSource locDataSource() {
        LOGGER.info("Creating LOC dataSource  using HikariDataSource");
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl(env.getProperty("schemas.loc.url"));
        dataSource.setUsername(env.getProperty("schemas.loc.username"));
        dataSource.setPassword(env.getProperty("schemas.loc.password"));
        return dataSource;
    }

}
