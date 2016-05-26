package com.example.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.database.DatabaseService;
import com.example.model.Exchange;
import com.example.utils.ExchangeTransformer;
import com.example.utils.MoneyCalculator;

@RestController
public class InternalController {

	private static final Logger logger = LogManager.getLogger(InternalController.class);
	
	private static final String START_DATE = "1998-01-05";
	private static final String END_DATE = "2016-05-12";

	@Autowired
	DatabaseService service;

	@RequestMapping(value = "/dataExchange", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Exchange>> returnText(
			@RequestParam(value = "startdate", required = true, defaultValue = START_DATE) String startDate,
			@RequestParam(value = "enddate", required = true, defaultValue = END_DATE) String endDate) {
		logger.info("dataExchange site entered!");

		Date start = ExchangeTransformer.getDateFromHtml(startDate);
		Date end = ExchangeTransformer.getDateFromHtml(endDate);
		List<Exchange> ex = service.selectWhereDate(start, end);
		return new ResponseEntity<List<Exchange>>(ex, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dataCompare", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Exchange>> compareExchanges(
			@RequestParam(value = "startdate", required = false, defaultValue = START_DATE) String startDate,
			@RequestParam(value = "enddate", required = false, defaultValue = END_DATE) String endDate,
			@RequestParam(value = "initialInput", required = false, defaultValue = "10000") String input, 
			@RequestParam(value = "percentage", required = false, defaultValue = "1") String percentage) {
		
		logger.info("dataCompare site entered!");
		
		Date start = ExchangeTransformer.getDateFromHtml(startDate);
		Date end = ExchangeTransformer.getDateFromHtml(endDate);
		List<Exchange> ex = service.selectWhereDate(start, end);
		
		BigDecimal in = new BigDecimal(input);
		
		BigDecimal factor = new BigDecimal(100);
		BigDecimal perc = new BigDecimal(percentage).add(factor);
		
		MoneyCalculator calc = new MoneyCalculator();
		//List<Exchange> compared = calc.compareIncomeRevisited(ex, in, perc);
		//List<Exchange> compared = calc.compareIncomeRevisitedNormalized(ex, in, perc);
		List<Exchange> compared = calc.compareIncomeFinal(ex, in, perc);
		
		return new ResponseEntity<List<Exchange>>(compared, HttpStatus.OK);
	}
	

}