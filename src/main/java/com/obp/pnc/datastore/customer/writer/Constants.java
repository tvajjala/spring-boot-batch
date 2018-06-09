package com.obp.pnc.datastore.customer.writer;

public final class Constants {


    public static final int CHUNK = 10;// insert 10 records at a time.
    public static final int SKIP_INDEX = 15;// skip primary key

    public static final String INSERT_PERSONS =
        "INSERT INTO persons(first_name,last_name,party_id,tax_id,company) VALUES(:firstName,:lastName,"
            + ":partyId,:taxId,:company)";


    public static final String INSERT_PARTIES =
        "INSERT INTO parties(id,status,description,cif_key) VALUES(:id," + ":status," + ":description,:cifKey)";


    public static final String INSERT_PARTY_LOGINS =
        "INSERT INTO party_logins(status,user_name,password,party_id) VALUES(:status," + ":username,"
            + ":password,:partyId)";

    public static final String INSERT_ADDRESS =
        "INSERT INTO addresses(party_id,type,address_line1,city,state,zip_code,iso3166_country_code) VALUES"
            + "(:partyId," + ":type," + ":addressLine1,:city,:state,:zip,:countryCode)";

    public static final String INSERT_TELEPHONE =
        "INSERT INTO telephone_numbers(party_id,type,status, phone_number, country_calling_code) VALUES" + "(:partyId,"
            + ":type," + ":status,:phoneNumber,:countryCode)";

    public static final String INSERT_USER_SECURITY_QUESTIONS =
        "INSERT INTO user_security_questions(party_login_id,security_question_id," + "answer) VALUES" + "(:partyId,"
            + ":qid," + ":answer)";
}
