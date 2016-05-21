package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.DatabaseService;
import com.example.DatabaseServiceImpl;
import com.example.SQLRepository;

@Configuration
public class SpringConfig {
	
	@Bean
	public SQLRepository sqlRepository() {
		
	}
	
	@Bean
	public DatabaseService databaseService() {
		return new DatabaseServiceImpl();
	}
 
}
