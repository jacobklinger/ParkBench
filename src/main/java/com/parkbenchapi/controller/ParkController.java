package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.ParkDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.ParksResponse;

@RestController
public class ParkController {

	@Autowired
	private ParkDao dao;
	
//	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
//	ResponseEntity<String> healthcheck() {
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks")
	ResponseEntity<ParksResponse> getParks(@PathVariable String resortId) {
		ParksResponse parks = new ParksResponse(dao.getParks(resortId));
		return new ResponseEntity<ParksResponse>(parks, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}")
	ResponseEntity<Park> getParkById(@PathVariable String resortId, @PathVariable String parkId) {
		return new ResponseEntity<Park>(dao.getParkById(resortId, parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/parks/location")
	ResponseEntity<ParksResponse> getParkByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		ParksResponse parks = new ParksResponse(dao.getParkByLocation(longitude, latitude, radius));
		return new ResponseEntity<ParksResponse>(parks, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/parks")
	ResponseEntity<ParksResponse> getAllParks(@RequestParam(value = "name", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		ParksResponse parks = new ParksResponse();
		if (parkName != null || resortName != null) {
			parks.setParks(dao.getAllParks(parkName, resortName));
			return new ResponseEntity<ParksResponse>(parks, HttpStatus.OK);
		}
		else {
			parks.setParks(dao.getAllParks());
			return new ResponseEntity<ParksResponse>(parks, HttpStatus.OK);
		}
	}

}
