package com.obp.pnc.datastore.loc.config;

import static com.obp.pnc.datastore.customer.writer.Constants.CHUNK;

import com.obp.pnc.datastore.loc.model.LocAccount;
import com.obp.pnc.datastore.loc.processor.LocAccountProcessor;
import com.obp.pnc.datastore.loc.writer.LocAccountWriter;
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

@Profile("loc")
@Configuration
public class LocBatchConfig {


    private static final Logger LOGGER = LoggerFactory.getLogger(LocBatchConfig.class);

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job locJob() {
        return jobBuilderFactory
            .get("locJob")
            .incrementer(new RunIdIncrementer())
            /** populate loc database */
            .start(locAccountStep())
            .build();
    }

    @Bean
    public Step locAccountStep() {
        return stepBuilderFactory
            .get("locAccountStep")
            .chunk(CHUNK)
            .reader(locAccountReader())
            .processor(processor())
            .writer(locAccountWriter())
            .exceptionHandler((context, throwable) -> LOGGER.error("  {} ", throwable.getMessage()))
            .build();
    }


    @Bean
    public FlatFileItemReader<LocAccount> locAccountReader() {
        return new FlatFileItemReaderBuilder<LocAccount>()
            .name("locAccountReader")
            .resource(new ClassPathResource("source/loc/loc_accounts.csv"))
            .delimited()
            .names(new String[]{"id", "accountType", "balanceAsof", "availableCredit", "nextPaymentAmount",
                "nextPaymentDate", "principalBalance", "currentBalance", "minimumPaymentAmount", "lastPaymentAmount",
                "lastPaymentDate", "pointsAccrued", "pointsRedeemed", "currentRewardsBalance", "purchaseApr",
                "advancesApr", "cashAdvanceLimit", "availableCash", "financeCharges", "creditLine", "displayName",
                "nickName", "accountStatus", "description", "accountOpeningDate", "accountClosingDate", "accountNumber",
                "lineOfBusiness", "isOnlineAccessEnabled", "interestRateType", "interestRate", "interestRateAsOf",
                "priorInterestRate", "balanceType", "currencyCurrencyCode", "parentAccountId", "lastActivityDate",
                "routingTransitNumber", "transferIn", "transferOut", "micrNumber", "transactionsIncluded",
                "fiAttributes0Name", "fiAttributesValue", "fiAttributes1Name", "fiAttributes1Value",
                "fiAttributes2Name", "fiAttributes2Value", "partyId"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<LocAccount>() {{
                setTargetType(LocAccount.class);
            }})
            .linesToSkip(2)
            .build();
    }


    @Bean
    public ItemProcessor processor() {
        return new LocAccountProcessor();
    }


    @Bean
    ItemWriter<? super LocAccount> locAccountWriter() {
        return new LocAccountWriter();

    }

}
