package com.example.model;
 
import java.math.BigDecimal;
import java.sql.Date;
 
public class Exchange {
	
	private Date date;
	private BigDecimal value;
	
	public Exchange(Date myDate, BigDecimal myValue) {
		date = myDate;
		value = myValue;
	}
	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public BigDecimal getValue() {
		return value;
	}


	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Exchange [date=" + date + ", value=" + value + "]";
	}

	
}
