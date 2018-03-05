package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Attraction;

public class AttractionResponse {
	
	private List<Attraction> attractions;
	
	public AttractionResponse() {
		
	}

	public AttractionResponse(List<Attraction> attractions) {
		this.attractions = attractions;
	}

	public List<Attraction> getAttractions() {
		return attractions;
	}

	public void setAttractions(List<Attraction> attractions) {
		this.attractions = attractions;
	}

}
