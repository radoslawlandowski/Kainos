package com.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.model.CalculationResults;
import com.example.model.Exchange;

public class MoneyCalculator {
	
	private static final Logger logger = LogManager.getLogger(MoneyCalculator.class);
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	public static final long monetizationPeriod = 30;
	
	private BigDecimal calculateSingleIncome(BigDecimal inputValue, BigDecimal percentage) {
		BigDecimal val = inputValue.multiply(percentage).divide(ONE_HUNDRED);
		return val;
	}

public List<CalculationResults> compareIncomeFinal(List<Exchange> data, BigDecimal inputValue, BigDecimal depPercentage) {
	
	List<CalculationResults> outputList = new ArrayList<>();
	
	if (data.size()==0) {
		logger.info("There is no data to be processed");
		return outputList;
	}
	
	BigDecimal basePercentage = data.get(0).getValue();
	BigDecimal depositInputValue = inputValue;
	BigDecimal depositOutputValue = depositInputValue;
	
	Date date = data.get(0).getDate();
	Date presentDate = date;

	LocalDate periodStart = date.toLocalDate();
	LocalDate presentLocalDate = presentDate.toLocalDate();

	for (int i = 0 ; i < data.size() ; i++) {
		BigDecimal currentPercentage = (BigDecimal)data.get(i).getValue();
		BigDecimal diffPercentage = currentPercentage.subtract(basePercentage).add(ONE_HUNDRED);
		BigDecimal fundValue = calculateSingleIncome(inputValue, diffPercentage);
		fundValue = fundValue.setScale(2, RoundingMode.HALF_EVEN);
		presentDate = data.get(i).getDate();
		presentLocalDate = presentDate.toLocalDate();
		long difference = ChronoUnit.DAYS.between(periodStart, presentLocalDate);
		if (difference >= monetizationPeriod) {
			depositOutputValue = calculateSingleIncome(depositInputValue, depPercentage);
			depositOutputValue = depositOutputValue.setScale(2, RoundingMode.HALF_EVEN);
			depositInputValue = depositOutputValue;
			periodStart = presentLocalDate;
		}
		CalculationResults res = new CalculationResults(presentDate, fundValue, depositOutputValue);
		outputList.add(res);
	}
	return outputList;
}

}
