package com.document.springbatch.configurations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobInstanceConfiguration {

    @Bean
    public Job simpleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleStep1(jobRepository, transactionManager))
                .next(simpleStep2(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step simpleStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("simpleStep1", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("simpleStep1 was executed");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }

    @Bean
    public Step simpleStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("simpleStep2", jobRepository)
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("simpleStep2 was executed");
                    return RepeatStatus.FINISHED;
                }), transactionManager)
                .build();
    }
}
