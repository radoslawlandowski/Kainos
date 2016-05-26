package com.example.controller;

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

@RunWith(MockitoJUnitRunner.class)
public class KainosControllerTest {

	private MockMvc mockMvc;
	
	private KainosController KainosController = new KainosController();
	
	private static final String path = "/home/radek/Documents/newWorkspace/Kainos/src/main/resources/data.csv";
	
	@Mock
	private DatabaseService mockService;
	
	@Before
	public void setUp() {
		KainosController.service = mockService;
		mockMvc = MockMvcBuilders.standaloneSetup(KainosController).build();
	}
	
	@Test
	public void sendGetRequestWhenDatabaseNotInitialized() {
		
		Mockito.when(mockService.isInitialized()).thenReturn(false);
		
		try {
			mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("mainIndex"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(mockService, times(1)).initializeDatabase();
		verify(mockService, times(1)).insertDataFromFile(path);
	}
	
	@Test
	public void sendGetRequestWhenDatabaseInitialized() {
		
		Mockito.when(mockService.isInitialized()).thenReturn(true);
		
		try {
			mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("mainIndex"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(mockService, times(0)).initializeDatabase();
		verify(mockService, times(0)).insertDataFromFile(path);
	}
	
	@Test
	public void sendGetCompareRequestWhenDatabaseInitialized() {
		
		Mockito.when(mockService.isInitialized()).thenReturn(false);
		
		try {
			mockMvc.perform(get("/compare"))
			.andExpect(status().isOk())
			.andExpect(view().name("compare"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(mockService, times(1)).initializeDatabase();
		verify(mockService, times(1)).insertDataFromFile(path);
	}
	
	@Test
	public void sendGetCompareRequestWhenDatabaseNotInitialized() {
		
		Mockito.when(mockService.isInitialized()).thenReturn(true);
		
		try {
			mockMvc.perform(get("/compare"))
			.andExpect(status().isOk())
			.andExpect(view().name("compare"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(mockService, times(0)).initializeDatabase();
		verify(mockService, times(0)).insertDataFromFile(path);
	}

}
