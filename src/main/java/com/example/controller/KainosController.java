package com.example.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class KainosController {
	
	private static final Logger logger = LogManager.getLogger(KainosController.class);

    @RequestMapping("/")
    public String index() {
    	logger.info("Entered \"/\" page");
        return "mainIndex";
    }
    
    @RequestMapping("/compare")
    public String greeting() {
    	logger.info("Entered \"/compare\" page");
    	return "compTest";
    }
}