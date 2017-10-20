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

	@RequestMapping(method = RequestMethod.GET, value = "/attractions")
	ResponseEntity<ArrayList<Attraction>> getAllAttractions() {
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAllAttractions(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/attractions/{id}")
	ResponseEntity<LocatableResource> getParkByName(@PathVariable String id) {
		return new ResponseEntity<LocatableResource>(dao.getAttractionById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/parks/{parkId}/attractions")
	ResponseEntity<ArrayList<Attraction>> getAttractionsByParkId(@PathVariable String parkId) {
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAttractionsByPark(parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/parks/{parkId}/attractions/{attractionId}")
	ResponseEntity<Attraction> getAttractionsByParkAndAttractionId(@PathVariable String parkId, @PathVariable String attractionId) {
		return new ResponseEntity<Attraction>(dao.getAttractionParkAndById(parkId, attractionId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/resorts/{resortId}/attractions")
	ResponseEntity<ArrayList<Attraction>> getAttractionsByResortId(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAttractionsByResort(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/resorts/{resortId}/attractions/{resortId}")
	ResponseEntity<Attraction> getAttractionsByResortAndAttractionId(@PathVariable String resortId, String attractionId) {
		return new ResponseEntity<Attraction>(dao.getAttractionResortAndById(resortId, attractionId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/attractions/location")
	ResponseEntity<ArrayList<Attraction>> getParkByLocation(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("radius") String radius) {
		double longitudeDouble = Double.parseDouble(longitude);
		double latitudeDouble = Double.parseDouble(latitude);
		double radiusDouble = Double.parseDouble(radius);
		return new ResponseEntity<ArrayList<Attraction>>(dao.getAttractionsByLocation(longitudeDouble, latitudeDouble, radiusDouble), HttpStatus.OK);
	}

}