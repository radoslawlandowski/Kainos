package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.Exchange;

public class ExchangeTransformer {
	
	 private static final Logger logger = LogManager.getLogger(ExchangeTransformer.class);
	
	public static Date getDate(String dateAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
		try {
			date = sdf.parse(dateAsString);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: dd/mm/yyyy]");
			e.printStackTrace();
		}
        java.sql.Date sqlDate = new Date(date.getTime()); 

        return sqlDate;
	}
	
	public static Date getDateFromHtml(String dateAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
		try {
			date = sdf.parse(dateAsString);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: dd/mm/yyyy]");
			e.printStackTrace();
		}
        java.sql.Date sqlDate = new Date(date.getTime()); 

        return sqlDate;
	}
	
	public static BigDecimal getBigDecimal(String valueAsString) {
		BigDecimal value = new BigDecimal(valueAsString.replaceAll("\\s+","")).setScale(2, RoundingMode.HALF_EVEN);
		return value;
	}
	
	public static void transform(Exchange ex) {
		byte datePos = 0;
		byte valuePos = 1;
		String dateAsString = (String)ex.getRow()[datePos];
 		String valueAsString = (String)ex.getRow()[valuePos];

 		Date date = ExchangeTransformer.getDate(dateAsString);
 		BigDecimal value = ExchangeTransformer.getBigDecimal(valueAsString);
 		
 		Object[] container = {date, value};
 		ex.setRow(container);
	}
					 
}
