package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class ServiceController {
	
	@Autowired
	DataAccessObjectImpl dao;
	
	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
	ResponseEntity<String> healthcheck() {
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
	@RequestMapping("/parks/")
	ResponseEntity<ArrayList<LocatableEntity>> getAllParks() {
		return new ResponseEntity<ArrayList<LocatableEntity>>(dao.getAllParks(), HttpStatus.OK);
	}

}
