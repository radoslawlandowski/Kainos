package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.example.database.DatabaseServiceImpl;
import com.example.database.HSQLDBConnector;
import com.example.utils.CSVExchangeExtruder;

@Configuration
@ComponentScan({"com.example.utils", "com.example"})
public class SpringConfiguration {
 
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
