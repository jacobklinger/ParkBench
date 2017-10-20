package com.parkbenchapi.model;

public class Entertainment extends LocatableResource{

	@Override
	public void generateLinks(String id) {
		addLink(new Link("self", "/entertainment/" + id));
	}
	
}
