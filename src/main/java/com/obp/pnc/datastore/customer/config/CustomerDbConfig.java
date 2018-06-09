package com.obp.pnc.datastore.customer.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@Profile("customer")
public class CustomerDbConfig {


    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDbConfig.class);


    @Autowired
    Environment env;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource customerDataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(customerDataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("scripts/pnc_openapi_customer_schema.sql"),
                                     new ClassPathResource("scripts/pnc_openapi_customer_schema_data.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(true);
        return dataSourceInitializer;
    }


    @Bean
    @Primary // batch requires on dataSource
    public DataSource customerDataSource() {
        LOGGER.info("Creating custom dataSource  using HikariDataSource");
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl(env.getProperty("schemas.customer.url"));
        dataSource.setUsername(env.getProperty("schemas.customer.username"));
        dataSource.setPassword(env.getProperty("schemas.customer.password"));
        return dataSource;
    }


}
