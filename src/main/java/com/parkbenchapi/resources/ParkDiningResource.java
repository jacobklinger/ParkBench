package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.ParkDining;
import com.parkbenchapi.services.ParkDiningService;

@Path("/resorts/{resortID}/parks/{parkID}/dining")
@Produces(MediaType.APPLICATION_JSON)
public class ParkDiningResource {
	
	ParkDiningService service = new ParkDiningService();

    @GET
    public List<ParkDining> getAllParkDining(@PathParam("resortID") String resortID, @PathParam("parkID") String parkID) {
    	return service.getAllDining(resortID, parkID);
    }
    
    @GET
    @Path("/{diningID}")
    public ParkDining getParkDining(@PathParam("resortID") String resortID, @PathParam("parkID") String parkID, @PathParam("diningID") String diningID) {
    	return service.getDining(resortID, parkID, diningID);
    }
}
