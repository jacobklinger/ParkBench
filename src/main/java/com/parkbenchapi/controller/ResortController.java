package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.ResortDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.ResortsResponse;

@RestController
public class ResortController {

	@Autowired
	private ResortDao dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts")
	ResponseEntity<ResortsResponse> getResorts(@RequestParam(value = "name", required = false)  String name) {
		ResortsResponse result = new ResortsResponse();
		if (name != null) {
			result.setResorts(dao.getAllResorts(name));
			return new ResponseEntity<ResortsResponse>(result, HttpStatus.OK);
		}
		else {
			result.setResorts(dao.getAllResorts());
			return new ResponseEntity<ResortsResponse>(result, HttpStatus.OK);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}")
	ResponseEntity<Resort> getResortById(@PathVariable String resortId) {
		return new ResponseEntity<Resort>(dao.getResortById(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/location")
	ResponseEntity<ResortsResponse> getResortsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		ResortsResponse result = new ResortsResponse(dao.getResortsByLocation(longitude, latitude, radius));
		return new ResponseEntity<ResortsResponse>(result, HttpStatus.OK);
	}

}
