package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.database.DatabaseService;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {

	private MockMvc mockMvc;
	
	private ApiController apiController = new ApiController();
		
	@Mock
	private DatabaseService mockService;
	
	@Before
	public void setUp() {
		apiController.service = mockService;
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
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
