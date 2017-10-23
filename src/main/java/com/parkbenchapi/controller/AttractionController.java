package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class AttractionController {

	@Autowired
	DataAccessObjectImpl dao;
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/attractions")
	ResponseEntity<ArrayList<Attraction>> getAttractions(@PathVariable String resortId, @PathVariable String parkId) {
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAttractions(resortId, parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/attractions/{attractionId}")
	ResponseEntity<Attraction> getAttractionById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String attractionId) {
		return new ResponseEntity<Attraction>(dao.getAttractionById(resortId, parkId, attractionId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/attractions/location")
	ResponseEntity<ArrayList<Attraction>> getAttractionsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAttractionsByLocation(longitude, latitude, radius), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/attractions")
	ResponseEntity<ArrayList<Attraction>> getAllAttractions(@RequestParam(value = "name", required = false)  String attractionName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (parkName != null || resortName != null || attractionName != null) {
			return new ResponseEntity<ArrayList<Attraction>>(dao.getAllAttractions(attractionName,  parkName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Attraction>>(dao.getAllAttractions(), HttpStatus.OK);
		}
	}

}