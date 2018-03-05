package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Park;
import com.parkbenchapi.rowmappers.ParkRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class ParkDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String PARK_COLUMNS = "SELECT park.parkId, park.name, park.short_name, park.longitude, park.latitude, resort.resortId as resortId, resort.name as resort_name";
	
	
	public List<Park> getParks(String resortId) {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId"
				+ " WHERE park.resortId = ?";
		List<Park> parks = jdbcTemplate.query(sql, new Object[] {resortId}, new ParkRowMapper());
		return parks;
	}
	
	public Park getParkById(String resortId, String parkId) {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId"
				+ " WHERE park.resortId = ? AND park.parkId = ?";
		Park park = (Park) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId}, new ParkRowMapper());
		return park;
	}
	
	public List<Park> getParkByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId";

		List<Park> filteredParks = new ArrayList<Park>();	
		List<Park> parks = jdbcTemplate.query(sql, new ParkRowMapper());
		List<Double> distances = new ArrayList<Double>();

		for (Park park: parks) {
			double distance = geoUtil.getDistance(longitude, latitude, park.getLongitude(), park.getLatitude());
			if (distance <= radius) {
				filteredParks.add(park);
				distances.add(distance);
			}
		}

		return filteredParks;	
	}
	
	public List<Park> getAllParks() {
		jdbcTemplate.execute("use parkbench");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId";
		List<Park> parks = (List<Park>) jdbcTemplate.query(sql, new ParkRowMapper());
		return parks;
	}

	public List<Park> getAllParks(String parkName, String resortName) {
		jdbcTemplate.execute("use parkbench");

		if (parkName == null) {
			parkName = "%";
		}
		else {
			parkName = "%" + parkName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}

		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId"
				+ " WHERE park.name LIKE ? AND resort.name LIKE ?";
		List<Park> parks = (List<Park>) jdbcTemplate.query(sql, new Object[] {parkName, resortName}, new ParkRowMapper());
		return parks;
	}

}
