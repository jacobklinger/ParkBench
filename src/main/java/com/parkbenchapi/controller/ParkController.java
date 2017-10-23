package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class ParkController {

	@Autowired
	DataAccessObjectImpl dao;
	
//	@RequestMapping(value = "/heartbeat", method = RequestMethod.GET)
//	ResponseEntity<String> healthcheck() {
//		return new ResponseEntity<String>("OK", HttpStatus.OK);
//	}

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks")
	ResponseEntity<ArrayList<Park>> getParks(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Park>>(dao.getParks(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}")
	ResponseEntity<Park> getParkById(@PathVariable String resortId, @PathVariable String parkId) {
		return new ResponseEntity<Park>(dao.getParkById(resortId, parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/parks/location")
	ResponseEntity<ArrayList<Park>> getParkByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		return new ResponseEntity<ArrayList<Park>>(dao.getParkByLocation(longitude, latitude, radius), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/parks")
	ResponseEntity<ArrayList<Park>> getAllParks(@RequestParam(value = "name", required = false)  String parkName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (parkName != null || resortName != null) {
			return new ResponseEntity<ArrayList<Park>>(dao.getAllParks(parkName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Park>>(dao.getAllParks(), HttpStatus.OK);
		}
	}

}
