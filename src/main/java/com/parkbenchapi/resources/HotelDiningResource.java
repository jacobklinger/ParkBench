package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.HotelDining;
import com.parkbenchapi.services.HotelDiningService;

@Path("/resorts/{resortID}/hotels/{hotelID}/dining")
@Produces(MediaType.APPLICATION_JSON)
public class HotelDiningResource {
	
	HotelDiningService service = new HotelDiningService();

    @GET
    public List<HotelDining> getAllParkDining(@PathParam("resortID") String resortID, @PathParam("hotelID") String hotelID) {
    	return service.getAllDining(resortID, hotelID);
    }
    
    @GET
    @Path("/{diningID}")
    public HotelDining HotelDining(@PathParam("resortID") String resortID, @PathParam("hotelID") String hotelID, @PathParam("diningID") String diningID) {
    	return service.getDining(resortID, hotelID, diningID);
    }
}
