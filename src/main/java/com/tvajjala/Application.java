package com.tvajjala;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
@SpringBootApplication
public class Application implements CommandLineRunner {

    static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        System.setProperty("spring.batch.job.names", "NONE");//default disable no jobs
        //replace NONE with specific job u want to run
        SpringApplication application = new SpringApplication(Application.class);


        // LOG.info("Running with args {}", Arrays.toString(args));

        ConfigurableApplicationContext ctx = application.run(args);//java -jar spring-boot-batch-1.0.jar --spring.batch.job.names=customerJob
        // LOG.info("Ctx {} ", ctx.getEnvironment().getSystemProperties());
    }


    // @Autowired
    // JobLauncher jobLauncher;

    @Override
    public void run(String... args) {

        // jobLauncher.run(job, new JobParameters());

        //Recommended way ->  java -jar spring-boot-batch-1.0-SNAPSHOT.jar --spring.batch.job.names=customerJob
    }
}
