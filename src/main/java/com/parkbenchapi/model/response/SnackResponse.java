package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Snack;

public class SnackResponse {
	
	private List<Snack> snacks;
	
	public SnackResponse() {
		
	}

	public SnackResponse(List<Snack> snacks) {
		this.snacks = snacks;
	}

	public List<Snack> getSnacks() {
		return snacks;
	}

	public void setSnacks(List<Snack> snacks) {
		this.snacks = snacks;
	}

}
