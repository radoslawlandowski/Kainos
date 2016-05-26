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
	private static final String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
	
	@Autowired
	DatabaseService service;

    @RequestMapping("/")
    public String index() {
    	if(service.isInitialized() == false) {
    		service.initializeDatabase();
        	service.insertDataFromFile(path);
        	logger.info("KainosController, Database initialized");
    	}
        return "mainIndex";
    }
    
    @RequestMapping("/compare")
    public String greeting() {
    	if(service.isInitialized() == false) {
    		service.initializeDatabase();
        	service.insertDataFromFile(path);
        	logger.info("KainosController, Database initialized");
    	}
    	return "compare";
    }

}