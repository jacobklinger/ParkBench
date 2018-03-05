package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.SnackDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.SnackResponse;

@RestController
public class SnackController {

	@Autowired
	SnackDao dao;
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/snacks")
	ResponseEntity<SnackResponse> getSnacks(@PathVariable String resortId) {
		SnackResponse snacks = new SnackResponse(dao.getSnacks(resortId));
		return new ResponseEntity<SnackResponse>(snacks, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/snacks/{snackId}")
	ResponseEntity<Snack> getSnackById(@PathVariable String resortId, @PathVariable String snackId) {
		return new ResponseEntity<Snack>(dao.getSnackById(resortId, snackId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/snacks")
	ResponseEntity<SnackResponse> getAllSnacks(@RequestParam(value = "name", required = false)  String snackName, @RequestParam(value = "resort", required = false)  String resortName) {
		SnackResponse snacks = new SnackResponse();
		if (resortName != null || snackName != null) {
			snacks.setSnacks(dao.getAllSnacks(snackName, resortName));
			return new ResponseEntity<SnackResponse>(snacks, HttpStatus.OK);
		}
		else {
			snacks.setSnacks(dao.getAllSnacks());
			return new ResponseEntity<SnackResponse>(snacks, HttpStatus.OK);
		}
	}

}
