package com.example.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.example.model.Exchange;

public class CSVExchangeExtruderTest {

	private static final Logger logger = LogManager.getLogger(CSVExchangeExtruderTest.class);

	private String testFile = "/home/radek/Documents/newWorkspace/Kainos/src/test/resources/com.example.utils/forAppTests.csv";
	private BufferedReader reader = null;
	private CSVExchangeExtruder extr;

	
	@Before 
	public void setUp() {
		try {
			reader = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e) {
			logger.error("Test file not found!");
			e.printStackTrace();
		}
		extr = new CSVExchangeExtruder(reader);
	}
	/*
	@Ignore
	@Test
	public void extrudeExchangesWithCorrectSeparator() {
		List<Exchange> expected = new ArrayList<>();
		Object[] container = {"id", "data", "wiek"};
		Object[] containerTwo = {"1", "12/02/2001", "22"};
		Object[] containerThree = {"2", "14/12/2002", "12"};
		Object[] containerFour = {"3", "01/04/2004", "23"};

		Exchange ex = new Exchange(container);
		Exchange exTwo = new Exchange(containerTwo);
		Exchange exThree = new Exchange(containerThree);
		Exchange exFour = new Exchange(containerFour);
		
		expected.add(ex);
		expected.add(exTwo);
		expected.add(exThree);
		expected.add(exFour);
		
		
		List<Exchange> accuired = new ArrayList<>();
		while(extr.hasNext()) {
			accuired.add(extr.next());
		}
		assertEquals(expected, accuired);
	} */
	
	@Test(expected = IllegalArgumentException.class)
	public void extrudeExchangesWithIncorrectSeparator() {
		String incorrectSeparatorFile = "/home/radek/Documents/newWorkspace/Kainos/src/test/resources/com.example.utils/forAppTestsSemicolon.csv";
		try {
			reader = new BufferedReader(new FileReader(incorrectSeparatorFile));
		} catch (FileNotFoundException e) {
			logger.error("Test file not found!");
			e.printStackTrace();
		}
		extr.setReader(reader);
		while(extr.hasNext()) {
			extr.next();
		}
	}
	
	

}
