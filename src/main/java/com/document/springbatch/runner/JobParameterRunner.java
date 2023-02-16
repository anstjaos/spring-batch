package com.document.springbatch.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobParameterRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job jobParameter;

    public JobParameterRunner(JobLauncher jobLauncher, Job jobParameter) {
        this.jobLauncher = jobLauncher;
        this.jobParameter = jobParameter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "user1")
                .addLong("seq", 2L)
                .addDate("date", new Date())
                .addDouble("age", 16.5)
                .toJobParameters();

        jobLauncher.run(jobParameter, jobParameters);
    }
}
