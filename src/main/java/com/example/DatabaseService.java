package com.example;

import java.sql.Date;
import java.util.List;

import com.example.model.Exchange;

public interface DatabaseService {
	void initializeDatabase();
	void insertDataFromFile(String path);
	List<Exchange> selectAll();
	List<Exchange> selectWhereDate(Date startDate, Date endDate);
	
}
