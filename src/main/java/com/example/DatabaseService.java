package com.example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.example.model.Exchange;

public interface DatabaseService {
	void initializeDatabase();
	void insertDataFromFile(String path);
	void insertDataFromFileRevisited(String path);
	List<ArrayList<String>> selectAll();
	List<Exchange> selectAllRevisited();
	List<ArrayList<String>> selectWhereDataMatches(String startDate, String endDate);
	List<Exchange> selectWhereDateMatchesRevisited(Date startDate, Date endDate);
	
}
