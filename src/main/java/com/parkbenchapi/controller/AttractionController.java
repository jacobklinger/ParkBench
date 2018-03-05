package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.AttractionDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.AttractionResponse;

@RestController
public class AttractionController {

	@Autowired
	AttractionDao dao;
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/attractions")
	ResponseEntity<AttractionResponse> getAttractions(@PathVariable String resortId, @PathVariable String parkId) {
		AttractionResponse attractions = new AttractionResponse(dao.getAttractions(resortId, parkId));
		return new ResponseEntity<AttractionResponse>(attractions, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/attractions/{attractionId}")
	ResponseEntity<Attraction> getAttractionById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String attractionId) {
		return new ResponseEntity<Attraction>(dao.getAttractionById(resortId, parkId, attractionId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/attractions/location")
	ResponseEntity<AttractionResponse> getAttractionsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		AttractionResponse attractions = new AttractionResponse(dao.getAttractionsByLocation(longitude, latitude, radius));
		return new ResponseEntity<AttractionResponse>(attractions, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/attractions")
	ResponseEntity<AttractionResponse> getAllAttractions(@RequestParam(value = "name", required = false)  String attractionName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		AttractionResponse attractions = new AttractionResponse();
		if (parkName != null || resortName != null || attractionName != null) {
			attractions.setAttractions(dao.getAllAttractions(attractionName,  parkName, resortName));
			return new ResponseEntity<AttractionResponse>(attractions, HttpStatus.OK);
		}
		else {
			attractions.setAttractions(dao.getAllAttractions());
			return new ResponseEntity<AttractionResponse>(attractions, HttpStatus.OK);
		}
	}

}