package com.obp.pnc.datastore.loc.processor;

import com.obp.pnc.datastore.loc.model.LocAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LocAccountProcessor implements ItemProcessor<LocAccount, LocAccount> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocAccountProcessor.class);


    @Override
    public LocAccount process(LocAccount locAccount) {
        LOGGER.info(" {} ", locAccount);
        return locAccount;
    }


}
