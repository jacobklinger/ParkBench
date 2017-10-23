package com.parkbenchapi.model;

import java.util.ArrayList;

public class CharacterMeet {

	private ArrayList<Link> links = new ArrayList<Link>();
	private String characterName;
	private NameAndURL resort;
	
	public String getCharacterName() {
		return characterName;
	}
	
	public NameAndURL getResort() {
		return resort;
	}

	public void setResort(String resortName, String resortId) {
		this.resort = new NameAndURL(resortName, "/resorts/" + resortId);
	}
	
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	public ArrayList<Link> getLinks() {
		return links;
	}
	
	protected void addLink(Link link) {
		links.add(link);
	}
	
	public void generateLinks(String resortId, String characterId) {
		addLink(new Link("self", "/resorts/" + resortId + "/characters/" + characterId));
	}
	
}
