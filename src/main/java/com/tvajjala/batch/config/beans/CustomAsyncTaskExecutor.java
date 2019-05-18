package com.tvajjala.batch.config.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.ThreadFactory;

public class CustomAsyncTaskExecutor extends SimpleAsyncTaskExecutor {

    private Logger LOG = LoggerFactory.getLogger(getClass());


    ThreadFactory threadFactory;

    public CustomAsyncTaskExecutor(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }


    @Override
    public void doExecute(Runnable task) {
        Thread thread = (this.threadFactory != null ? this.threadFactory.newThread(task) : createThread(task));

        LOG.info("########Thread Name {} ", thread.getName());
        MDC.put("job", thread.getName());
        thread.start();
    }
}
