package com.system.batch.lecture6;

import lombok.Data;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  POJO 를 활용해서 JobParameters 관리하기 ->  다양한 방식으로 Job 파라미터를 주입받을 수 있음
 *
 *  tasklet에서 아래와 같이 사용 가능
 *  public Tasklet terminatorTasklet(SystemInfiltrationParameters infiltrationParams)
 */
@Data
@StepScope
@Component
public class SystemInfiltrationParameters {
    @Value("#{jobParameters[missionName]}")
    private String missionName;
    private int securityLevel;
    private final String operationCommander;

    public SystemInfiltrationParameters(@Value("#{jobParameters[operationCommander]}") String operationCommander) {
        this.operationCommander = operationCommander;
    }

    @Value("#{jobParameters[securityLevel]}")
    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
}
