package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.example.model.CalculationResults;
import com.example.model.Exchange;

public class MoneyCalculator {

	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	public static final int monetizationPeriod = 25; // TODO: implement better monetization period algorithm
	
	private BigDecimal calculateSingleIncome(BigDecimal inputValue, BigDecimal percentage) {
		BigDecimal val = inputValue.multiply(percentage).divide(ONE_HUNDRED);
		return val;
	}

public List<CalculationResults> compareIncomeFinal(List<Exchange> data, BigDecimal inputValue, BigDecimal depPercentage) {
	
	List<CalculationResults> outputList = new ArrayList<>();
	
	BigDecimal basePercentage = (BigDecimal)data.get(0).getValue();
	BigDecimal depositInputValue = inputValue;
	BigDecimal depositOutputValue = depositInputValue;
	
	for(int i = 0 ; i < data.size() ; i++) {
		BigDecimal currentPercentage = (BigDecimal)data.get(i).getValue();
		BigDecimal diffPercentage = currentPercentage.subtract(basePercentage).add(ONE_HUNDRED);
		BigDecimal fundValue = calculateSingleIncome(inputValue, diffPercentage);
		fundValue = fundValue.setScale(2, RoundingMode.HALF_EVEN);
		
		if(i % monetizationPeriod == 0 && i != 0) {
			depositOutputValue = calculateSingleIncome(depositInputValue, depPercentage);
			depositOutputValue = depositOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			depositInputValue = depositOutputValue;
		}
		
		Object[] container = {data.get(i).getDate(), fundValue, depositOutputValue};
		CalculationResults res = new CalculationResults(data.get(i).getDate(), fundValue, depositOutputValue);
		outputList.add(res);
	}

	return outputList;
}

}
