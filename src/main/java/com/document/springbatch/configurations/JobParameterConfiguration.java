package com.document.springbatch.configurations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
public class JobParameterConfiguration {
    @Bean
    public Job jobParameter(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("jobParameter", jobRepository)
                .start(jobParameterStep1(jobRepository, transactionManager))
                .next(jobParameterStep2(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step jobParameterStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jobParameterStep1", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
                    jobParameters.getString("name");
                    jobParameters.getLong("seq");
                    jobParameters.getDate("date");
                    jobParameters.getDouble("age");

                    System.out.println("jobParameterStep1 was executed.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }

    @Bean
    public Step jobParameterStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("jobParameterStep2", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
                    jobParameters.get("name");
                    jobParameters.get("seq");
                    jobParameters.get("date");
                    jobParameters.get("age");

                    System.out.println("jobParameterStep2 was executed.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }
}
