package com.example.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.infrastructure.SQLRepository;
import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.FormatTransformer;

public class DatabaseServiceImpl implements DatabaseService {
	
	private static final Logger logger = LogManager.getLogger(DatabaseServiceImpl.class);
	
	@Autowired
	private DatabaseConnector connector;
	
	@Autowired
	private CSVExchangeExtruder extruder;
	
	@Autowired
	private FormatTransformer transformer;
	
	private SQLRepository repo;
	
	public DatabaseServiceImpl() {
	}
	
	@Override
	public boolean isInitialized() {
		boolean result = false;
		if(repo == null) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}

	@Override
	public void insertDataFromFile(String fileName) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
   	 	extruder.setReader(br);
     	extruder.next(); // skip first row 'cause it contains column names
     	while(extruder.hasNext()) {
     		Exchange ex = extruder.next();
     		transformer.transform(ex);
     		repo.insertRowRevisited(ex.getRow());
     	}     	
	}

	@Override
	public List<Exchange> selectAll() {
		List<Exchange> result;
		result = repo.selectRevisited();
		return result;
	}

	@Override
	public List<Exchange> selectWhereDate(Date startDate, Date endDate) {
		List<Exchange> list;
		list = repo.selectWhereDateMatchesRevisited(startDate, endDate);
		return list;
	}

	@Override
	public void initializeDatabase() {     	
     	Connection c = null;
     	try {
			c = connector.connect();
		} catch (SQLException e) {
			logger.error("Couldn't obtain database connetion", e);
		}
     	
	    this.repo = new SQLRepository(c);
   	    repo.createTableInsideDatabaseRevisited();
	}
}
