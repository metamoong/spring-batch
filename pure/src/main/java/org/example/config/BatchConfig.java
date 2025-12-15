package org.example.config;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * DefaultBatchConfiguration
 * -> JobRepository, JobLauncher 등 Spring Batch의 핵심 컴포넌트들을 자동으로 구성해줌
 */
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    // Spring Batch는 Job과 Step의 실행 정보(메타데이터)를 데이터베이스에 저장하므로
    // 메타 데이터를 위한 DataSource 설정이 필요함
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    // 메타데이터 저장과 배치 작업 실행 등 Spring Batch의 모든 작업은 트랜잭션 내에서 처리됨
    // 공통으로 사용될 transactionmanager bean에 등록
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
