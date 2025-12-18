package com.system.batch.lecture6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class SystemTerminatorConfig {
    @Bean
    public Job processTerminatorJob(JobRepository jobRepository, Step terminationStep) {
        return new JobBuilder("processTerminatorJob", jobRepository)
                .start(terminationStep)
                .build();
    }

    @Bean
    public Step terminationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet terminatorTasklet) {
        return new StepBuilder("terminationStep", jobRepository)
                .tasklet(terminatorTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope // @Value를 사용해 잡 파라미터를 전달받으려면 이 애노테이션을 반드시 함께 사용해야함
    public Tasklet terminatorTasklet( // @Value 어노테이션과 #{jobParameters['parameterName']} 표현식으로 JobParameter를 주입받을 수 있음
            @Value("#{jobParameters['terminatorId']}") String terminatorId,
            @Value("#{jobParameters['targetCount']}") Integer targetCount
    ) {
        return (contribution, chunkContext) -> {
            log.info("시스템 종결자 정보:");
            log.info("ID: {}", terminatorId);
            log.info("제거 대상 수: {}", targetCount);
            log.info("⚡ SYSTEM TERMINATOR {} 작전을 개시합니다.", terminatorId);
            log.info("☠️ {}개의 프로세스를 종료합니다.", targetCount);

            for (int i = 1; i <= targetCount; i++) {
                log.info("💀 프로세스 {} 종료 완료!", i);
            }

            log.info("🎯 임무 완료: 모든 대상 프로세스가 종료되었습니다.");
            return RepeatStatus.FINISHED;
        };
    }
}