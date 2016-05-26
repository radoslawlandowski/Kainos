package com.example.controller;

import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.database.DatabaseService;

import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class InternalControllerTest {

	private MockMvc mockMvc;
	
	private InternalController InternalController = new InternalController();
	
	private static final String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
	
	@Mock
	private DatabaseService mockService;
	
	@Before
	public void setUp() {
		InternalController.service = mockService;
		mockMvc = MockMvcBuilders.standaloneSetup(InternalController).build();
	}
	
	@Test
	public void test() {
		
		try {
			mockMvc.perform(get("/dataExchange")
					.param("startDate", "2011-02-02")
					.param("endDate", "2011-04-02"))
					.andExpect(status().isOk())
					;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
