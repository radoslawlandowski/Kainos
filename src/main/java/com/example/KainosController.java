package com.example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // should be Controller
public class KainosController {
	
	 private static final Logger logger = LogManager.getLogger(KainosController.class);
	
	@Autowired
	DatabaseService service;

    @RequestMapping("/")
    public String index() {
     	service.initializeDatabase();
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
    	service.insertDataFromFile(path);

    	return "mainIndex";
    }
    
    @RequestMapping("/compare")
    public String greeting() {

    	return "compare";
    }

    @RequestMapping("/example")
    public String exampleController() {
    	logger.info("Entered example COntroller");
    	return "Goodbye";
    }
    
    @RequestMapping("/d")
    public String DebugController() {
    	logger.info("Entered debug COntroller");
    	return "debug";
    }
    

}