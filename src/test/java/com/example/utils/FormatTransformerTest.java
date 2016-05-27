package com.example.utils;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.example.model.Exchange;

public class FormatTransformerTest {
	
	private static final Logger logger = LogManager.getLogger(FormatTransformerTest.class);
	
	private FormatTransformer trans = null;
	
	String dateSlash = "20/10/2012";	
	String dateHyphen = "2012-10-20";
	
	int[] dateSlashArray = {2012, Calendar.OCTOBER, 20};
	
	String dateSlashCorrupted = "12312/1232/1232";
	String dateHyphenCorrupted = "11111-1111-1111";
	
	java.sql.Date sqlDate = null;
	
	@Before
	public void setUp() {
		trans = new FormatTransformer();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
		try {
			date = sdf.parse(dateSlash);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: dd/MM/yyyy]");
			e.printStackTrace();
		}
        sqlDate = new Date(date.getTime()); 
	}
	
	@Test
	public void getDateFromCorrectString() {
		String accuired = trans.getDate(dateSlash).toString();
		String expected = "2012-10-20";
		assertEquals(expected, accuired);
	}
	
	@Test
	public void getBigDecimal() {
		BigDecimal expected = new BigDecimal(12333.123);
		expected = expected.setScale(2, RoundingMode.HALF_EVEN);
		String valueAsString = "12  3  33 .123  "; // whitespaces
		BigDecimal accuired = trans.getBigDecimal(valueAsString);
		assertEquals(expected, accuired);
	}
	
	@Test
	public void transformExchange() {
		Object[] containerRaw = {dateSlash, "123.32"};
		Exchange argument = new Exchange(containerRaw);
		
		BigDecimal expVal = new BigDecimal(123.32);
		expVal = expVal.setScale(2, RoundingMode.HALF_EVEN);
		Object[] containerFinal = {sqlDate, expVal};
		Exchange expected = new Exchange(containerFinal);
		
		trans.transform(argument);
		
		assertEquals(expected, argument);
	}
	

}
