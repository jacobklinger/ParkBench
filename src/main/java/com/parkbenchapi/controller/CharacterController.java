package com.parkbenchapi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.DataAccessObjectImpl;
import com.parkbenchapi.model.*;

@RestController
public class CharacterController {

	@Autowired
	DataAccessObjectImpl dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/characters")
	ResponseEntity<ArrayList<CharacterMeet>> getCharacters(@PathVariable String resortId) {
		return new ResponseEntity<ArrayList<CharacterMeet>>(dao.getCharacters(resortId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/characters/{characterId}")
	ResponseEntity<CharacterMeet> getCharacterById(@PathVariable String resortId, @PathVariable String characterId) {
		return new ResponseEntity<CharacterMeet>(dao.getCharacterById(resortId, characterId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/characters")
	ResponseEntity<ArrayList<CharacterMeet>> getAllCharacters(@RequestParam(value = "name", required = false)  String characterName, @RequestParam(value = "resort", required = false)  String resortName) {
		if (resortName != null || characterName != null) {
			return new ResponseEntity<ArrayList<CharacterMeet>>(dao.getAllCharacters(characterName, resortName), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<ArrayList<CharacterMeet>>(dao.getAllCharacters(), HttpStatus.OK);
		}
	}

}
