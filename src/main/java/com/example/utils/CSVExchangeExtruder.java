package com.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.Exchange;

public class CSVExchangeExtruder implements Iterator<Exchange> {
	private static final Logger logger = LogManager.getLogger(CSVExchangeExtruder.class);
	private static final char commaSeparator = ',';
	
	BufferedReader reader;
	
	public CSVExchangeExtruder() {
	}
	
	public CSVExchangeExtruder(BufferedReader myReader) { 
		reader = myReader; 
	};

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public boolean hasNext() {
		boolean result = false;
		try {
			result = reader.ready();
		} catch (IOException e) {
			logger.error("I/O exception reading a file");
			e.printStackTrace();
		}
		return result;
	}
  
	@Override
	public Exchange next() {
	    String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			logger.error("I/O exception reading a file");
			e.printStackTrace();
		}
		String separatingRegex = " *" + commaSeparator + " *";
		String[] columns = line.split(separatingRegex);
		if(columns[0] == line) { // checks if 'split(separatingRegex) splitted the row properly
			logger.warn("String does not contain the specified separator");
			return null;
		} else {
			if(columns[0].equals("Data")) {
				return null;
			}
			FormatTransformer trans = new FormatTransformer();
			Date date = trans.getDate(columns[0]);
			BigDecimal value = trans.getBigDecimal(columns[1]);
			Exchange ex = new Exchange(date, value);
			return ex;
		}
	}
}
