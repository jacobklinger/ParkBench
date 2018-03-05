package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Entertainment;

public class EntertainmentResponse {
	
	private List<Entertainment> entertainment;
	
	public EntertainmentResponse() {
		
	}

	public EntertainmentResponse(List<Entertainment> entertainment) {
		this.entertainment = entertainment;
	}

	public List<Entertainment> getEntertainments() {
		return entertainment;
	}

	public void setEntertainments(List<Entertainment> entertainment) {
		this.entertainment = entertainment;
	}

}
