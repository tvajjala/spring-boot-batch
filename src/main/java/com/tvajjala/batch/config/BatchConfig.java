package com.tvajjala.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * SpringBoot Batch Configuration without batch database tables
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void setDataSource(DataSource customerDataSource) {
        //super.setDataSource(dataSource);//enable this to persist batch processing state
        LOG.info("Disable DataSource configuration");//
    }


}
