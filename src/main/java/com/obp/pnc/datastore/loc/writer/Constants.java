package com.obp.pnc.datastore.loc.writer;

import java.time.format.DateTimeFormatter;

public final class Constants {


    public static final DateTimeFormatter FORMATTER_IN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static final DateTimeFormatter FORMATTER_OUT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final int OFFSET = 35;

    public static final String INSERT_LOC_ACCOUNTS = "INSERT INTO loc_accounts(id, account_type, balance_as_of, "
        + "available_credit, next_payment_amount, next_payment_date, principal_balance, current_balance, "
        + "minimum_payment_amount, last_payment_amount, last_payment_date, points_accrued, points_redeemed, "
        + "current_rewards_balance, purchases_apr, advances_apr, cash_advance_limit, available_cash, "
        + "finance_charges, credit_line, loc_account_id, display_name, nick_name, account_status, description, "
        + "account_closing_date, account_opening_date, account_number, line_of_business, "
        + "is_online_access_enabled, category, interest_rate_type, interest_rate, interest_rate_as_of, "
        + "prior_interest_rate, balance_type, business_unit_id, currency_id, parent_account_id, "
        + "last_activity_date, transfer_in, transfer_out,micr_number, transactions_included) VALUES(:id,"
        + ":account_type,:balance_as_of,:available_credit,:next_payment_amount,:next_payment_date,"
        + ":principal_balance,:current_balance,:minimum_payment_amount,:last_payment_amount, "
        + ":last_payment_date,:points_accrued,:points_redeemed,:current_rewards_balance,:purchases_apr, "
        + ":advances_apr,:cash_advance_limit,:available_cash,:finance_charges,:credit_line,:loc_account_id, "
        + ":display_name,:nick_name,:account_status,:description,:account_closing_date, "
        + ":account_opening_date,:account_number,:line_of_business,:is_online_access_enabled,:category, "
        + ":interest_rate_type,:interest_rate,:interest_rate_as_of,:prior_interest_rate,:balance_type, "
        + ":business_unit_id,:currency_id,:parent_account_id,:last_activity_date,:transfer_in,:transfer_out,"
        + ":micr_number,:transactions_included)";


    public static final String INSERT_LOC_ACCOUNTS_OWNERS =
        "INSERT INTO loc_account_owners(loc_account_id, party_id) " + "VALUES(:loc_account_id,:party_id)";
}
