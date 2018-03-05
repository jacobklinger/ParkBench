package com.parkbenchapi.model.response;

import java.util.List;
import com.parkbenchapi.model.Hotel;

public class HotelResponse {
	
	private List<Hotel> hotels;
	
	public HotelResponse() {
		
	}

	public HotelResponse(List<Hotel> hotels) {
		this.hotels = hotels;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

}
