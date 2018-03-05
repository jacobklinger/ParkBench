package com.parkbenchapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parkbenchapi.dao.CharacterDao;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.response.CharacterResponse;

@RestController
public class CharacterController {

	@Autowired
	CharacterDao dao;

	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/characters")
	ResponseEntity<CharacterResponse> getCharacters(@PathVariable String resortId) {
		CharacterResponse characters = new CharacterResponse(dao.getCharacters(resortId));
		return new ResponseEntity<CharacterResponse>(characters, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/resorts/{resortId}/characters/{characterId}")
	ResponseEntity<CharacterMeet> getCharacterById(@PathVariable String resortId, @PathVariable String characterId) {
		return new ResponseEntity<CharacterMeet>(dao.getCharacterById(resortId, characterId), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/characters")
	ResponseEntity<CharacterResponse> getAllCharacters(@RequestParam(value = "name", required = false)  String characterName, @RequestParam(value = "resort", required = false)  String resortName) {
		CharacterResponse characters = new CharacterResponse();
		if (resortName != null || characterName != null) {
			characters.setCharacters(dao.getAllCharacters(characterName, resortName));
			return new ResponseEntity<CharacterResponse>(characters, HttpStatus.OK);
		}
		else {
			characters.setCharacters(dao.getAllCharacters());
			return new ResponseEntity<CharacterResponse>(characters, HttpStatus.OK);
		}
	}

}
