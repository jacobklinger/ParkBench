package com.parkbenchapi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkBenchController {
	
	@RequestMapping(method = RequestMethod.GET, value="/healthcheck")
	public ResponseEntity<String> healthcheck() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/")
	public void forward(HttpServletResponse response) throws IOException {
		response.sendRedirect("/resorts");
	}

}
