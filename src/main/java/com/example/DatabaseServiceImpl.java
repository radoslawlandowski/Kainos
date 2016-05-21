package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.DatabaseConnector;
import com.example.utils.DateConverter;

public class DatabaseServiceImpl implements DatabaseService {
	
	@Autowired
	private DatabaseConnector connector;
	
	@Autowired
	private CSVExchangeExtruder extruder;
	
	private SQLRepository repo;
	
	public DatabaseServiceImpl() {
	}

	@Override
	public void insertDataFromFile(String path) {
   	    BufferedReader br = null;
   	 	try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace(); 
		}
   	 	
   	 	extruder.setReader(br);
     	extruder.next(); // skip first row 'cause it contains column name
     	while(extruder.hasNext()) {
     		Exchange ex = extruder.next();
     		ex.getRow()[0] = DateConverter.replace((String)ex.getRow()[0]);
     		repo.insertRow((String[])ex.getRow());
     	}     	
	}

	@Override
	public List<ArrayList<String>> selectAll() {
		List<ArrayList<String>> list;
		String[] allColumns = repo.getTable().getColumnNames();
		list = repo.select(allColumns);
		return list;
	}

	@Override
	public List<ArrayList<String>> selectWhereDataMatches(String startDate, String endDate) {
		List<ArrayList<String>> list;
		list = repo.selectWhereDateMatches(startDate, endDate);
		return list;
	}

	@Override
	public void initializeDatabase() {
		String tableName = "kainos";
     	String[] columnNames = {"mydate", "val"};
     	DataTypes[] types = {DataTypes.DATE, DataTypes.CHAR};
     	int firstColumnLength = 0;
    	int secondColumnLength = 6;
     	int[] sizes = {firstColumnLength, secondColumnLength};
     	SQLTable myTable = new SQLTable(tableName, columnNames, types, sizes);
     	
     	Connection c = null;
     	try {
			c = connector.connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
     	
	    this.repo = new SQLRepository(c, myTable);
   	    repo.createTableInsideDatabase();
   	    
	}
}
