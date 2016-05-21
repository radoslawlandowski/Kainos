package com.example;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.utils.DatabaseConnector;

@Service
public class DatabaseServiceImpl implements DatabaseService {
	
	private DatabaseConnector connector;
	private SQLRepository repo;
	
	public DatabaseServiceImpl(DatabaseConnector myConnector, SQLRepository myRepo) {
		this.connector = myConnector;
		this.repo = myRepo;
	}

	@Override
	public void insertDataFromFile(String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ArrayList<String>> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArrayList<String>> selectWhereDataMatches(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
