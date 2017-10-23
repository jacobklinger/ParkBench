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

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/entertainment")
	ResponseEntity<ArrayList<Entertainment>> getEntertainment(@PathVariable String resortId, @PathVariable String parkId) {
		return new ResponseEntity<ArrayList<Entertainment>>(dao.getEntertainment(resortId, parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/entertainment/{entertainmentId}")
	ResponseEntity<Entertainment> getEntertainmentById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String entertainmentId) {
		return new ResponseEntity<Entertainment>(dao.getEntertainmentById(resortId, parkId, entertainmentId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/entertainment")
	ResponseEntity<ArrayList<Entertainment>> getAllAttractions(@RequestParam(value = "name", required = false)  String entertainmentName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (parkName != null || resortName != null || entertainmentName != null) {
			return new ResponseEntity<ArrayList<Entertainment>>(dao.getAllEntertainment(entertainmentName,  parkName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Entertainment>>(dao.getAllEntertainment(), HttpStatus.OK);
		}
	}

}