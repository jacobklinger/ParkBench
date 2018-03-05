package com.parkbenchapi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.parkbenchapi.model.Snack;
import com.parkbenchapi.rowmappers.SnackRowMapper;
import com.parkbenchapi.util.GeoUtil;

@Component
public class SnackDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	GeoUtil geoUtil;
	
	private final String SNACK_COLUMNS = "SELECT snack.snackId, snack.name, resort.resortId as resortId, resort.name as resort_name";
	
	
	public List<Snack> getSnacks(String resortId) {
		jdbcTemplate.execute("use parkbench");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = snack.resortId"
				+ " WHERE snack.resortId = ?";
		List<Snack> snacks = jdbcTemplate.query(sql, new Object[] {resortId}, new SnackRowMapper());
		return snacks;
	}
	
	public Snack getSnackById(String resortId, String snackId) {
		jdbcTemplate.execute("use parkbench");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId"
				+ " WHERE snack.resortId = ? AND snack.snackId = ?";
		Snack snack = (Snack) jdbcTemplate.queryForObject(sql, new Object[] {resortId, snackId}, new SnackRowMapper());
		return snack;
	}
	
	public List<Snack> getAllSnacks() {
		jdbcTemplate.execute("use parkbench");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId";
		List<Snack> snacks = jdbcTemplate.query(sql, new SnackRowMapper());
		return snacks;
	}
	
	public List<Snack> getAllSnacks(String snackName, String resortName) {
		if (snackName == null) {
			snackName = "%";
		}
		else {
			snackName = "%" + snackName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}
		
		jdbcTemplate.execute("use parkbench");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId"
				+ " WHERE snack.name LIKE ? AND resort.name LIKE ?";
		List<Snack> snacks = jdbcTemplate.query(sql, new Object[] {snackName, resortName}, new SnackRowMapper());
		return snacks;
	}

}
