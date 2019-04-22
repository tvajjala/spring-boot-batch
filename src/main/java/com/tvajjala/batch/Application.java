package com.tvajjala.batch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * DataStore project using spring dataStore
 * <p>
 * If you want to run specific Job(s) from the terminal pass below argument to program
 * <p>
 * <p>
 * Run individual jobs on the final artifact as
 * <p>
 * <p>
 * java -jar spring-boot-batch-1.0.jar --spring.batch.job.names=customerJob
 *
 * @author ThirupathiReddy Vajjala
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements CommandLineRunner {


    public static void main(String[] args) {

        System.setProperty("spring.batch.job.names", "NONE");//default disable no jobs
        //replace NONE with specific job u want to run
        SpringApplication application = new SpringApplication(Application.class);

        application.run(args);//java -jar spring-boot-batch-1.0.jar --spring.batch.job.names=customerJob

    }


    // @Autowired
    // JobLauncher jobLauncher;

    @Override
    public void run(String... args) {

        // jobLauncher.run(job, new JobParameters());

        //Recommended way ->  java -jar spring-boot-batch-1.0-SNAPSHOT.jar --spring.batch.job.names=customerJob
    }
}
