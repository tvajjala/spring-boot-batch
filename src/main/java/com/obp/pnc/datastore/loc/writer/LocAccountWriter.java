package com.obp.pnc.datastore.loc.writer;

import static com.obp.pnc.datastore.common.SerializationUtil.partyIds;

import com.obp.pnc.datastore.loc.model.LocAccount;
import java.time.LocalDateTime;
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


public class LocAccountWriter implements ItemWriter<LocAccount>, InitializingBean {


    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    DataSource locDataSource;

    @Override
    public void afterPropertiesSet() {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(locDataSource);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LocAccountWriter.class);


    @Override
    public void write(List<? extends LocAccount> items) {

        insertLocAccounts(items);

        insertLocAccountOwners(items);


    }


    private void insertLocAccountOwners(List<? extends LocAccount> items) {

        List<Map<String, Object>> batchValues = new ArrayList<>(items.size());

        for (LocAccount locAccount : items) {

            long partyId = com.obp.pnc.datastore.customer.writer.Constants.SKIP_INDEX + locAccount.getPartyId();

            List<String> customerPartyIds = partyIds();

            LOGGER.error("{} ", customerPartyIds);
            if (!customerPartyIds.contains(String.valueOf(partyId))) {
                LOGGER.error(" PartyId {}  not exist in the customer DB  ", partyId);
            }

            batchValues.add(new MapSqlParameterSource("loc_account_id", Constants.OFFSET + locAccount.getId())
                                .addValue("party_id", partyId)
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_LOC_ACCOUNTS_OWNERS, batchValues.toArray(new Map[items.size()]));


    }

    private void insertLocAccounts(List<? extends LocAccount> items) {

        List<Map<String, Object>> batchValues = new ArrayList<>(items.size());

        for (LocAccount locAccount : items) {
            batchValues.add(new MapSqlParameterSource("id", Constants.OFFSET + locAccount.getId())
                                .addValue("account_type", locAccount.getAccountType())
                                .addValue("balance_as_of", toDate(locAccount.getBalanceAsof()))
                                .addValue("available_credit", locAccount.getAvailableCredit())
                                .addValue("next_payment_amount", locAccount.getNextPaymentAmount())
                                .addValue("next_payment_date", toDate(locAccount.getNextPaymentDate()))
                                .addValue("principal_balance", locAccount.getPrincipalBalance())
                                .addValue("current_balance", locAccount.getCurrentBalance())
                                .addValue("minimum_payment_amount", locAccount.getMinimumPaymentAmount())
                                .addValue("last_payment_amount", locAccount.getLastPaymentAmount())
                                .addValue("last_payment_date", toDate(locAccount.getLastPaymentDate()))
                                .addValue("points_accrued", locAccount.getPointsAccrued())
                                .addValue("points_redeemed", locAccount.getPointsRedeemed())
                                .addValue("current_rewards_balance", locAccount.getCurrentRewardsBalance())
                                .addValue("purchases_apr", locAccount.getPurchaseApr())
                                .addValue("advances_apr", locAccount.getAdvancesApr())
                                .addValue("cash_advance_limit", locAccount.getCashAdvanceLimit())
                                .addValue("available_cash", locAccount.getAvailableCash())

                                .addValue("finance_charges", locAccount.getFinanceCharges())
                                .addValue("credit_line", locAccount.getCreditLine())
                                .addValue("loc_account_id", (Constants.OFFSET + locAccount.getId()))
                                .addValue("display_name", locAccount.getDisplayName())
                                .addValue("nick_name", locAccount.getNickName())
                                .addValue("account_status", accountStatus(locAccount.getAccountStatus()))
                                .addValue("description", locAccount.getDescription())
                                .addValue("account_opening_date", toDate(locAccount.getAccountOpeningDate()))
                                .addValue("account_closing_date", toDate(locAccount.getAccountClosingDate()))
                                .addValue("account_number", locAccount.getAccountNumber())

                                .addValue("line_of_business", lineOfBusiness(locAccount.getLineOfBusiness()))

                                .addValue("is_online_access_enabled", flag(locAccount.getIsOnlineAccessEnabled()))
                                .addValue("interest_rate_type", locAccount.getInterestRateType())
                                .addValue("interest_rate", locAccount.getInterestRate())
                                .addValue("interest_rate_as_of", toDate(locAccount.getInterestRateAsOf()))
                                .addValue("prior_interest_rate", locAccount.getPriorInterestRate())
                                .addValue("balance_type", locAccount.getBalanceType())

                                .addValue("last_activity_date", toDate(locAccount.getLastActivityDate()))

                                .addValue("transfer_in", flag(locAccount.getTransferIn()))
                                .addValue("transfer_out", flag(locAccount.getTransferOut()))
                                .addValue("micr_number", locAccount.getMicrNumber())
                                .addValue("transactions_included", flag(locAccount.getTransactionsIncluded()))

                                //FIXME:NO_DB_COLUMN for routing_transit_number
                                .addValue("routing_transit_number", locAccount.getRoutingTransitNumber())

                                // not mentioned in csv
                                .addValue("parent_account_id", null)
                                .addValue("currency_id", "1")
                                .addValue("business_unit_id", "1")
                                .addValue("category", "LOC")
                                .getValues());
        }

        jdbcTemplate.batchUpdate(Constants.INSERT_LOC_ACCOUNTS, batchValues.toArray(new Map[items.size()]));
    }

    private Object flag(String flag) {
        return Boolean.valueOf(flag);
    }

    private Object lineOfBusiness(String enumValue) {
        return enumValue;
    }

    private Object accountStatus(String status) {
        return status;
    }


    private Object toDate(String dateString) {
        try {
            if (dateString == null || dateString.equalsIgnoreCase("")) { return null; }

            //2017-12-01T14:23:00.000Z
            LocalDateTime dateTime = LocalDateTime.parse(dateString, Constants.FORMATTER_IN);

            //2017-05-25 00:00:00
            return Constants.FORMATTER_OUT.format(dateTime);

        } catch (Exception e) {
            LOGGER.error("Error while parsing date {} ", dateString);
            return null;
        }
    }


}
