package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Park;

public class ParksResponse {
	
	private List<Park> parks;
	
	public ParksResponse() {
		
	}

	public ParksResponse(List<Park> parks) {
		this.parks = parks;
	}

	public List<Park> getParks() {
		return parks;
	}

	public void setParks(List<Park> parks) {
		this.parks = parks;
	}

}
