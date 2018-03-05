package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Resort;
import com.parkbenchapi.rowmappers.ResortRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class ResortDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String RESORT_COLUMNS = "SELECT resort.resortId, resort.name, resort.short_name, resort.longitude, resort.latitude";
	
	
	public List<Resort> getAllResorts() {
		jdbcTemplate.execute("use parkbench");
		String sql = RESORT_COLUMNS
				+ " FROM resort";
		List<Resort> resorts = jdbcTemplate.query(sql, new ResortRowMapper());
		return resorts;
	}
	
	public Resort getResortById(String resortId) {
		jdbcTemplate.execute("use parkbench");
		String sql = RESORT_COLUMNS
				+ " FROM resort"
				+ " WHERE resort.resortId = ?";
		Resort resort = (Resort) jdbcTemplate.queryForObject(sql, new Object[] {resortId}, new ResortRowMapper());
		return resort;
	}
	
	public List<Resort> getResortsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use parkbench");
		String sql = RESORT_COLUMNS
				+ " FROM resort";

		List<Resort> filteredResorts = new ArrayList<Resort>();	
		List<Resort> resorts = jdbcTemplate.query(sql, new ResortRowMapper());
		List<Double> distances = new ArrayList<Double>();

		for (Resort resort: resorts) {
			double distance = geoUtil.getDistance(longitude, latitude, resort.getLongitude(), resort.getLatitude());
			if (distance <= radius) {
				filteredResorts.add(resort);
				distances.add(distance);
			}
		}

		return filteredResorts;	
	}
	
	public List<Resort> getAllResorts(String name) {
		jdbcTemplate.execute("use parkbench");

		if (name == null) {
			name = "%";
		}
		else {
			name = "%" + name + "%";
		}

		String sql = RESORT_COLUMNS
				+ " FROM resort"
				+ " WHERE resort.name LIKE ?";
		List<Resort> resorts = jdbcTemplate.query(sql, new Object[] {name}, new ResortRowMapper());
		return resorts;
	}
	
}
