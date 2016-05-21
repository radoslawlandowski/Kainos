package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Exchange;
import com.example.utils.CSVExchangeExtruder;
import com.example.utils.DateConverter;

@RestController
public class KainosController {

    @RequestMapping("/")
    public String index() {
 
    	Connection c = null;
    	 try {
			c = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 
    	final int firstColumnLength = 10;
    	final int secondColumnLength = 6;
     	
     	String tableName = "kainos";
     	String[] columnNames = {"mydate", "val"};
     	DataTypes[] types = {DataTypes.CHAR, DataTypes.CHAR};
     	int[] sizes = {firstColumnLength, secondColumnLength};
     	SQLTable myTable = new SQLTable(tableName, columnNames, types, sizes);
     	SQLRepository repo = new SQLRepository(c, myTable);
   	    repo.createTableInsideDatabase();

     	String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
 		BufferedReader br = null;
     	try {
 			br = new BufferedReader(new FileReader(path));
 		} catch (FileNotFoundException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace(); 
 		}
     	
     	CSVExchangeExtruder ext = new CSVExchangeExtruder(br);
     	
     	List<Exchange> lista = new ArrayList<>();
     	int i = 0;
     	
     	ext.next(); // skip first row as it contains column name
     	while(ext.hasNext()) {
     			Exchange ex = ext.next();
     		    repo.insertRow((String[])ex.getRow());
     	}     	
        return repo.select(columnNames).toString();
    }

}