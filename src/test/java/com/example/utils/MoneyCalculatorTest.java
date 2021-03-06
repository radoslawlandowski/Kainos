package com.example.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.CalculationResults;
import com.example.model.Exchange;

public class MoneyCalculatorTest {
	
	private static final Logger logger = LogManager.getLogger(MoneyCalculatorTest.class);
	
	@Autowired
	FormatTransformer transformer;

	MoneyCalculator calc = null;
	Exchange ex = null;
	List<Exchange> list = null;
	
	java.sql.Date sqlDate = null;
	/*
	@Before
	public void setUp() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
		try {
			date = sdf.parse("12/02/2012");
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: dd/MM/yyyy]");
			e.printStackTrace();
		}
        sqlDate = new Date(date.getTime()); 
  
		calc = new MoneyCalculator();
		
		Object[] container = {"12/02/2012", "104"};
		ex = new Exchange("12/02/2012", "104");
		transformer.transform(ex);
		
		list = new ArrayList<>();
		list.add(ex);
	}
	
	@Ignore
	@Test
	public void test() {
		List<CalculationResults> accuired = calc.compareIncomeFinal(list, new BigDecimal(100), new BigDecimal(102));
		
		Object[] containerExc = {sqlDate, new BigDecimal(104), new BigDecimal(102)};
		Exchange expected = new Exchange(containerExc);
		List<Exchange> expList= new ArrayList<>();
		expList.add(expected);
		
		assertEquals(expList, accuired);
	} */

}
