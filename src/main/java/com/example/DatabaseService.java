package com.example;

import java.util.ArrayList;
import java.util.List;

public interface DatabaseService {
	void insertDataFromFile(String path);
	List<ArrayList<String>> selectAll();
	List<ArrayList<String>> selectWhereDataMatches(String startDate, String endDate);
	
}
