package com.example.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.database.DatabaseService;

@Controller 
public class KainosController {
	
	private static final Logger logger = LogManager.getLogger(KainosController.class);
	
 	private static final String fileName = "data.csv";
 	
	@Autowired
	DatabaseService service;
	
	private void checkDatabase() {
	  	if (service.isInitialized() == false) {
    		service.initializeDatabase();
        	service.insertDataFromFile(fileName);
        	logger.info("Database initialized");
    	}
	}

    @RequestMapping("/")
    public String index() {
    	checkDatabase();
        return "mainIndex";
    }
    
    @RequestMapping("/compare")
    public String greeting() {
    	checkDatabase();
    	return "comp";
    }

}