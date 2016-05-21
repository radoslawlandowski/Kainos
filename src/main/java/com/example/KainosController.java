package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.DatabaseConnector;
import com.example.utils.DateConverter;
import com.example.utils.HSQLDBConnector;

@RestController
public class KainosController {
	
	private static final Logger logger = LogManager.getLogger(KainosController.class);

    @RequestMapping("/")
    public String index() {
        final int firstColumnLength = 0;
    	final int secondColumnLength = 6;

    	Connection c = null;
    	DatabaseConnector con = new HSQLDBConnector();
    	try {
			c = con.connect();
		} catch (SQLException e) {
			logger.error("Could not connect to database");
 			e.printStackTrace();
		}
 
     	String tableName = "kainos";
     	String[] columnNames = {"mydate", "val"};
     	DataTypes[] types = {DataTypes.DATE, DataTypes.CHAR};
     	int[] sizes = {firstColumnLength, secondColumnLength};
     	SQLTable myTable = new SQLTable(tableName, columnNames, types, sizes);
     	
     	SQLRepository repo = new SQLRepository(c, myTable);
   	    repo.createTableInsideDatabase();

     	String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
 		BufferedReader br = null;
     	try {
 			br = new BufferedReader(new FileReader(path));
 		} catch (FileNotFoundException e1) {
 			e1.printStackTrace(); 
 		}
     	
     	CSVExchangeExtruder ext = new CSVExchangeExtruder(br);
     	
     	List<Exchange> lista = new ArrayList<>();
     	int i = 0;
     	
     	ext.next(); // skip first row 'cause it contains column name
     	while(ext.hasNext()) {
     			Exchange ex = ext.next();
     			ex.getRow()[0] = DateConverter.replace((String)ex.getRow()[0]);
     		    repo.insertRow((String[])ex.getRow());
     			lista.add(ex);
     	}     	
     	
     	List<ArrayList<String>> lista2 = repo.selectWhereDateMatches("1998-01-05", "1998-01-10");
        return lista2.toString(); //repo.select(columnNames).toString();
    }

}