package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class EntertainmentController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET, value = "/entertainment")
	ResponseEntity<ArrayList<Entertainment>> getAllAttractions() {
		return new ResponseEntity<ArrayList<Entertainment>>(dao.getAllEntertainment(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/entertainment/{id}")
	ResponseEntity<Entertainment> getParkByName(@PathVariable String id) {
		return new ResponseEntity<Entertainment>(dao.getEntertainmentById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/parks/{parkId}/entertainment")
	ResponseEntity<ArrayList<Entertainment>> getAttractionsByParkId(@PathVariable String parkId) {
		return new ResponseEntity<ArrayList<Entertainment>>(dao.getEntertainmentByPark(parkId), HttpStatus.OK);
	}

}