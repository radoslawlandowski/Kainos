package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Exchange;

public class MoneyCalculator {

	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static final int monetization = 30;
	
	private BigDecimal calculateSingleIncome(BigDecimal inputValue, BigDecimal percentage) {
		BigDecimal val = inputValue.multiply(percentage).divide(ONE_HUNDRED);
		return val;
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

public List<Exchange> compareIncomeRevisitedNormalized(List<Exchange> data, BigDecimal inputValue, BigDecimal depPercentage) {
	
	List<Exchange> outputList = new ArrayList<Exchange>();
	
	int listSize = data.size();
	
	BigDecimal fundInputValue = inputValue;
	BigDecimal depositInputValue = inputValue;
	
	BigDecimal fundPercentage = new BigDecimal(100);
	BigDecimal averagedFundPercentage = new BigDecimal(0);
	BigDecimal depositPercentage = depPercentage;
	
	BigDecimal fundOutputValue = new BigDecimal(0);
	BigDecimal depositOutputValue = new BigDecimal(0);
	
	for(int i = 0 ; i < listSize ; i++) {

		averagedFundPercentage = averagedFundPercentage.add((BigDecimal)data.get(i).getRow()[1]);
		
		fundOutputValue = fundInputValue;
		depositOutputValue = depositInputValue;
		
		if(i % monetization == 0 && i != 0) {
			fundPercentage = averagedFundPercentage.divide(new BigDecimal(monetization), 2, RoundingMode.HALF_EVEN);
			fundOutputValue = calculateSingleIncome(fundInputValue, fundPercentage);
			depositOutputValue = calculateSingleIncome(depositInputValue, depositPercentage);
			fundOutputValue = fundOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			fundInputValue = fundOutputValue;
			depositOutputValue = depositOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			depositInputValue = depositOutputValue;
			averagedFundPercentage = new BigDecimal(100);
		}
		
		Object[] container = {data.get(i).getRow()[0], fundOutputValue, depositOutputValue};
		Exchange row = new Exchange(container);
		outputList.add(row);
	}
	return outputList;
}
	
}
