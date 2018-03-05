package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.rowmappers.HotelRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class HotelDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String HOTEL_COLUMNS = "SELECT hotel.hotelId, hotel.name, hotel.short_name, hotel.longitude, hotel.latitude, hotel.resort_area, resort.resortId as resortId, resort.name as resort_name";
	
	
	public List<Hotel> getHotels(String resortId) {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId"
				+ " WHERE hotel.resortId = ?";
		List<Hotel> hotels = jdbcTemplate.query(sql, new Object[] {resortId}, new HotelRowMapper());
		return hotels;
	}
	
	public Hotel getHotelById(String resortId, String hotelId) {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId"
				+ " WHERE hotel.resortId = ? AND hotel.hotelId = ?";
		Hotel hotel = (Hotel) jdbcTemplate.queryForObject(sql, new Object[] {resortId, hotelId}, new HotelRowMapper());
		return hotel;
	}
	
	public List<Hotel> getHotelsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId";

		List<Hotel> filteredHotels = new ArrayList<Hotel>();	
		List<Hotel> hotels = jdbcTemplate.query(sql, new HotelRowMapper());
		List<Double> distances = new ArrayList<Double>();

		for (Hotel hotel: hotels) {
			double distance = geoUtil.getDistance(longitude, latitude, hotel.getLongitude(), hotel.getLatitude());
			if (distance <= radius) {
				filteredHotels.add(hotel);
				distances.add(distance);
			}
		}

		return filteredHotels;	
	}

	public List<Hotel> getAllHotels() {
		jdbcTemplate.execute("use parkbench");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId";
		List<Hotel> hotels = jdbcTemplate.query(sql, new HotelRowMapper());
		return hotels;
	}

	public List<Hotel> getAllHotels(String hotelName, String resortName) {
		jdbcTemplate.execute("use parkbench");

		if (hotelName == null) {
			hotelName = "%";
		}
		else {
			hotelName = "%" + hotelName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}

		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId"
				+ " WHERE hotel.name LIKE ? AND resort.name LIKE ?";
		List<Hotel> hotels = jdbcTemplate.query(sql, new Object[] {hotelName, resortName}, new HotelRowMapper());
		return hotels;
	}

}
