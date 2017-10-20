package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
@RequestMapping("/resorts")
public class ResortController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<ArrayList<Resort>> getAllResorts() {
		return new ResponseEntity<ArrayList<Resort>>(dao.getAllResorts(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<Resort> getResortByName(@PathVariable String id) {
		return new ResponseEntity<Resort>(dao.getResortById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/location")
	ResponseEntity<ArrayList<Resort>> getResortByLocation(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("radius") String radius) {
		double longitudeDouble = Double.parseDouble(longitude);
		double latitudeDouble = Double.parseDouble(latitude);
		double radiusDouble = Double.parseDouble(radius);
		return new ResponseEntity<ArrayList<Resort>>(dao.getResortsByLocation(longitudeDouble, latitudeDouble, radiusDouble), HttpStatus.OK);
	}

}
