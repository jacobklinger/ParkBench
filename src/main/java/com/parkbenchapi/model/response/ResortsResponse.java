package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Resort;

public class ResortsResponse {
	
	private List<Resort> resorts;
	
	public ResortsResponse() {
		
	}

	public ResortsResponse(List<Resort> resorts) {
		this.resorts = resorts;
	}

	public List<Resort> getResorts() {
		return resorts;
	}

	public void setResorts(List<Resort> resorts) {
		this.resorts = resorts;
	}

}
