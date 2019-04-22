package com.tvajjala.batch.customer.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

//@Configuration//enable this if you want to use database
public class CustomerDbConfig {


    /**
     * Reference to logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDbConfig.class);


    @Autowired
    Environment env;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource customerDataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(customerDataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScripts(new ClassPathResource("scripts/ddl.sql"),
                new ClassPathResource("scripts/dml.sql"));
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
