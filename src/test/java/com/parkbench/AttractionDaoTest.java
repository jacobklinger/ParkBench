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
import com.parkbenchapi.dao.AttractionDao;
import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.rowmappers.AttractionRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class AttractionDaoTest {
	
	@InjectMocks
	private AttractionDao dao = new AttractionDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<Attraction> attractions;
	private Attraction attraction;
	
	private String attractionName = "Astro Orbiter";
	private String land = "Tomorrowland";
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private String attractionId = "astro-orbiter";
	private double latitude = 28.417663;
	private double longitude = 81.581212;
	private double radius = 10000;
	
	@Before
	public void setup() throws Exception {
		attractions = new ArrayList<Attraction>();
		attraction = new Attraction();
		attraction.setName(attractionName);
		attraction.setShortName(attractionName);
		attraction.setLand(land);
		attraction.setLatitude(28.41852);
		attraction.setLongitude(-81.578985);
		attraction.setResort(resortName, resortId);
		attraction.generateLinks(resortId, parkId, attractionId);
		attractions.add(attraction);
		attraction.setPark(parkName, resortId, parkId);
	}
	
	@Test
	public void getAttractionsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		List<Attraction> actualAttractions = dao.getAttractions(resortId, parkId);
		Assert.assertEquals(attractions, actualAttractions);
	}
	
	@Test
	public void getAttractionByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(AttractionRowMapper.class))).thenReturn(attraction);
		Attraction actualAttraction = dao.getAttractionById(resortId, parkId, attractionId);
		Assert.assertEquals(attraction, actualAttraction);
	}
	
	@Test
	public void getAttractionByLocationTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius - 1);
		List<Attraction> actualAttractions = dao.getAttractionsByLocation(longitude, latitude, radius);
		Assert.assertEquals(attractions, actualAttractions);
	}
	
	@Test
	public void getAttractionByLocationExpectEmptyTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius + 1);
		List<Attraction> actualAttractions = dao.getAttractionsByLocation(longitude, latitude, radius);
		Assert.assertTrue(actualAttractions.isEmpty());
	}
	
	@Test
	public void getAllAttractionsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		List<Attraction> actualAttractions = dao.getAllAttractions();
		Assert.assertEquals(attractions, actualAttractions);
	}
	
	@Test
	public void getAllAttractionsWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		List<Attraction> actualAttractions = dao.getAllAttractions(parkName, resortName, attractionName);
		Assert.assertEquals(attractions, actualAttractions);
	}
	
	@Test
	public void getAllAttractionsTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(AttractionRowMapper.class))).thenReturn(attractions);
		List<Attraction> actualAttractions = dao.getAllAttractions(null, null, null);
		Assert.assertEquals(attractions, actualAttractions);
	}

}
