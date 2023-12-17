package com.spm.financeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@ServletComponentScan
public class FinanceAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FinanceAppApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(FinanceAppApplication.class);
	}

}
