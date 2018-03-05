package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.HotelDining;
import com.parkbenchapi.model.LocatableResource;
import com.parkbenchapi.model.ParkDining;
import com.parkbenchapi.rowmappers.DiningRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class DiningDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String PARK_DINING_COLUMNS = "SELECT park_dining.diningId, park_dining.name, park_dining.short_name, park_dining.land, park_dining.longitude, park_dining.latitude, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";
	private final String HOTEL_DINING_COLUMNS = "SELECT hotel_dining.diningId, hotel_dining.name, hotel_dining.short_name, hotel_dining.longitude, hotel_dining.latitude, hotel.hotelId as hotelId, hotel.name as hotel_name, resort.resortId as resortId, resort.name as resort_name";
	
	public List<LocatableResource> getParkDining(String resortId, String parkId) {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND park_dining.parkId = ?";
		List<LocatableResource> parkDining = jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new DiningRowMapper());
		return parkDining;
	}

	
	public ParkDining getParkDiningById(String resortId, String parkId, String diningId) {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND park_dining.parkId = ? AND park_dining.diningId = ?";
		ParkDining parkDining = (ParkDining) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, diningId}, new DiningRowMapper());
		return parkDining;
	}
	
	public List<LocatableResource> getHotelDining(String resortId, String hotelId) {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND hotel_dining.hotelId = ?";
		List<LocatableResource> hotelDining = jdbcTemplate.query(sql, new Object[] {resortId, hotelId}, new DiningRowMapper());
		return hotelDining;
	}

	
	public HotelDining getHotelDiningById(String resortId, String hotelId, String diningId) {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND hotel_dining.hotelId = ? AND hotel_dining.diningId = ?";
		HotelDining hotelDining = (HotelDining) jdbcTemplate.queryForObject(sql, new Object[] {resortId, hotelId, diningId}, new DiningRowMapper());
		return hotelDining;
	}
	
	public List<LocatableResource> getAllDining() {
		List<LocatableResource> allDining = new ArrayList<LocatableResource>();

		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)";

		List<LocatableResource> hotelDining = jdbcTemplate.query(sql, new DiningRowMapper());	

		sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		List<LocatableResource> parkDining = jdbcTemplate.query(sql, new DiningRowMapper());

		allDining.addAll(parkDining);
		allDining.addAll(hotelDining);

		return allDining;
	}

	public List<LocatableResource> getDiningByLocation(double longitude, double latitude,double radius) {

		List<LocatableResource> filteredDining = new ArrayList<LocatableResource>();
		List<Double> distances = new ArrayList<Double>();

		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)";

		List<LocatableResource> hotelDining = jdbcTemplate.query(sql, new DiningRowMapper());	

		for (LocatableResource dining: hotelDining) {
			double distance = geoUtil.getDistance(longitude, latitude, dining.getLongitude(), dining.getLatitude());
			if (distance <= radius) {
				filteredDining.add(dining);
				distances.add(distance);
			}
		}

		sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		List<LocatableResource> parkDining = jdbcTemplate.query(sql, new DiningRowMapper());

		for (LocatableResource dining: parkDining) {
			double distance = geoUtil.getDistance(longitude, latitude, dining.getLongitude(), dining.getLatitude());
			if (distance <= radius) {
				filteredDining.add(dining);
			}
		}

		return filteredDining;	
	}

	
	public List<LocatableResource> getAllDining(String diningName, String resortName, String hotelName, String parkName) {
		
		if (diningName == null) {
			diningName = "%";
		}
		else {
			diningName = "%" + diningName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}

		List<LocatableResource> allDining = new ArrayList<LocatableResource>();

		jdbcTemplate.execute("use parkbench");
		
		if (hotelName != null && parkName == null) {
			hotelName = "%" + hotelName + "%";
			String sql = HOTEL_DINING_COLUMNS
					+ " FROM ((hotel_dining"
					+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
					+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
					+ " WHERE hotel_dining.name LIKE ? AND hotel.name LIKE ? AND resort.name LIKE ?";

			List<LocatableResource> hotelDining = jdbcTemplate.query(sql, new Object[] {diningName, hotelName, resortName}, new DiningRowMapper());	
			allDining.addAll(hotelDining);
		}
		else if (parkName != null && hotelName == null) {
			parkName = "%" + parkName + "%";
			String sql = PARK_DINING_COLUMNS
					+ " FROM ((park_dining"
					+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
					+ " INNER JOIN resort ON park.resortId = resort.resortId)"
					+ " WHERE park_dining.name LIKE ? AND park.name LIKE ? AND resort.name LIKE ?";
			
			List<LocatableResource> parkDining = jdbcTemplate.query(sql, new Object[] {diningName, parkName, resortName}, new DiningRowMapper());
			allDining.addAll(parkDining);
		}
		else {
			parkName = "%" + parkName + "%";
			String sql = PARK_DINING_COLUMNS
					+ " FROM ((park_dining"
					+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
					+ " INNER JOIN resort ON park.resortId = resort.resortId)"
					+ " WHERE park_dining.name LIKE ? AND resort.name LIKE ?";
			
			List<LocatableResource> parkDining = jdbcTemplate.query(sql, new Object[] {diningName, resortName}, new DiningRowMapper());
			allDining.addAll(parkDining);
			
			hotelName = "%" + hotelName + "%";
			sql = HOTEL_DINING_COLUMNS
					+ " FROM ((hotel_dining"
					+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
					+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
					+ " WHERE hotel_dining.name LIKE ? AND resort.name LIKE ?";

			List<LocatableResource> hotelDining = jdbcTemplate.query(sql, new Object[] {diningName, resortName}, new DiningRowMapper());	
			allDining.addAll(hotelDining);
		}

		return allDining;
	}

}
