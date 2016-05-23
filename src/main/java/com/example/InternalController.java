package com.example;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Exchange;
import com.example.utils.ExchangeTransformer;

@Controller
public class InternalController {
	
	private static final Logger logger = LogManager.getLogger(InternalController.class);
	
	@Autowired
	DatabaseService service;
    
    @RequestMapping(value = "/dataExchange", method = RequestMethod.GET)
    public @ResponseBody List<Exchange> returnText(@RequestParam(value = "startdate", required = false) String startDate,
			@RequestParam(value = "enddate", required = false) String endDate) {
    	Date start = ExchangeTransformer.getDate("21/02/2003");
    	Date end = ExchangeTransformer.getDate("04/03/2003");
    	List<Exchange> ex = service.selectWhereDate(start, end);
     	return ex;
    }
    
}