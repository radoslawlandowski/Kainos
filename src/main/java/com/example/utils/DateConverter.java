package com.example.utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateConverter {
	public static String changeDateFormat(String str) {
		TemporalAccessor temporal = DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(str); 
		String output = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(temporal);
		return output;
	}
}
