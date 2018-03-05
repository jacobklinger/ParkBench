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
import com.parkbenchapi.dao.SnackDao;
import com.parkbenchapi.model.Snack;
import com.parkbenchapi.rowmappers.SnackRowMapper;
import com.parkbenchapi.util.GeoUtil;

@RunWith(SpringRunner.class)
public class SnackDaoTest {
	
	@InjectMocks
	private SnackDao dao = new SnackDao();
	
	@Mock
	JdbcTemplate jdbcTemplate;
	
	@Mock
	GeoUtil geoUtil;
	
	private ArrayList<Snack> snacks;
	private Snack snack;
	
	private String snackName = "Mickey Ice Cream Bar";
	private String resortName = "Walt Disney World";
	private String resortId = "walt-disney-world";
	private String snackId = "mickey-ice-cream-bar";
	
	@Before
	public void setup() throws Exception {
		snacks = new ArrayList<Snack>();
		snack = new Snack();
		snack.setName(snackName);
		snack.setResort(resortName, resortId);
		snack.generateLinks(resortId, snackId);
		snacks.add(snack);
		Mockito.doNothing().when(jdbcTemplate).execute(Matchers.anyString());
	}
	
	@Test
	public void getSnacksTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(SnackRowMapper.class))).thenReturn(snacks);
		List<Snack> actualSnacks = dao.getSnacks(resortId);
		Assert.assertEquals(snacks, actualSnacks);
	}
	
	@Test
	public void getSnackByIdTest() {
		Mockito.when(jdbcTemplate.queryForObject(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(SnackRowMapper.class))).thenReturn(snack);
		Snack actualSnack = dao.getSnackById(resortId, snackId);
		Assert.assertEquals(snack, actualSnack);
	}
	
	@Test
	public void getAllSnacksTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(SnackRowMapper.class))).thenReturn(snacks);
		List<Snack> actualSnacks = dao.getAllSnacks();
		Assert.assertEquals(snacks, actualSnacks);
	}
	
	@Test
	public void getAllSnacksWithParamsTest() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(SnackRowMapper.class))).thenReturn(snacks);
		List<Snack> actualSnacks = dao.getAllSnacks(snackName, resortName);
		Assert.assertEquals(snacks, actualSnacks);
	}
	
	@Test
	public void getAllSnacksTestWithNullParams() {
		Mockito.when(jdbcTemplate.query(Matchers.anyString(), Matchers.any(Object[].class), Matchers.any(SnackRowMapper.class))).thenReturn(snacks);
		List<Snack> actualSnacks = dao.getAllSnacks(null, null);
		Assert.assertEquals(snacks, actualSnacks);
	}

}
