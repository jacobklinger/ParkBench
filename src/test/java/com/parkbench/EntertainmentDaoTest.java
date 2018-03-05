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
import com.parkbenchapi.dao.EntertainmentDao;
import com.parkbenchapi.model.Entertainment;
import com.parkbenchapi.rowmappers.EntertainmentRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class EntertainmentDaoTest {
	
	@InjectMocks
	private EntertainmentDao dao = new EntertainmentDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<Entertainment> entertainmentList;
	private Entertainment entertainment;
	
	private String entertainmentName = "Captain Jack Sparrow’s Pirate Tutorial";
	private String land = "Adventureland";
	private String parkName = "Magic Kingdom";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "magic-kingdom";
	private String entertainmentId = "captain-jack-sparrows-pirate-tutorial";
	
	@Before
	public void setup() throws Exception {
		entertainmentList = new ArrayList<Entertainment>();
		entertainment = new Entertainment();
		entertainment.setName(entertainmentName);
		entertainment.setShortName(entertainmentName);
		entertainment.setLand(land);
		entertainment.setResort(resortName, resortId);
		entertainment.generateLinks(resortId, parkId, entertainmentId);
		entertainmentList.add(entertainment);
		entertainment.setPark(parkName, resortId, parkId);
	}
	
	@Test
	public void getEntertainmentTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(EntertainmentRowMapper.class))).thenReturn(entertainmentList);
		List<Entertainment> actualEntertainment = dao.getEntertainment(resortId, parkId);
		Assert.assertEquals(entertainmentList, actualEntertainment);
	}
	
	@Test
	public void getEntertainmentByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(EntertainmentRowMapper.class))).thenReturn(entertainment);
		Entertainment actualEntertainment = dao.getEntertainmentById(resortId, parkId, entertainmentId);
		Assert.assertEquals(entertainment, actualEntertainment);
	}
	
	@Test
	public void getAllEntertainmentTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(EntertainmentRowMapper.class))).thenReturn(entertainmentList);
		List<Entertainment> actualEntertainment = dao.getAllEntertainment();
		Assert.assertEquals(entertainmentList, actualEntertainment);
	}
	
	@Test
	public void getAllEntertainmentWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(EntertainmentRowMapper.class))).thenReturn(entertainmentList);
		List<Entertainment> actualEntertainment = dao.getAllEntertainment(parkName, resortName, entertainmentName);
		Assert.assertEquals(entertainmentList, actualEntertainment);
	}
	
	@Test
	public void getAllEntertainmentTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(EntertainmentRowMapper.class))).thenReturn(entertainmentList);
		List<Entertainment> actualEntertainment = dao.getAllEntertainment(null, null, null);
		Assert.assertEquals(entertainmentList, actualEntertainment);
	}

}
