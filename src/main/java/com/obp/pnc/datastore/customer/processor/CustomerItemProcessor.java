package com.obp.pnc.datastore.customer.processor;

import com.obp.pnc.datastore.customer.model.Customer;
import org.springframework.batch.item.ItemProcessor;

/**
 * Useful for intermediate processing
 *
 * @author ThirupathiReddy
 */
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {


    @Override
    public Customer process(Customer customer) {
        return customer;
    }
}
