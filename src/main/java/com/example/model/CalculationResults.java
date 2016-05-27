package com.example.model;

import java.util.Arrays;

public class CalculationResults {
	private Object[] row;
	
	public CalculationResults(Object[] myRow) {
		this.row = myRow;
	}

	public Object[] getRow() {
		return row;
	}

	public void setRow(Object[] row) {
		this.row = row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(row);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculationResults other = (CalculationResults) obj;
		if (!Arrays.equals(row, other.row))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculationResults [row=" + Arrays.toString(row) + "]";
	}

}
