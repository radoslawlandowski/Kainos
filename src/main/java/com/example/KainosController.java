package com.example;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.utils.ExchangeTransformer;

@Controller // should be Controller
public class KainosController {
	
	 private static final Logger logger = LogManager.getLogger(KainosController.class);
	
	@Autowired
	DatabaseService service;

    @RequestMapping("/")
    public String index() {
     	service.initializeDatabase();
    	
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
		
    	service.insertDataFromFileRevisited(path);
        
    	//MoneyCalculator calc = new MoneyCalculator();
   
    	//List<ArrayList<String>> compared = calc.compareIncome(service.selectWhereDataMatches("1998-01-05", "1998-03-23"), new BigDecimal(10000), new BigDecimal(101));
    	Date start = ExchangeTransformer.getDate("21/02/2003");
    	Date end = ExchangeTransformer.getDate("21/03/2003");
    	
    	String sth = service.selectWhereDateMatchesRevisited(start, end).toString();
    	String sth2 = service.selectAllRevisited().toString();
    	return sth2;
    }
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	service.initializeDatabase();
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
    	service.insertDataFromFile(path);
    	
    	  String strDate = "31/12/1993";
          
     
          SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
          java.util.Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
          java.sql.Date sqlDate = new Date(date.getTime());
             	
        return sqlDate.toString();
    }

    

}