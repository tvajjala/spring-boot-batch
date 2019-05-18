package com.tvajjala.batch.config.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.repository.dao.JobExecutionDao;
import org.springframework.batch.core.repository.dao.JobInstanceDao;
import org.springframework.batch.core.repository.dao.StepExecutionDao;
import org.springframework.batch.core.repository.support.SimpleJobRepository;

import java.util.UUID;

public class CustomJobRepository extends SimpleJobRepository {


    private Logger LOG = LoggerFactory.getLogger(getClass());

    public CustomJobRepository(JobInstanceDao jobInstanceDao, JobExecutionDao jobExecutionDao, StepExecutionDao stepExecutionDao, ExecutionContextDao ecDao) {

        super(jobInstanceDao, jobExecutionDao, stepExecutionDao, ecDao);
    }

    @Override
    public JobExecution createJobExecution(String jobName, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        LOG.info("Job execution started ... {} thread Name {}", jobName, Thread.currentThread().getName());
        MDC.put("job", UUID.randomUUID().toString());
        return super.createJobExecution(jobName, jobParameters);
    }

    @Override
    public JobExecution createJobExecution(JobInstance jobInstance, JobParameters jobParameters, String jobConfigurationLocation) {
        // Thread.currentThread().setName(jobInstance.getJobName());
        LOG.info("Job execution started ... {}", jobInstance.getJobName());
        MDC.put("job", UUID.randomUUID().toString());
        return super.createJobExecution(jobInstance, jobParameters, jobConfigurationLocation);
    }


}
