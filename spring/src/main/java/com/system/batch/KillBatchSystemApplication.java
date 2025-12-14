package com.system.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KillBatchSystemApplication {
	public static void main(String[] args) {
		// 배치 어플리케이션에서는 SpringApplication.run()의 결과를 System.exit()로 처리하는 것이 권장됨
		// 배치 작업의 성공/실패 상태를 exit code로 외부 시스템에 전달할 수 있어 실무에서 배치 모니터링과 제어에 필수적
		System.exit(SpringApplication.exit(SpringApplication.run(KillBatchSystemApplication.class, args)));
	}
}
