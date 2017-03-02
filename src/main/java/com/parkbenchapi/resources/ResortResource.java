package com.parkbenchapi.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.parkbenchapi.model.Resort;
import com.parkbenchapi.services.ResortsService;

@Path("resorts")
@Produces(MediaType.APPLICATION_JSON)
public class ResortResource {
	
	ResortsService service = new ResortsService();

    @GET
    public List<Resort> getResorts() {
    	return service.getResorts();
    }
    
    @GET
    @Path("/{resortID}")
    public Resort getResort(@PathParam("resortID") String resortID) {
    	return service.getResort(resortID);
    }
}
