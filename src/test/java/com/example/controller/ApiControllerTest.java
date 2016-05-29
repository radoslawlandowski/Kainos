package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.database.DatabaseService;
import com.example.utils.FormatTransformer;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {
	
	private static final Logger logger = LogManager.getLogger(ApiControllerTest.class);

	private MockMvc mockMvc;
	
	private ApiController apiController = new ApiController();
		
	@Mock
	private DatabaseService mockService;
	
	@Mock
	private FormatTransformer mockTransformer;
		
	private Date prepareDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date trueDate = null;
		try {
			trueDate = sdf.parse(date);
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: yyyy-MM-dd]", e);
		}
        java.sql.Date resultDate = new Date(trueDate.getTime()); 
        return resultDate;
	}
	
	@Before
	public void setUp() {
		apiController.service = mockService;
		apiController.transformer = mockTransformer;
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}
	
	@Ignore
	@Test
	public void dataExchangeRequestWithNoGivenArguments() {
		String start = "2011-02-04";
		String end = "2011-02-05";
		
		
		
		Date sDate = prepareDate(start);
		Date eDate = prepareDate(end);
		
		Mockito.when(mockTransformer.getDateFromHtml(start)).thenReturn(sDate);
		Mockito.when(mockTransformer.getDateFromHtml(end)).thenReturn(eDate);
		
	//	Mockito.when(mockService.selectAll()).thenReturn(value)
		
		try {
			mockMvc.perform(get("/dataExchange")).andExpect(status().isOk());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
