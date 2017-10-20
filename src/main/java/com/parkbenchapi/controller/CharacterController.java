package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
@RequestMapping("/characters")
public class CharacterController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<ArrayList<CharacterMeet>> getAllCharacters() {
		return new ResponseEntity<ArrayList<CharacterMeet>>(dao.getAllCharacters(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<CharacterMeet> getParkByName(@PathVariable String id) {
		return new ResponseEntity<CharacterMeet>(dao.getCharacterById(id), HttpStatus.OK);
	}

}
