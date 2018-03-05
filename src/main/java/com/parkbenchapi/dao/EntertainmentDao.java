package com.parkbenchapi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Entertainment;
import com.parkbenchapi.rowmappers.EntertainmentRowMapper;

@Component
public class EntertainmentDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	private final String ENTERTAINMENT_COLUMNS = "SELECT entertainment.entertainmentId, entertainment.name, entertainment.short_name, entertainment.land, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";

	
	public List<Entertainment> getEntertainment(String resortId, String parkId) {
		jdbcTemplate.execute("use parkbench");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND entertainment.parkId = ?";
		List<Entertainment> entertainment = jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new EntertainmentRowMapper());
		return entertainment;
	}
	
	public Entertainment getEntertainmentById(String resortId, String parkId, String entertainmentId) {
		jdbcTemplate.execute("use parkbench");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND entertainment.parkId = ? AND entertainment.entertainmentId = ?";
		Entertainment entertainment = jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, entertainmentId}, new EntertainmentRowMapper());
		return entertainment;
	}
	
	public List<Entertainment> getAllEntertainment() {
		jdbcTemplate.execute("use parkbench");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";
		List<Entertainment> entertainment = jdbcTemplate.query(sql, new EntertainmentRowMapper());
		return entertainment;
	}
	
	public List<Entertainment> getAllEntertainment(String entertainmentName, String resortName, String parkName) {
		jdbcTemplate.execute("use parkbench");

		if (entertainmentName == null) {
			entertainmentName = "%";
		}
		else {
			entertainmentName = "%" + entertainmentName + "%";
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

		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE entertainment.name LIKE ? AND park.name LIKE ? AND resort.name LIKE ?";
		List<Entertainment> entertainment = jdbcTemplate.query(sql, new Object[] {entertainmentName, parkName, resortName}, new EntertainmentRowMapper());
		return entertainment;
	}
	
}