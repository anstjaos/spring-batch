package com.document.springbatch.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {
    private final JobBuilder jobBuilder;
    private final StepBuilder stepBuilder;

    @Bean
    public Job helloJob() {
        return jobBuilder
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilder
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(" =======================");
                    System.out.println(" >> Hello Spring Batch!!");
                    System.out.println(" =======================");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilder
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(" =======================");
                    System.out.println(" >> step2 was executed.");
                    System.out.println(" =======================");

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
