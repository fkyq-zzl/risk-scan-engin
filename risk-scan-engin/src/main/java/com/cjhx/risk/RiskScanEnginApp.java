package com.cjhx.risk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 风控扫描引擎服务启动入口
 *
 * @author lujinfu
 * @date 2017年10月17日
 */
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
// @EnableAsync //支持异步调用
public class RiskScanEnginApp extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RiskScanEnginApp.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RiskScanEnginApp.class, args);
	}
}
