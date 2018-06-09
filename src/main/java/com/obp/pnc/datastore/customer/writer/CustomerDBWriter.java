package com.obp.pnc.datastore.customer.writer;

import static com.obp.pnc.datastore.common.SerializationUtil.serialize;

import com.obp.pnc.datastore.customer.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class CustomerDBWriter implements ItemWriter<Customer>, InitializingBean {

    /** Reference to logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDBWriter.class);

    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    DataSource customerDataSource;

    @Override
    public void afterPropertiesSet() {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(customerDataSource);
    }


    @Override
    public void write(List<? extends Customer> customers) {
        insertParties(customers);
        insertPersons(customers);
        insertPartyLogins(customers);
        insertAddresses(customers);
        insertTelephoneNumbers(customers);
        insertUserSecurityQuestions(customers);

        serializePartyIds();

    }


    private void serializePartyIds() {

        List<Map<String, Object>> rows = jdbcTemplate
            .getJdbcTemplate()
            .queryForList("SELECT cif_key FROM parties");

        List<String> partyIds = new ArrayList<>();

        for (Map<String, Object> map : rows) {
            partyIds.add(String.valueOf(map.get("cif_key")));
        }

        serialize(partyIds);

    }

    private void insertUserSecurityQuestions(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("partyId", Constants.SKIP_INDEX + customer.getId())
                                .addValue("qid", "2")
                                .addValue("answer", "911")
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_USER_SECURITY_QUESTIONS,
                                 batchValues.toArray(new Map[customers.size()]));

    }

    private void insertTelephoneNumbers(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("partyId", Constants.SKIP_INDEX + customer.getId())
                                .addValue("type", "HOME")
                                .addValue("status", "ACTIVE")
                                .addValue("phoneNumber", customer.getPhoneNumber())
                                .addValue("countryCode", "1")
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_TELEPHONE, batchValues.toArray(new Map[customers.size()]));

    }

    private void insertAddresses(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("partyId", Constants.SKIP_INDEX + customer.getId())
                                .addValue("addressLine1", customer.getAddressLine1())
                                .addValue("city", customer.getCity())
                                .addValue("state", customer.getState())
                                .addValue("zip", customer.getZip())
                                .addValue("countryCode", "US")
                                .addValue("type", "HOME")

                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_ADDRESS, batchValues.toArray(new Map[customers.size()]));

    }

    private void insertPartyLogins(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("status", "ACTIVE")
                                .addValue("username", customer.getUsername())
                                .addValue("password", customer.getPassword())
                                .addValue("partyId", Constants.SKIP_INDEX + customer.getId())
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_PARTY_LOGINS, batchValues.toArray(new Map[customers.size()]));

    }

    private void insertParties(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("id", Constants.SKIP_INDEX + customer.getId())
                                .addValue("status", "ACTIVE")
                                .addValue("description", customer.getFirstName() + customer.getLastName())
                                .addValue("cifKey", Constants.SKIP_INDEX + customer.getId())
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_PARTIES, batchValues.toArray(new Map[customers.size()]));

    }


    void insertPersons(List<? extends Customer> customers) {

        List<Map<String, Object>> batchValues = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            batchValues.add(new MapSqlParameterSource("firstName", customer.getFirstName())
                                .addValue("lastName", customer.getLastName())
                                .addValue("partyId", Constants.SKIP_INDEX + customer.getId())
                                .addValue("taxId", customer.getSsn())
                                .addValue("company", "PNC Bank")
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_PERSONS, batchValues.toArray(new Map[customers.size()]));

    }


}
