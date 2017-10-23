package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class ResortController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts")
	ResponseEntity<ArrayList<Resort>> getResorts(@RequestParam(value = "name", required = false)  String name) {
		if (name != null) {
			return new ResponseEntity<ArrayList<Resort>>(dao.getAllResorts(name), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Resort>>(dao.getResorts(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}")
	ResponseEntity<Resort> getResortById(@PathVariable String resortId) {
		return new ResponseEntity<Resort>(dao.getResortById(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/location")
	ResponseEntity<ArrayList<Resort>> getResortsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		return new ResponseEntity<ArrayList<Resort>>(dao.getResortsByLocation(longitude, latitude, radius), HttpStatus.OK);
	}

}
