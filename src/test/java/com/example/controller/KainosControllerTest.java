package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(MockitoJUnitRunner.class)
public class KainosControllerTest {

	private static final Logger logger = LogManager.getLogger(KainosControllerTest.class);
	
	private MockMvc mockMvc;

	@Before
	public void setUp() {

	}
	
	@Test
	public void sendGetRequest() {		
		try {
			mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("mainIndex"));
		} catch (Exception e) {
			logger.error("Cannot perform '/' request", e);
		}
	}
	
	@Test
	public void sendGetCompareRequest() {
		try {
			mockMvc.perform(get("/compare"))
			.andExpect(status().isOk())
			.andExpect(view().name("compTest"));
		} catch (Exception e) {
			logger.error("Cannot perform '/compare' request", e);
		}
	}

}
