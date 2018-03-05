package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.EntertainmentDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.EntertainmentResponse;

@RestController
public class EntertainmentController {

	@Autowired
	EntertainmentDao dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/entertainment")
	ResponseEntity<EntertainmentResponse> getEntertainment(@PathVariable String resortId, @PathVariable String parkId) {
		EntertainmentResponse entertainment = new EntertainmentResponse(dao.getEntertainment(resortId, parkId));
		return new ResponseEntity<EntertainmentResponse>(entertainment, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/entertainment/{entertainmentId}")
	ResponseEntity<Entertainment> getEntertainmentById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String entertainmentId) {
		return new ResponseEntity<Entertainment>(dao.getEntertainmentById(resortId, parkId, entertainmentId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/entertainment")
	ResponseEntity<EntertainmentResponse> getAllAttractions(@RequestParam(value = "name", required = false)  String entertainmentName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		EntertainmentResponse entertainment = new EntertainmentResponse();
		if (parkName != null || resortName != null || entertainmentName != null) {
			entertainment.setEntertainments(dao.getAllEntertainment(entertainmentName,  parkName, resortName));
			return new ResponseEntity<EntertainmentResponse>(entertainment, HttpStatus.OK);
		}
		else {
			entertainment.setEntertainments(dao.getAllEntertainment());
			return new ResponseEntity<EntertainmentResponse>(entertainment, HttpStatus.OK);
		}
	}

}