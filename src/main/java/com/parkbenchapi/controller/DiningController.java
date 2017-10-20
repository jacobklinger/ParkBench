package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class DiningController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET, value = "/dining")
	ResponseEntity<ArrayList<Dining>> getAllAttractions() {
		return new ResponseEntity<ArrayList<Dining>>(dao.getAllDining(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dining/{id}")
	ResponseEntity<Dining> getParkByName(@PathVariable String id) {
		return new ResponseEntity<Dining>(dao.getDiningById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/parks/{parkId}/dining")
	ResponseEntity<ArrayList<Dining>> getDiningByParkId(@PathVariable String parkId) {
		return new ResponseEntity<ArrayList<Dining>>(dao.getDiningByPark(parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/hotels/{hotelId}/dining")
	ResponseEntity<ArrayList<Dining>> getDiningByHotelId(@PathVariable String hotelId) {
		return new ResponseEntity<ArrayList<Dining>>(dao.getDiningByPark(hotelId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dining/location")
	ResponseEntity<ArrayList<Dining>> getParkByLocation(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("radius") String radius) {
		double longitudeDouble = Double.parseDouble(longitude);
		double latitudeDouble = Double.parseDouble(latitude);
		double radiusDouble = Double.parseDouble(radius);
		return new ResponseEntity<ArrayList<Dining>>(dao.getDiningByLocation(longitudeDouble, latitudeDouble, radiusDouble), HttpStatus.OK);
	}

}