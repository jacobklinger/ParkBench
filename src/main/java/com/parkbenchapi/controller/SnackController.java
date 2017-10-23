package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class SnackController {

	@Autowired
	DataAccessObjectImpl dao;
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/snacks")
	ResponseEntity<ArrayList<Snack>> getSnacks(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<Snack>>(dao.getSnacks(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/snacks/{snackId}")
	ResponseEntity<Snack> getSnackById(@PathVariable String resortId, @PathVariable String snackId) {
		return new ResponseEntity<Snack>(dao.getSnackById(resortId, snackId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/snacks")
	ResponseEntity<ArrayList<Snack>> getAllSnacks(@RequestParam(value = "name", required = false)  String snackName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (resortName != null || snackName != null) {
			return new ResponseEntity<ArrayList<Snack>>(dao.getAllSnacks(snackName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<Snack>>(dao.getAllSnacks(), HttpStatus.OK);
		}
	}

}
