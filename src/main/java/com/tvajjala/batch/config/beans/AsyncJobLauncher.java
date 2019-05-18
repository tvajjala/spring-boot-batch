package com.tvajjala.batch.config.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public class AsyncJobLauncher extends SimpleJobLauncher {


    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        LOG.info("Executing job {} ", job.getName());

        JobExecution jobExecution = super.run(job, jobParameters);
        LOG.info(" jobExecution.getJobId() {} ", jobExecution.getJobId());
        return jobExecution;
    }

}
