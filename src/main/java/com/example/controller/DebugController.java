package com.example.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {
	
	private static final Logger logger = LogManager.getLogger(DebugController.class);

	@RequestMapping(value = "/debug", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> debug() {
		logger.info("Debug site entered!");
		String ex = "Debug okej!";

		return new ResponseEntity<String>(ex, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/debug2", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> debug2() {
		logger.info("Debug site entered!");
		String ex = "Debug dwa okej!";

		return new ResponseEntity<String>(ex, HttpStatus.OK);
	}
}
