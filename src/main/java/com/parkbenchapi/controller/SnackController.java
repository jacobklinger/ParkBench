package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
@RequestMapping("/snacks")
public class SnackController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<ArrayList<Snack>> getAllCharacters() {
		return new ResponseEntity<ArrayList<Snack>>(dao.getAllSnacks(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<Snack> getParkByName(@PathVariable String id) {
		return new ResponseEntity<Snack>(dao.getSnackById(id), HttpStatus.OK);
	}

}
