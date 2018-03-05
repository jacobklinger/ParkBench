package com.parkbenchapi.test;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class ParkBenchTest {
	
	@Autowired 
	protected MockMvc mockMvc;

	public String fileToString(String path) throws Exception {
		return new String(Files.readAllBytes(Paths.get(getClass().getResource(path).toURI())));
	}

}
