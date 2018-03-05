package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.rowmappers.AttractionRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class AttractionDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String ATTRACTION_COLUMNS = "SELECT attraction.attractionId, attraction.name, attraction.short_name, attraction.longitude, attraction.latitude, attraction.land, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";

	
	public List<Attraction> getAttractions(String resortId, String parkId) {
		jdbcTemplate.execute("use parkbench");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND attraction.parkId = ?";
		List<Attraction> attractions = jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new AttractionRowMapper());
		return attractions;
	}
	
	public Attraction getAttractionById(String resortId, String parkId, String attractionId) {
		jdbcTemplate.execute("use parkbench");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND attraction.parkId = ? AND attraction.attractionId = ?";
		Attraction attraction = (Attraction) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, attractionId}, new AttractionRowMapper());
		return attraction;
	}
	
	public List<Attraction> getAttractionsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use parkbench");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		List<Attraction> filteredAttractions = new ArrayList<Attraction>();	
		List<Attraction> attractions = jdbcTemplate.query(sql, new AttractionRowMapper());
		List<Double> distances = new ArrayList<Double>();

		for (Attraction attraction: attractions) {
			double distance = geoUtil.getDistance(longitude, latitude, attraction.getLongitude(), attraction.getLatitude());
			if (distance <= radius) {
				filteredAttractions.add(attraction);
				distances.add(distance);
			}
		}

		return filteredAttractions;
	}

	public List<Attraction> getAllAttractions() {
		jdbcTemplate.execute("use parkbench");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";
		List<Attraction> attractions = jdbcTemplate.query(sql, new AttractionRowMapper());
		return attractions;
	}

	public List<Attraction> getAllAttractions(String attractionName, String parkName, String resortName) {
		jdbcTemplate.execute("use parkbench");

		if (attractionName == null) {
			attractionName = "%";
		}
		else {
			attractionName = "%" + attractionName + "%";
		}

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

		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE attraction.name LIKE ? AND park.name LIKE ? AND resort.name LIKE ?";
		List<Attraction> attractions = jdbcTemplate.query(sql, new Object[] {attractionName, parkName, resortName}, new AttractionRowMapper());
		return attractions;
	}
	
}
