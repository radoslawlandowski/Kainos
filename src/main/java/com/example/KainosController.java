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
        
    	MoneyCalculator calc = new MoneyCalculator();
    	Date start = ExchangeTransformer.getDate("21/02/2003");
    	Date end = ExchangeTransformer.getDate("21/03/2003");
    	List<Exchange> compared = calc.compareIncomeRevisited(service.selectWhereDate(start, end), new BigDecimal(10000), new BigDecimal(101));
    	
    	
    	String sth = service.selectWhereDate(start, end).toString();
    	String sth2 = service.selectAll().toString();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	List<Exchange> ex = service.selectAll();

    	String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(ex);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	String result = jsonInString;
    	return "mainIndex";
    }
    
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	service.initializeDatabase();
		String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";    	
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