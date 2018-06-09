package com.obp.pnc.datastore.customer.config;

import static com.obp.pnc.datastore.customer.writer.Constants.CHUNK;

import com.obp.pnc.datastore.customer.model.Customer;
import com.obp.pnc.datastore.customer.processor.CustomerItemProcessor;
import com.obp.pnc.datastore.customer.writer.CustomerDBWriter;
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
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

/**
 * Customer database population dataStore job configuration
 *
 * @author ThirupathiReddy V
 */
@Configuration
@Profile("customer")
public class CustomerBatchConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBatchConfig.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    /**
     * job used to populate customer database
     *
     * @return customerJob
     */
    @Bean
    public Job customerJob() {
        return jobBuilderFactory
            .get("customerJob")
            .incrementer(new RunIdIncrementer())
            /** populate customer database */
            .start(customerStep())
            .build();
    }


    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
        return new FlatFileItemReaderBuilder<Customer>()
            .name("customerItemReader")
            .resource(new ClassPathResource("source/customer/customers.csv"))
            .delimited()
            .names(new String[]{"id", "firstName", "lastName", "username", "password", "phoneNumber", "addressLine1",
                "city", "state", "zip", "ssn"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {{
                setTargetType(Customer.class);
            }})
            .linesToSkip(1)
            .build();
    }


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
    ItemWriter<? super Customer> customerDBWriter() {
        return new CustomerDBWriter();
    }

    @Bean
    public ItemProcessor processor() {
        return new CustomerItemProcessor();
    }


}
