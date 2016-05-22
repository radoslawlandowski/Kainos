package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.utils.MoneyCalculator;

@RestController
public class KainosController {
	
	private static final Logger logger = LogManager.getLogger(KainosController.class);
	
	@Autowired
	DatabaseService service;

    @RequestMapping("/")
    public String index() {
    	logger.info(" The '/' site reached");
    	service.initializeDatabase();
    	
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
		
    	service.insertDataFromFile(path);
        
    	MoneyCalculator calc = new MoneyCalculator();
    	List<ArrayList<String>> result = calc.calculateIncome(service.selectWhereDataMatches("1998-01-05", "1998-01-23"), new BigDecimal(10000));
    	List<ArrayList<String>> sResult = calc.calculateIncomeConstantPercentage(service.selectWhereDataMatches("1998-01-05", "1998-01-23"), new BigDecimal(10000), new BigDecimal(110));
    	
    	return sResult.toString();
    }

}