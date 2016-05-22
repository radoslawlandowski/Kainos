package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MoneyCalculator {

	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	public MoneyCalculator() {
	 
	}
	
	private BigDecimal calculateSingleIncome(BigDecimal inputValue, BigDecimal percentage) {
		BigDecimal val = inputValue.multiply(percentage).divide(ONE_HUNDRED);
		return val;
	}
	
	public List<ArrayList<String>> calculateIncome(List<ArrayList<String>> data, BigDecimal inputValue) {
		
		List<ArrayList<String>> outputList = new ArrayList<ArrayList<String>>();
		
		int listSize = data.size();
		for(int i = 0 ; i < listSize ; i++) {
			String v = data.get(i).get(1);
			v = v.replaceAll("\\s+",""); // remove whitespaces
			BigDecimal val = new BigDecimal(v);
			BigDecimal percentage = val;
			BigDecimal outputValue = calculateSingleIncome(inputValue, percentage);
			outputValue = outputValue.setScale(2, RoundingMode.HALF_EVEN);
			inputValue = outputValue;
			
			ArrayList<String> row = new ArrayList<>();
			row.add(data.get(i).get(0));
			row.add(outputValue.toString());
			outputList.add(row);
		}
		return outputList;
	}
	
	public List<ArrayList<String>> calculateIncomeConstantPercentage(List<ArrayList<String>> data, BigDecimal inputValue, BigDecimal percentage) {
		
		List<ArrayList<String>> outputList = new ArrayList<ArrayList<String>>();
		
		int listSize = data.size();
		for(int i = 0 ; i < listSize ; i++) {
			BigDecimal outputValue = calculateSingleIncome(inputValue, percentage);
			outputValue = outputValue.setScale(2, RoundingMode.HALF_EVEN);
			inputValue = outputValue;
			ArrayList<String> row = new ArrayList<>();
			row.add(data.get(i).get(0));
			row.add(outputValue.toString());
			outputList.add(row);
		}
		return outputList;

	}
	
}
