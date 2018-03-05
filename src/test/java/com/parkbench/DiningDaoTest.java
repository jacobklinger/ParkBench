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
import com.parkbenchapi.dao.DiningDao;
import com.parkbenchapi.model.HotelDining;
import com.parkbenchapi.model.LocatableResource;
import com.parkbenchapi.model.ParkDining;
import com.parkbenchapi.rowmappers.DiningRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class DiningDaoTest {
	
	@InjectMocks
	private DiningDao dao = new DiningDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<LocatableResource> parkDiningList;
	private ParkDining parkDining;
	
	private ArrayList<LocatableResource> hotelDiningList;
	private HotelDining hotelDining;
	
	private ArrayList<LocatableResource> allDiningList;
	
	private String parkDiningName = "50's Prime Time Cafe";
	private String land = "Echo Lake";
	private String parkName = "Disney’s Hollywood Studios";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String parkId = "hollywood-studios";
	private String parkDiningId = "50s-prime-time-cafe";
	private double parkDiningLatitude = 28.357405;
	private double parkDiningLongitude = -81.558897;
	private double radius = 10000;
	
	private String hotelDiningName = "Chef Mickey’s";
	private String hotelDiningId = "chef-mickeys";
	private String hotelName = "Disney's Contemporary Resort ";
	private String hotelId = "contemporary";
	private double hotelDiningLatitude = 28.414385;
	private double hotelDiningLongitude = -81.574385;
	
	
	
	@Before
	public void setup() throws Exception {
		parkDiningList = new ArrayList<LocatableResource>();
		parkDining = new ParkDining();
		parkDining.setName(parkDiningName);
		parkDining.setShortName(parkDiningName);
		parkDining.setLand(land);
		parkDining.setLatitude(parkDiningLatitude);
		parkDining.setLongitude(parkDiningLongitude);
		parkDining.setResort(resortName, resortId);
		parkDining.generateLinks(resortId, parkId, parkDiningId);
		parkDiningList.add(parkDining);
		parkDining.setPark(parkName, resortId, parkId);
		
		hotelDiningList = new ArrayList<LocatableResource>();
		hotelDining = new HotelDining();
		hotelDining.setName(hotelDiningName);
		hotelDining.setShortName(hotelDiningName);
		hotelDining.setLatitude(hotelDiningLatitude);
		hotelDining.setLongitude(hotelDiningLongitude);
		hotelDining.setResort(resortName, resortId);
		hotelDining.generateLinks(resortId, hotelId, hotelDiningId);
		hotelDiningList.add(hotelDining);
		hotelDining.setHotel(hotelName, resortId, hotelId);
		
		allDiningList = new ArrayList<LocatableResource>();
		allDiningList.add(parkDining);
		allDiningList.add(hotelDining);
	}
	
	@Test
	public void getParkDiningTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class))).thenReturn(parkDiningList);
		List<LocatableResource> actualDining = dao.getParkDining(resortId, parkId);
		Assert.assertEquals(parkDiningList, actualDining);
	}
	
	@Test
	public void getParkDiningByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class))).thenReturn(parkDining);
		ParkDining actualDining = dao.getParkDiningById(resortId, parkId, parkDiningId);
		Assert.assertEquals(parkDining, actualDining);
	}
	
	@Test
	public void getHotelDiningTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class))).thenReturn(hotelDiningList);
		List<LocatableResource> actualDining = dao.getHotelDining(resortId, hotelId);
		Assert.assertEquals(hotelDiningList, actualDining);
	}
	
	@Test
	public void getHotelDiningByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class))).thenReturn(hotelDining);
		HotelDining actualDining = dao.getHotelDiningById(resortId, hotelId, hotelDiningId);
		Assert.assertEquals(hotelDining, actualDining);
	}
	
	@Test
	public void getParkDiningByLocationTest() {
		Mockito
			.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(DiningRowMapper.class)))
			.thenReturn(parkDiningList)
			.thenReturn(hotelDiningList);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius - 1);
		List<LocatableResource> actualDining = dao.getDiningByLocation(parkDiningLongitude, parkDiningLatitude, radius);
		Assert.assertEquals(allDiningList, actualDining);
	}
	
	@Test
	public void getParkDiningByLocationExpectEmptyTest() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(DiningRowMapper.class)))
		.thenReturn(parkDiningList)
		.thenReturn(hotelDiningList);
		Mockito.when(geoUtil.getDistance(Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble(), Matchers.anyDouble())).thenReturn(radius + 1);
		List<LocatableResource> actualDining = dao.getDiningByLocation(parkDiningLongitude, parkDiningLatitude, radius);
		Assert.assertTrue(actualDining.isEmpty());
	}
	
	@Test
	public void getAllDiningTest() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(DiningRowMapper.class)))
		.thenReturn(parkDiningList)
		.thenReturn(new ArrayList<LocatableResource>());
		List<LocatableResource> actualDining = dao.getAllDining();
		Assert.assertEquals(parkDiningList, actualDining);
	}
	
	@Test
	public void getAllDiningWithParamsTest() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class)))
		.thenReturn(parkDiningList)
		.thenReturn(new ArrayList<LocatableResource>());
		List<LocatableResource> actualDining = dao.getAllDining(parkName, resortName, parkDiningName, hotelDiningName);
		Assert.assertEquals(parkDiningList, actualDining);
	}
	
	@Test
	public void getAllDiningWithNullHotelTest() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class)))
		.thenReturn(parkDiningList);
		List<LocatableResource> actualDining = dao.getAllDining(parkName, resortName, parkDiningName, null);
		Assert.assertEquals(parkDiningList, actualDining);
	}
	
	@Test
	public void getAllDiningWithNullParkTest() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class)))
		.thenReturn(hotelDiningList);
		List<LocatableResource> actualDining = dao.getAllDining(parkName, resortName, null, hotelDiningName);
		Assert.assertEquals(hotelDiningList, actualDining);
	}
	
	@Test
	public void getAllDiningTestWithNullParams() {
		Mockito
		.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(DiningRowMapper.class)))
		.thenReturn(parkDiningList)
		.thenReturn(hotelDiningList);
		List<LocatableResource> actualDining = dao.getAllDining(null, null, null, null);
		Assert.assertEquals(allDiningList, actualDining);
	}

}
