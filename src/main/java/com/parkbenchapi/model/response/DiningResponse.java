package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.LocatableResource;

public class DiningResponse {
	
	private List<LocatableResource> dining;
	
	public DiningResponse() {
		
	}

	public DiningResponse(List<LocatableResource> dining) {
		this.dining = dining;
	}

	public List<LocatableResource> getDining() {
		return dining;
	}

	public void setDining(List<LocatableResource> dining) {
		this.dining = dining;
	}

}
