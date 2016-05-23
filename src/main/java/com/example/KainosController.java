package com.example;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Exchange;
import com.example.utils.ExchangeTransformer;
import com.example.utils.MoneyCalculator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller // should be Controller
public class KainosController {
	
	// private static final Logger logger = LogManager.getLogger(KainosController.class);
	
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

    

}