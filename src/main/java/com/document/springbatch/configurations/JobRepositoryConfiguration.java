package com.document.springbatch.configurations;

import com.document.springbatch.listener.JobRepositoryListener;
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
public class JobRepositoryConfiguration {
    private final JobRepositoryListener jobRepositoryListener;

    public JobRepositoryConfiguration(JobRepositoryListener jobRepositoryListener) {
        this.jobRepositoryListener = jobRepositoryListener;
    }

    @Bean
    public Job batchJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("batchJob", jobRepository)
                .start(batchJobStep1(jobRepository, transactionManager))
                .next(batchJobStep2(jobRepository, transactionManager))
                .listener(jobRepositoryListener)
                .build();
    }

    @Bean
    public Step batchJobStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("batchJobStep1", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
                    jobParameters.getString("date");

                    System.out.println("batchJobStep1 was executed.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }

    @Bean
    public Step batchJobStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("batchJobStep2", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
                    jobParameters.get("date");

                    System.out.println("batchJobStep2 was executed.");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }
}
