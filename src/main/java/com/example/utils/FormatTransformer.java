package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.Exchange;

public class FormatTransformer {
	
	private static final Logger logger = LogManager.getLogger(FormatTransformer.class);
	
	public Date getDate(String dateAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = null;
		try {
			date = sdf.parse(dateAsString);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: dd/MM/yyyy]", e);
		}
        java.sql.Date sqlDate = new Date(date.getTime()); 

        return sqlDate;
	}
	
	public Date getDateFromHtml(String dateAsString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
		try {
			date = sdf.parse(dateAsString);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: yyyy-MM-dd]", e);
		}
        java.sql.Date sqlDate = new Date(date.getTime()); 

        return sqlDate;
	}
	
	public BigDecimal getBigDecimal(String valueAsString) {
		BigDecimal value = new BigDecimal(valueAsString.replaceAll("\\s+","")).setScale(2, RoundingMode.HALF_EVEN);
		return value;
	}
					 
}
