package com.obp.pnc.datastore.common;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

/**
 * This is to avoid datastore persistence. otherwise spring datastore expect set of tables to persist the jobStatus,
 * which is
 * not required at this stage.
 *
 * @author ThirupathiReddy Vajjala
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {


    @Override
    public void setDataSource(DataSource customerDataSource) {
        //super.setDataSource(dataSource);//
    }


}
