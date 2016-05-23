package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.DatabaseConnector;
import com.example.utils.DateConverter;
import com.example.utils.ExchangeTransformer;

public class DatabaseServiceImpl implements DatabaseService {
	
	private static final Logger logger = LogManager.getLogger(DatabaseServiceImpl.class);
	
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
			logger.error("File not found. Check your path");
			e1.printStackTrace(); 
		}
   	 	
   	 	extruder.setReader(br);
     	extruder.next(); // skip first row 'cause it contains column names
     	while(extruder.hasNext()) {
     		Exchange ex = extruder.next();
     		ex.getRow()[0] = DateConverter.changeDateFormat((String)ex.getRow()[0]);
     		repo.insertRow((String[])ex.getRow());
     	}     	
	}
	
	@Override
	public void insertDataFromFileRevisited(String path) {
   	    BufferedReader br = null;
   	 	try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
			logger.error("File not found. Check your path");
			e1.printStackTrace(); 
		}
   	 	extruder.setReader(br);
     	extruder.next(); // skip first row 'cause it contains column names
     	while(extruder.hasNext()) {
     		Exchange ex = extruder.next();
     		ExchangeTransformer.transform(ex);
     		repo.insertRowRevisited(ex.getRow());
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
	public List<Exchange> selectAllRevisited() {
		List<Exchange> result;
		String[] allColumns = repo.getTable().getColumnNames();
		result = repo.selectRevisited(allColumns);
		return result;
	}

	@Override
	public List<Exchange> selectWhereDateMatchesRevisited(Date startDate, Date endDate) {
		List<Exchange> list;
		list = repo.selectWhereDateMatchesRevisited(startDate, endDate);
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
