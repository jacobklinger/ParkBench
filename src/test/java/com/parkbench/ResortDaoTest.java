package com.parkbench;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.parkbenchapi.dao.ParkDao;
import com.parkbenchapi.dao.ResortDao;
import com.parkbenchapi.model.Park;
import com.parkbenchapi.model.Resort;
import com.parkbenchapi.rowmappers.ParkRowMapper;
import com.parkbenchapi.rowmappers.ResortRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class ResortDaoTest {
	
	@InjectMocks
	private ResortDao dao = new ResortDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private List<Resort> resorts;
	private Resort resort;
	
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private double latitude = 28.393364; 
	private double longitude = -81.573823;
	private double radius = 10000;
	
	@Before
	public void setup() throws Exception {
		resorts = new ArrayList<Resort>();
		resort = new Resort();
		resort.setLatitude(latitude);
		resort.setLongitude(longitude);
		resort.generateLinks(resortId);
		resorts.add(resort);
		Mockito.doNothing().when(jdbcTemplate).execute(Matchers.anyString());
	}
	
	@Test
	public void getResortsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(ResortRowMapper.class))).thenReturn(resorts);
		List<Resort> actualResorts = dao.getAllResorts();
		Assert.assertEquals(resorts, actualResorts);
	}
	
	@Test
	public void getResortByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ResortRowMapper.class))).thenReturn(resort);
		Resort actualResort = dao.getResortById(resortId);
		Assert.assertEquals(resort, actualResort);
	}
	
	@Test
	public void getAllResortsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(ResortRowMapper.class))).thenReturn(resorts);
		List<Resort> actualResorts = dao.getAllResorts();
		Assert.assertEquals(resorts, actualResorts);
	}
	
	@Test
	public void getAllResortsWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ResortRowMapper.class))).thenReturn(resorts);
		List<Resort> actualResorts = dao.getAllResorts(resortName);
		Assert.assertEquals(resorts, actualResorts);
	}
	
	@Test
	public void getAllResortsTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(ResortRowMapper.class))).thenReturn(resorts);
		List<Resort> actualResorts = dao.getAllResorts(null);
		Assert.assertEquals(resorts, actualResorts);
	}

}
