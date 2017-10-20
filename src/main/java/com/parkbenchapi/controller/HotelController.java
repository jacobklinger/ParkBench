package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class HotelController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET, value="/hotels")
	ResponseEntity<ArrayList<Hotel>> getAllHotels() {
		return new ResponseEntity<ArrayList<Hotel>>(dao.getAllHotels(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hotels/{id}")
	ResponseEntity<Hotel> getAllHotels(@PathVariable String id) {
		return new ResponseEntity<Hotel>(dao.getHotelById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "resorts/{resortId}/hotels/")
	ResponseEntity<ArrayList<Hotel>> getHotelByResortId(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Hotel>>(dao.getHotelsByResort(resortId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hotels/location")
	ResponseEntity<ArrayList<Hotel>> getHotelsLocation(@RequestParam("longitude") String longitude, @RequestParam("latitude") String latitude, @RequestParam("radius") String radius) {
		double longitudeDouble = Double.parseDouble(longitude);
		double latitudeDouble = Double.parseDouble(latitude);
		double radiusDouble = Double.parseDouble(radius);
		return new ResponseEntity<ArrayList<Hotel>>(dao.getHotelsByLocation(longitudeDouble, latitudeDouble, radiusDouble), HttpStatus.OK);
	}

}
