package com.example;

import java.sql.Date;
import java.util.List;

import com.example.model.Exchange;

public interface DatabaseService {
	void initializeDatabase();
	void insertDataFromFileRevisited(String path);
	List<Exchange> selectAllRevisited();
	List<Exchange> selectWhereDateMatchesRevisited(Date startDate, Date endDate);
	
}
