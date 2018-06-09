package com.obp.pnc.datastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DataStore project using spring dataStore
 * <p>xx
 * If you want to run specific Job(s) from the terminal pass below argument to program
 * <p>
 * --spring.datastore.job.names=customerJob,locJob
 *
 * @author ThirupathiReddy Vajjala
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run();
    }


}
