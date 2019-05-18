package com.tvajjala.batch.config;

import com.tvajjala.batch.config.beans.AsyncJobLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

/**
 * SpringBoot Batch Configuration without batch database tables
 *
 * @author ThirupathiReddy Vajjala DefaultBatchConfigurer
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    private Logger LOG = LoggerFactory.getLogger(getClass());


    @Autowired
    public BatchConfig(DataSource dataSource) {
        setDataSource(dataSource);
    }


    @Bean
    public JobLauncher jobLauncher(final JobRepository jobRepository, final TaskExecutor taskExecutor) {
        final SimpleJobLauncher jobLauncher = new AsyncJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor);
        return jobLauncher;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        LOG.info("Creating new Task Executor");
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setDaemon(false);
        taskExecutor.setThreadNamePrefix("Batch");
        taskExecutor.setThreadPriority(Thread.MIN_PRIORITY);
        return taskExecutor;
    }


}
