package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.DatabaseServiceImpl;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.HSQLDBConnector;

@Configuration
@ComponentScan({"com.example.utils", "com.example"})
public class SpringConfig {
 
	@Bean
	public CSVExchangeExtruder csvExchangeExtruder() {
		return new CSVExchangeExtruder();
	}
	
	@Bean
	public HSQLDBConnector HSQLDBConnector() {
		return new HSQLDBConnector();
	}
	
	@Bean
	public DatabaseServiceImpl DatabaseServiceImpl() {
		return new DatabaseServiceImpl();
	}
 
}
