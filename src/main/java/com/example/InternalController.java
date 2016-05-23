package com.example;

import java.math.BigDecimal;
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
import com.example.utils.MoneyCalculator;

@Controller
public class InternalController {

	private static final Logger logger = LogManager.getLogger(InternalController.class);
	
	private static final String START_DATE = "1998-01-05";
	private static final String END_DATE = "2016-05-12";

	@Autowired
	DatabaseService service;

	@RequestMapping(value = "/dataExchange", method = RequestMethod.GET)
	public @ResponseBody List<Exchange> returnText(
			@RequestParam(value = "startdate", required = true, defaultValue = START_DATE) String startDate,
			@RequestParam(value = "enddate", required = true, defaultValue = END_DATE) String endDate) {
		logger.info("dataExchange site entered!");

		Date start = ExchangeTransformer.getDateFromHtml(startDate);
		Date end = ExchangeTransformer.getDateFromHtml(endDate);
		List<Exchange> ex = service.selectWhereDate(start, end);
		return ex;
	}
	
	@RequestMapping(value = "/dataCompare", method = RequestMethod.GET)
	public @ResponseBody List<Exchange> compareExchanges(
			@RequestParam(value = "startdate", required = true, defaultValue = START_DATE) String startDate,
			@RequestParam(value = "enddate", required = true, defaultValue = END_DATE) String endDate,
			@RequestParam(value = "initialInput", required = false, defaultValue = "1") String input, 
			@RequestParam(value = "percentage", required = false, defaultValue = "100") String percentage) {
		
		logger.info("dataCompare site entered!");
		
		Date start = ExchangeTransformer.getDateFromHtml(startDate);
		Date end = ExchangeTransformer.getDateFromHtml(endDate);
		List<Exchange> ex = service.selectWhereDate(start, end);
		
		BigDecimal in = new BigDecimal(input);
		
		BigDecimal factor = new BigDecimal(100);
		BigDecimal perc = new BigDecimal(percentage).multiply(factor);
		
		MoneyCalculator calc = new MoneyCalculator();
		List<Exchange> compared = calc.compareIncomeRevisited(ex, in, perc);
		
		return compared;
	}
	

}