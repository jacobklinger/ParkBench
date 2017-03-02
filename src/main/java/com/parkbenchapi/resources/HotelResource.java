package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.services.HotelService;

@Path("/resorts/{resortID}/hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelResource {
	
	HotelService service = new HotelService();

    @GET
    public List<Hotel> getHotels(@PathParam("resortID") String resortID) {
    	return service.getHotels(resortID);
    }
    
    @GET
    @Path("/{hotelID}")
    public Hotel getHotel(@PathParam("resortID") String resortID, @PathParam("hotelID") String hotelID) {
    	return service.getHotel(resortID, hotelID);
    }
}
