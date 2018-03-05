package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.HotelDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.HotelResponse;

@RestController
public class HotelController {

	@Autowired
	HotelDao dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels")
	ResponseEntity<HotelResponse> getHotels(@PathVariable String resortId) {
		HotelResponse hotels = new HotelResponse(dao.getHotels(resortId));
		return new ResponseEntity<HotelResponse>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/hotels/{hotelId}")
	ResponseEntity<Hotel> getHotelById(@PathVariable String resortId, @PathVariable String hotelId) {
		return new ResponseEntity<Hotel>(dao.getHotelById(resortId, hotelId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/hotels/location")
	ResponseEntity<HotelResponse> getHotelsByLocation(@RequestParam double longitude, @RequestParam double latitude, @RequestParam double radius) {
		HotelResponse hotels = new HotelResponse(dao.getHotelsByLocation(longitude, latitude, radius));
		return new ResponseEntity<HotelResponse>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/hotels")
	ResponseEntity<HotelResponse> getAllHotels(@RequestParam(value = "name", required = false)  String hotelName, @RequestParam(value = "resort", required = false)  String resortName) {
		HotelResponse hotels = new HotelResponse();
		if (hotelName != null || resortName != null) {
			hotels.setHotels(dao.getAllHotels(hotelName, resortName));
			return new ResponseEntity<HotelResponse>(hotels, HttpStatus.OK);
		}
		else {
			hotels.setHotels(dao.getAllHotels());
			return new ResponseEntity<HotelResponse>(hotels, HttpStatus.OK);
		}
	}

}
