package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.services.AttractionService;

@Path("/resorts/{resortID}/parks/{parkID}/attractions")
@Produces(MediaType.APPLICATION_JSON)
public class AttractionResource {
	
	AttractionService service = new AttractionService();

    @GET
    public List<Attraction> getAttractions(@PathParam("resortID") String resortID, @PathParam("parkID") String parkID) {
    	return service.getAttractions(resortID, parkID);
    }
    
    @GET
    @Path("/{attractionID}")
    public Attraction getAttraction(@PathParam("resortID") String resortID, @PathParam("parkID") String parkID, @PathParam("attractionID") String attractionID) {
    	return service.getAttraction(resortID, parkID, attractionID);
    }
}
