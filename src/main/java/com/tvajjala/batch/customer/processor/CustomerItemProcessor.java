package com.tvajjala.batch.customer.processor;

import com.tvajjala.batch.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Useful for intermediate processing
 *
 * @author ThirupathiReddy Vajjala
 */
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public Customer process(Customer customer) {

        LOG.info("Processing customer {} ", customer);

        return customer;
    }
}
