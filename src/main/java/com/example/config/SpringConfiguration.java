package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.database.DatabaseServiceImpl;
import com.example.database.HSQLDBConnector;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.FormatTransformer;

@Configuration
@ComponentScan({"com.example.utils", "com.example", "com.example.infrastructure", "com.example.database"})
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
	
	@Bean
	public FormatTransformer FormatTransformer() {
		return new FormatTransformer();
	}
 
 
}
