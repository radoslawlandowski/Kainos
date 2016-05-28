package com.example.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.infrastructure.SQLRepository;
import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.FormatTransformer;

public class DatabaseServiceImpl implements DatabaseService {
	
	private static final Logger logger = LogManager.getLogger(DatabaseServiceImpl.class);
	private static final String fileName = "data.csv";

	@Autowired
	DatabaseConnector connector;
	
	@Autowired
	CSVExchangeExtruder extruder;
	
	@Autowired
	FormatTransformer transformer;
	
	private SQLRepository repo;
	
	public DatabaseServiceImpl() {}
	
	@Override
	public boolean isInitialized() {
		boolean result = repo != null;
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
     		Date date = ex.getDate();
     		BigDecimal value = ex.getValue();
     		repo.insertRowRevisited(date, value);
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

	@PostConstruct
	@Override
	public void initializeDatabase() {
		Connection c = null;
		try {
			c = connector.connect();
		} catch (SQLException e) {
			logger.error("Can't connect to the database", e);
		}
		repo = new SQLRepository(c);
   	    repo.createTableInsideDatabaseRevisited();
   	    this.insertDataFromFile(fileName);
   	    logger.info("DATABASE INITIALIZED POSTCONSTRUCT");
   	    
	}
	
}
