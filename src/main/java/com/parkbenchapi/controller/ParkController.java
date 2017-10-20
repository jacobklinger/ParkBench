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

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<ArrayList<Park>> getAllParks() {
		return new ResponseEntity<ArrayList<Park>>(dao.getAllParks(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/parks/{id}")
	ResponseEntity<Park> getParkByName(@PathVariable String id) {
		return new ResponseEntity<Park>(dao.getParkById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/resorts/{resortId}/parks")
	ResponseEntity<ArrayList<Park>> getParksByResortId(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Park>>(dao.getParksByResort(resortId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/parks/location")
	ResponseEntity<ArrayList<Park>> getParkByLocation(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("radius") String radius) {
		double longitudeDouble = Double.parseDouble(longitude);
		double latitudeDouble = Double.parseDouble(latitude);
		double radiusDouble = Double.parseDouble(radius);
		return new ResponseEntity<ArrayList<Park>>(dao.getParkByLocation(longitudeDouble, latitudeDouble, radiusDouble), HttpStatus.OK);
	}

}
