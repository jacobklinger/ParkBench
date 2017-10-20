package com.parkbenchapi.model;

public class Resort extends LocatableResource{

	@Override
	public void generateLinks(String id) {
		addLink(new Link("self", "/resorts/" + id));
		addLink(new Link("parks", "/resorts/" + id + "/parks/"));
		addLink(new Link("hotels", "/resorts/" + id + "/hotels/"));
		addLink(new Link("dining", "/resorts/" + id + "/dining/"));
		addLink(new Link("attractions", "/resorts/" + id + "/attractions/"));
		addLink(new Link("entertainment", "/resorts/" + id + "/entertainment/"));
		addLink(new Link("characters", "/resorts/" + id + "/characters/"));
		addLink(new Link("snacks", "/resorts/" + id + "/snacks/"));
		addLink(new Link("location", "/resorts/location?longitude=" + getLongitude() + "&latitude=" + getLatitude() + "&radius=0"));
	}
	
}
