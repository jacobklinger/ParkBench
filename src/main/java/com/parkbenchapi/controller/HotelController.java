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

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels")
	ResponseEntity<ArrayList<Hotel>> getHotels(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Hotel>>(dao.getHotels(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}")
	ResponseEntity<Hotel> getHotelById(@PathVariable String resortId, @PathVariable String hotelId) {
		return new ResponseEntity<Hotel>(dao.getHotelById(resortId, hotelId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/hotels/location")
	ResponseEntity<ArrayList<Hotel>> getHotelsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		return new ResponseEntity<ArrayList<Hotel>>(dao.getHotelsByLocation(longitude, latitude, radius), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/hotels")
	ResponseEntity<ArrayList<Hotel>> getAllParks(@RequestParam(value = "name", required = false)  String hotelName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (hotelName != null || resortName != null) {
			return new ResponseEntity<ArrayList<Hotel>>(dao.getAllHotels(hotelName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Hotel>>(dao.getAllHotels(), HttpStatus.OK);
		}
	}

}
