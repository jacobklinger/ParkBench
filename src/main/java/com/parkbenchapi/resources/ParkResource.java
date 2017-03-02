package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.Park;
import com.parkbenchapi.services.ParkService;

@Path("/resorts/{resortID}/parks")
@Produces(MediaType.APPLICATION_JSON)
public class ParkResource {
	
	ParkService service = new ParkService();

    @GET
    public List<Park> getParks(@PathParam("resortID") String resortID) {
    	return service.getParks(resortID);
    }
    
    @GET
    @Path("/{parkID}")
    public Park getPark(@PathParam("resortID") String resortID, @PathParam("parkID") String parkID) {
    	return service.getPark(resortID, parkID);
    }
}
