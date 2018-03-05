package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.CharacterMeet;

public class CharacterResponse {
	
	private List<CharacterMeet> characters;
	
	public CharacterResponse() {
		
	}

	public CharacterResponse(List<CharacterMeet> attractions) {
		this.characters = attractions;
	}

	public List<CharacterMeet> getCharacters() {
		return characters;
	}

	public void setCharacters(List<CharacterMeet> attractions) {
		this.characters = attractions;
	}

}
