package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DiningDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.DiningResponse;

@RestController
public class DiningController {

	@Autowired
	DiningDao dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/dining")
	ResponseEntity<DiningResponse> getParkDining(@PathVariable String resortId, @PathVariable String parkId) {
		DiningResponse dining = new DiningResponse();
		dining.setDining(dao.getParkDining(resortId, parkId));
		return new ResponseEntity<DiningResponse>(dining, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/parks/{parkId}/dining/{diningId}")
	ResponseEntity<ParkDining> getParkDiningById(@PathVariable String resortId, @PathVariable String parkId, @PathVariable String diningId) {
		return new ResponseEntity<ParkDining>(dao.getParkDiningById(resortId, parkId, diningId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}/dining")
	ResponseEntity<DiningResponse> getHotelDining(@PathVariable String resortId, @PathVariable String hotelId) {
		DiningResponse dining = new DiningResponse(dao.getHotelDining(resortId, hotelId));
		return new ResponseEntity<DiningResponse>(dining, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}/dining/{diningId}")
	ResponseEntity<HotelDining> getHotelDiningById(@PathVariable String resortId, @PathVariable String hotelId, @PathVariable String diningId) {
		return new ResponseEntity<HotelDining>(dao.getHotelDiningById(resortId, hotelId, diningId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/dining/location")
	ResponseEntity<DiningResponse> getDiningByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		DiningResponse dining = new DiningResponse(dao.getDiningByLocation(longitude, latitude, radius));
		return new ResponseEntity<DiningResponse>(dining, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/dining")
	ResponseEntity<DiningResponse> getAllDining(@RequestParam(value = "name", required = false)  String diningName, @RequestParam(value = "park", required = false)  String parkName, @RequestParam(value = "hotel", required = false)  String hotelName, @RequestParam(value = "resort", required = false)  String resortName) {
		DiningResponse dining = new DiningResponse();
		if (diningName != null || resortName != null || hotelName != null || parkName != null) {
			dining.setDining(dao.getAllDining(diningName, resortName, hotelName, parkName));
			return new ResponseEntity<DiningResponse>(dining, HttpStatus.OK);
		}
		else {
			dining.setDining(dao.getAllDining());
			return new ResponseEntity<DiningResponse>(dining, HttpStatus.OK);
		}
	}

}