package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.utils.MoneyCalculator;

@Controller
public class KainosController {
	
	//private static final Logger logger = LogManager.getLogger(KainosController.class);
	
	@Autowired
	DatabaseService service;

    @RequestMapping("/")
    public String index() {
     	service.initializeDatabase();
    	
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
		
    	service.insertDataFromFile(path);
        
    	MoneyCalculator calc = new MoneyCalculator();
   
    	List<ArrayList<String>> compared = calc.compareIncome(service.selectWhereDataMatches("1998-01-05", "1998-03-23"), new BigDecimal(10000), new BigDecimal(101));
    	
    	return compared.toString();
    }
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("msg", name);
        return "index";
    }

}