package com.tvajjala.batch.customer.writer;

import com.tvajjala.batch.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * ItemWriter to persistent in desired format
 *
 * @author ThirupathiReddy Vajjala
 */
public class CustomerWriter implements ItemWriter<Customer> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerWriter.class);


    @Override
    public void write(List<? extends Customer> customers) {

        LOG.info("Received customers {} ", customers);// save into database /different file

    }


}
