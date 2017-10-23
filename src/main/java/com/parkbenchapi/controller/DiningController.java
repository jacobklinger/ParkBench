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

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/dining")
	ResponseEntity<ArrayList<ParkDining>> getParkDining(@PathVariable String resortId, @PathVariable String parkId) {
		return new ResponseEntity<ArrayList<ParkDining>>(dao.getParkDining(resortId, parkId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/dining/{diningId}")
	ResponseEntity<ParkDining> getParkDiningById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String diningId) {
		return new ResponseEntity<ParkDining>(dao.getParkDiningById(resortId, parkId, diningId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}/dining")
	ResponseEntity<ArrayList<HotelDining>> getHotelDining(@PathVariable String resortId, @PathVariable String hotelId) {
		return new ResponseEntity<ArrayList<HotelDining>>(dao.getHotelDining(resortId, hotelId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}/dining/{diningId}")
	ResponseEntity<HotelDining> getHotelDiningById(@PathVariable String resortId, @PathVariable String hotelId, @PathVariable String diningId) {
		return new ResponseEntity<HotelDining>(dao.getHotelDiningById(resortId, hotelId, diningId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/dining/location")
	ResponseEntity<ArrayList<LocatableResource>> getDiningByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		return new ResponseEntity<ArrayList<LocatableResource>>(dao.getDiningByLocation(longitude, latitude, radius), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/dining")
	ResponseEntity<ArrayList<LocatableResource>> getAllDining(@RequestParam(value = "name", required = false)  String diningName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "hotel", required = false)  String hotelName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (diningName != null || resortName != null || hotelName != null || parkName != null) {
			return new ResponseEntity<ArrayList<LocatableResource>>(dao.getAllDining(diningName, resortName, hotelName, parkName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<LocatableResource>>(dao.getAllDining(), HttpStatus.OK);
		}
	}

}