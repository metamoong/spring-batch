package com.system.batch.lecture6;

import io.micrometer.common.lang.Nullable;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * jobParameters 유효성 검증
 */
@Component
public class SystemDestructionValidator implements JobParametersValidator {

    @Override
    public void validate(@Nullable JobParameters parameters) throws JobParametersInvalidException {
        if (parameters == null) {
            throw new JobParametersInvalidException("파라미터가 NULL입니다");
        }

        Long destructionPower = parameters.getLong("destructionPower");
        if (destructionPower == null) {
            throw new JobParametersInvalidException("destructionPower 파라미터는 필수값입니다");
        }

        if (destructionPower > 9) {
            throw new JobParametersInvalidException(
                    "파괴력 수준이 허용치를 초과했습니다: " + destructionPower + " (최대 허용치: 9)");
        }
    }


}
