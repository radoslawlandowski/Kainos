package com.example.model;

import java.math.BigDecimal;
import java.sql.Date;

public class CalculationResults {
	private Date date;
	private BigDecimal fundValue;
	private BigDecimal depositValue;
	
	public CalculationResults(Date myDate, BigDecimal myFundValue, BigDecimal myDepositValue) {
		date = myDate;
		fundValue = myFundValue;
		depositValue = myDepositValue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getFundValue() {
		return fundValue;
	}

	public void setFundValue(BigDecimal fundValue) {
		this.fundValue = fundValue;
	}

	public BigDecimal getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(BigDecimal depositValue) {
		this.depositValue = depositValue;
	}
}
