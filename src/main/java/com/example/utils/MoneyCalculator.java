package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Exchange;

public class MoneyCalculator {

	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	private BigDecimal calculateSingleIncome(BigDecimal inputValue, BigDecimal percentage) {
		BigDecimal val = inputValue.multiply(percentage).divide(ONE_HUNDRED);
		return val;
	}
	
	public List<ArrayList<String>> compareIncome(List<ArrayList<String>> data, BigDecimal inputValue, BigDecimal depPercentage) {
		
		List<ArrayList<String>> outputList = new ArrayList<ArrayList<String>>();
		
		int listSize = data.size();
		
		BigDecimal fundInputValue = inputValue;
		BigDecimal depositInputValue = inputValue;
		
		BigDecimal fundPercentage = null;
		BigDecimal depositPercentage = depPercentage;
		
		BigDecimal fundOutputValue = null;
		BigDecimal depositOutputValue = null;
		
		for(int i = 0 ; i < listSize ; i++) {
			String val = data.get(i).get(1);
			val = val.replaceAll("\\s+",""); // remove whitespaces if no decimal part
			fundPercentage = new BigDecimal(val);
			
			fundOutputValue = calculateSingleIncome(fundInputValue, fundPercentage);
			fundOutputValue = fundOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			fundInputValue = fundOutputValue;
			
			depositOutputValue = calculateSingleIncome(depositInputValue, depositPercentage);
			depositOutputValue = depositOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			depositInputValue = depositOutputValue;
			
			ArrayList<String> row = new ArrayList<>(3); // == 3, because: date, fundOutput, depositOutput
			row.add(data.get(i).get(0));
			row.add(fundOutputValue.toString());
			row.add(depositOutputValue.toString());
			outputList.add(row);
		}
		return outputList;
	}
	
	
public List<Exchange> compareIncomeRevisited(List<Exchange> data, BigDecimal inputValue, BigDecimal depPercentage) {
		
		List<Exchange> outputList = new ArrayList<Exchange>();
		
		int listSize = data.size();
		
		BigDecimal fundInputValue = inputValue;
		BigDecimal depositInputValue = inputValue;
		
		BigDecimal fundPercentage = null;
		BigDecimal depositPercentage = depPercentage;
		
		BigDecimal fundOutputValue = null;
		BigDecimal depositOutputValue = null;
		
		for(int i = 0 ; i < listSize ; i++) {

			fundPercentage = (BigDecimal)data.get(i).getRow()[1];
			
			fundOutputValue = calculateSingleIncome(fundInputValue, fundPercentage);
			fundOutputValue = fundOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			fundInputValue = fundOutputValue;
			
			depositOutputValue = calculateSingleIncome(depositInputValue, depositPercentage);
			depositOutputValue = depositOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			depositInputValue = depositOutputValue;
			
			Object[] container = {data.get(i).getRow()[0], fundOutputValue, depositOutputValue};
			Exchange row = new Exchange(container);
			outputList.add(row);
		}
		return outputList;
	}
	
}
