package com.example;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InternalController {
	
	 private static final Logger logger = LogManager.getLogger(InternalController.class);
	
	@Autowired
	DatabaseService service;

    
    @RequestMapping(value = "/dataExchange", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ArrayList<String>>> returnText(@RequestParam(value = "startdate", required = true) String startDate,
			@RequestParam(value = "enddate", required = true) String endDate) {

    	
    	//List<ArrayList<String>> list = service.selectWhereDataMatches(startDate, endDate);
    	//ResponseEntity<List<ArrayList<String>>> response = new ResponseEntity<>(list, HttpStatus.OK);
    	return null;
    }
}