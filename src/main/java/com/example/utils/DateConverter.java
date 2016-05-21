package com.example.utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateConverter {
	public static String replace(String str, char acutal, char wanted) {
		TemporalAccessor temporal = DateTimeFormatter
		    .ofPattern("dd/MM/yyyy")
		    .parse(str); // use parse(date, LocalDateTime::from) to get LocalDateTime
		String output = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(temporal);
		return output;

	}
}
