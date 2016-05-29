package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.database.DatabaseService;
import com.example.model.Exchange;
import com.example.utils.FormatTransformer;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {

	private static final Logger logger = LogManager.getLogger(ApiControllerTest.class);

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private ApiController apiController = new ApiController();

	@Mock
	private DatabaseService mockService;

	@Mock
	private FormatTransformer mockTransformer;

	String start = "2011-02-04";
	String end = "2011-02-05";

	Date sDate;
	Date eDate;

	List<Exchange> exchangeList;

	ResponseEntity<List<Exchange>> response;

	private Date prepareDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date trueDate = null;
		java.sql.Date resultDate = null;
		try {
			trueDate = sdf.parse(date);
			resultDate = new Date(trueDate.getTime());
		} catch (ParseException e) {
			logger.error("The date cannot be parsed. Check your string format. [Should be: yyyy-MM-dd]", e);
		}
		return resultDate;
	}

	@Before
	public void setUp() {
		apiController.service = mockService;
		apiController.transformer = mockTransformer;
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
		sDate = prepareDate(start);
		eDate = prepareDate(end);

		BigDecimal val1 = new BigDecimal(102);
		BigDecimal val2 = new BigDecimal(102.3);

		Exchange ex1 = new Exchange(sDate, val1);
		Exchange ex2 = new Exchange(eDate, val2);

		exchangeList = new ArrayList<>();
		exchangeList.add(ex1);
		exchangeList.add(ex2);

		response = new ResponseEntity<List<Exchange>>(exchangeList, HttpStatus.OK);
	}

	@Test
	public void dataExchangeRequestWithNoGivenArguments() {
		String noArgumentRequestString = "/dataExchange";
		try {
			mockMvc.perform(get(noArgumentRequestString)).andExpect(status().is4xxClientError());
		} catch (Exception e) {
			logger.error("Couldn't make /dataExchange request", e);
		}
	}

	@Test
	public void dataExchangeRequestWithOneArgumentGiven() {
		String incompleteRequestString = "/dataExchange?startdate=" + start;
		try {
			mockMvc.perform(get(incompleteRequestString)).andExpect(status().is4xxClientError());
		} catch (Exception e) {
			logger.error("Couldn't make /dataExchange request", e);
		}
	}

	@Test
	public void dataExchangeRequestWithMalformedArgumentNames() {
		String malformedRequestString = "/dataExchange?stMALFORMEDte=" + start + "&enMALFORMEDd=" + end;
		try {
			mockMvc.perform(get(malformedRequestString)).andExpect(status().is4xxClientError());
		} catch (Exception e) {
			logger.error("Couldn't make /dataExchange request", e);
		}
	}

	@Test
	public void dataExchangeRequestWithEmptyArguments() {
		String emptyArgumentsRequestString = "/dataExchange?startdate=&enddate=";
		try {
			mockMvc.perform(get(emptyArgumentsRequestString)).andExpect(status().isOk());
		} catch (Exception e) {
			logger.error("Couldn't make /dataExchange request", e);
		}
	}

	@Ignore
	@Test
	public void dataExchangeRequestWithCorrectParameters() {
		String correctArgumentsRequestString = "/dataExchange?startdate=" + start + "&enddate=" + end;

		Mockito.when(mockTransformer.getDate(start)).thenReturn(sDate);
		Mockito.when(mockTransformer.getDate(end)).thenReturn(eDate);
		Mockito.when(mockService.selectWhereDate(sDate, eDate)).thenReturn(exchangeList);

		MvcResult result = null;
		String resultResponse = null;
		try {
			mockMvc.perform(get(correctArgumentsRequestString)).andExpect(status().isOk())
					.andExpect(content().contentType(APPLICATION_JSON_UTF8));
			resultResponse = result.getResponse().getContentAsString();
		} catch (Exception e) {
			logger.error("Couldn't make /dataExchange request", e);
		}

		logger.info(resultResponse);
	}

}
