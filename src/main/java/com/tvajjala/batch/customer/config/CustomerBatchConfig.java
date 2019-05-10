package com.tvajjala.batch.customer.config;

import com.tvajjala.batch.customer.model.Customer;
import com.tvajjala.batch.customer.processor.CustomerItemProcessor;
import com.tvajjala.batch.customer.writer.CustomerWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author ThirupathiReddy Vajjala
 */
@Configuration
public class CustomerBatchConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBatchConfig.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static int CHUNK = 2;// Number of records to read from ItemReader before it send to ItemWriter


    /**
     * Job contains multiple Steps and each steps contains three stages (read, process, write)
     *
     * @return Job
     */
    @Bean
    public Job customerJob() {
        return jobBuilderFactory
                .get("customerJob")
                .incrementer(new RunIdIncrementer())
                .start(customerStep())
                .build();
    }


    /**
     * other job
     *
     * @return job
     */
    @Bean
    public Job userJob() {
        return jobBuilderFactory
                .get("userJob")
                .incrementer(new RunIdIncrementer())
                .start(userStep())
                .build();
    }


    /**
     * Each Step contains three stages
     *
     * @return Step
     */
    @Bean
    public Step customerStep() {
        return stepBuilderFactory
                .get("populateCustomer")
                .chunk(CHUNK)
                .reader(customerItemReader())
                .processor(processor())
                .writer(customerDBWriter())
                .exceptionHandler((context, throwable) -> LOGGER.error("  {} ", throwable.getMessage()))
                .build();
    }

    @Bean
    public Step userStep() {
        return stepBuilderFactory
                .get("userRead")
                .chunk(CHUNK)
                .reader(userItemReader())
                .processor(userProcessor())
                .writer(userDBWriter())
                .exceptionHandler((context, throwable) -> LOGGER.error("  {} ", throwable.getMessage()))
                .build();
    }


    @Bean
    public FlatFileItemReader<Customer> userItemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("userItemReader")
                .resource(new ClassPathResource("user.csv"))
                .delimited()
                .names(new String[]{"id", "username"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {{
                    setTargetType(Customer.class);
                }})
                .linesToSkip(1)// first line CSV file contains headers but not actual data
                .build();
    }

    /**
     * Reading each record from the CSV file
     *
     * @return
     */
    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .resource(new ClassPathResource("customer.csv"))
                .delimited()
                .names(new String[]{"id", "username"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {{
                    setTargetType(Customer.class);
                }})
                .linesToSkip(1)// first line CSV file contains headers but not actual data
                .build();
    }


    @Bean
    ItemWriter<? super Customer> userDBWriter() {
        return new CustomerWriter();
    }

    @Bean
    ItemWriter<? super Customer> customerDBWriter() {
        return new CustomerWriter();
    }

    @Bean
    public ItemProcessor processor() {
        return new CustomerItemProcessor();
    }


    @Bean
    public ItemProcessor userProcessor() {
        return new CustomerItemProcessor();
    }


}
