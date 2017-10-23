package com.parkbenchapi.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.parkbenchapi.model.*;
import com.parkbenchapi.rowmappers.*;

@Service
public class DataAccessObjectImpl implements DataAccessObject {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private final String RESORT_COLUMNS = "SELECT resort.resortId, resort.name, resort.short_name, resort.longitude, resort.latitude";
	private final String PARK_COLUMNS = "SELECT park.parkId, park.name, park.short_name, park.longitude, park.latitude, resort.resortId as resortId, resort.name as resort_name";
	private final String HOTEL_COLUMNS = "SELECT hotel.hotelId, hotel.name, hotel.short_name, hotel.longitude, hotel.latitude, hotel.resort_area, resort.resortId as resortId, resort.name as resort_name";
	private final String ATTRACTION_COLUMNS = "SELECT attraction.attractionId, attraction.name, attraction.short_name, attraction.longitude, attraction.latitude, attraction.land, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";
	private final String PARK_DINING_COLUMNS = "SELECT park_dining.diningId, park_dining.name, park_dining.short_name, park_dining.land, park_dining.longitude, park_dining.latitude, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";
	private final String ENTERTAINMENT_COLUMNS = "SELECT entertainment.entertainmentId, entertainment.name, entertainment.short_name, entertainment.land, park.parkId as parkId, park.name as park_name, resort.resortId as resortId, resort.name as resort_name";
	private final String HOTEL_DINING_COLUMNS = "SELECT hotel_dining.diningId, hotel_dining.name, hotel_dining.short_name, hotel_dining.longitude, hotel_dining.latitude, hotel.hotelId as hotelId, hotel.name as hotel_name, resort.resortId as resortId, resort.name as resort_name";
	private final String CHARACTER_COLUMNS = "SELECT character_meet.characterId, character_meet.name, resort.resortId as resortId, resort.name as resort_name";
	private final String SNACK_COLUMNS = "SELECT snack.snackId, snack.name, resort.resortId as resortId, resort.name as resort_name";

	@Override
	public ArrayList<Resort> getResorts() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = RESORT_COLUMNS
				+ " FROM resort";
		ArrayList<Resort> resorts = (ArrayList<Resort>) jdbcTemplate.query(sql, new ResortRowMapper());
		return resorts;
	}

	@Override
	public Resort getResortById(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = RESORT_COLUMNS
				+ " FROM resort"
				+ " WHERE resort.resortId = ?";
		Resort resort = (Resort) jdbcTemplate.queryForObject(sql, new Object[] {resortId}, new ResortRowMapper());
		return resort;
	}

	@Override
	public ArrayList<Resort> getResortsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = RESORT_COLUMNS
				+ " FROM resort";

		ArrayList<Resort> filteredResorts = new ArrayList<Resort>();	
		ArrayList<Resort> resorts = (ArrayList<Resort>) jdbcTemplate.query(sql, new ResortRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();

		for (Resort resort: resorts) {
			double distance = getDistance(longitude, latitude, resort.getLongitude(), resort.getLatitude());
			if (distance <= radius) {
				filteredResorts.add(resort);
				distances.add(distance);
			}
		}

		return filteredResorts;	
	}

	@Override
	public ArrayList<Resort> getAllResorts(String name) {
		jdbcTemplate.execute("use ParkBenchDB");

		if (name == null) {
			name = "%";
		}
		else {
			name = "%" + name + "%";
		}

		String sql = RESORT_COLUMNS
				+ " FROM resort"
				+ " WHERE resort.name LIKE ?";
		ArrayList<Resort> resorts = (ArrayList<Resort>) jdbcTemplate.query(sql, new Object[] {name}, new ResortRowMapper());
		return resorts;
	}

	@Override
	public ArrayList<Park> getParks(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId"
				+ " WHERE park.resortId = ?";
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new Object[] {resortId}, new ParkRowMapper());
		return parks;
	}

	@Override
	public Park getParkById(String resortId, String parkId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId"
				+ " WHERE park.resortId = ? AND park.parkId = ?";
		Park park = (Park) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId}, new ParkRowMapper());
		return park;
	}

	@Override
	public ArrayList<Park> getParkByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId";

		ArrayList<Park> filteredParks = new ArrayList<Park>();	
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new ParkRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();

		for (Park park: parks) {
			double distance = getDistance(longitude, latitude, park.getLongitude(), park.getLatitude());
			if (distance <= radius) {
				filteredParks.add(park);
				distances.add(distance);
			}
		}

		return filteredParks;	
	}

	@Override
	public ArrayList<Park> getAllParks() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS
				+ " FROM park"
				+ " INNER JOIN resort ON park.resortId = resort.resortId";
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new ParkRowMapper());
		return parks;
	}

	@Override
	public ArrayList<Park> getAllParks(String parkName, String resortName) {
		jdbcTemplate.execute("use ParkBenchDB");

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
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new Object[] {parkName, resortName}, new ParkRowMapper());
		return parks;
	}
	@Override
	public ArrayList<Hotel> getHotels(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId"
				+ " WHERE hotel.resortId = ?";
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new Object[] {resortId}, new HotelRowMapper());
		return hotels;
	}
	@Override
	public Hotel getHotelById(String resortId, String hotelId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId"
				+ " WHERE hotel.resortId = ? AND hotel.hotelId = ?";
		Hotel hotel = (Hotel) jdbcTemplate.queryForObject(sql, new Object[] {resortId, hotelId}, new HotelRowMapper());
		return hotel;
	}
	@Override
	public ArrayList<Hotel> getHotelsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId";

		ArrayList<Hotel> filteredHotels = new ArrayList<Hotel>();	
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new HotelRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();

		for (Hotel hotel: hotels) {
			double distance = getDistance(longitude, latitude, hotel.getLongitude(), hotel.getLatitude());
			if (distance <= radius) {
				filteredHotels.add(hotel);
				distances.add(distance);
			}
		}

		return filteredHotels;	
	}

	@Override
	public ArrayList<Hotel> getAllHotels() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId";
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new HotelRowMapper());
		return hotels;
	}

	@Override
	public ArrayList<Hotel> getAllHotels(String hotelName, String resortName) {
		jdbcTemplate.execute("use ParkBenchDB");

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
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new Object[] {hotelName, resortName}, new HotelRowMapper());
		return hotels;
	}

	@Override
	public ArrayList<Attraction> getAttractions(String resortId, String parkId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND attraction.parkId = ?";
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new AttractionRowMapper());
		return attractions;
	}
	@Override
	public Attraction getAttractionById(String resortId, String parkId, String attractionId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND attraction.parkId = ? AND attraction.attractionId = ?";
		Attraction attraction = (Attraction) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, attractionId}, new AttractionRowMapper());
		return attraction;
	}
	@Override
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		ArrayList<Attraction> filteredAttractions = new ArrayList<Attraction>();	
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new AttractionRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();

		for (Attraction attraction: attractions) {
			double distance = getDistance(longitude, latitude, attraction.getLongitude(), attraction.getLatitude());
			if (distance <= radius) {
				filteredAttractions.add(attraction);
				distances.add(distance);
			}
		}

		return filteredAttractions;
	}

	@Override
	public ArrayList<Attraction> getAllAttractions() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction"
				+ " INNER JOIN park ON attraction.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new AttractionRowMapper());
		return attractions;
	}

	@Override
	public ArrayList<Attraction> getAllAttractions(String attractionName, String parkName, String resortName) {
		jdbcTemplate.execute("use ParkBenchDB");

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
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new Object[] {attractionName, parkName, resortName}, new AttractionRowMapper());
		return attractions;
	}
	@Override
	public ArrayList<ParkDining> getParkDining(String resortId, String parkId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND park_dining.parkId = ?";
		ArrayList<ParkDining> parkDining = (ArrayList<ParkDining>) jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new DiningRowMapper());
		return parkDining;
	}

	@Override
	public ParkDining getParkDiningById(String resortId, String parkId, String diningId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND park_dining.parkId = ? AND park_dining.diningId = ?";
		ParkDining parkDining = (ParkDining) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, diningId}, new DiningRowMapper());
		return parkDining;
	}

	@Override
	public ArrayList<ParkDining> getParkDiningByLocation(double longitude, double latitude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Entertainment> getEntertainment(String resortId, String parkId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND entertainment.parkId = ?";
		ArrayList<Entertainment> entertainment = (ArrayList<Entertainment>) jdbcTemplate.query(sql, new Object[] {resortId, parkId}, new EntertainmentRowMapper());
		return entertainment;
	}

	@Override
	public Entertainment getEntertainmentById(String resortId, String parkId, String entertainmentId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND entertainment.parkId = ? AND entertainment.entertainmentId = ?";
		Entertainment entertainment = (Entertainment) jdbcTemplate.queryForObject(sql, new Object[] {resortId, parkId, entertainmentId}, new EntertainmentRowMapper());
		return entertainment;
	}
	@Override
	public ArrayList<Entertainment> getAllEntertainment() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ENTERTAINMENT_COLUMNS
				+ " FROM ((entertainment"
				+ " INNER JOIN park ON entertainment.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";
		ArrayList<Entertainment> entertainment = (ArrayList<Entertainment>) jdbcTemplate.query(sql, new EntertainmentRowMapper());
		return entertainment;
	}
	@Override
	public ArrayList<Entertainment> getAllEntertainment(String entertainmentName, String resortName, String parkName) {
		jdbcTemplate.execute("use ParkBenchDB");

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
		ArrayList<Entertainment> entertainment = (ArrayList<Entertainment>) jdbcTemplate.query(sql, new Object[] {entertainmentName, parkName, resortName}, new EntertainmentRowMapper());
		return entertainment;
	}
	@Override
	public ArrayList<HotelDining> getHotelDining(String resortId, String hotelId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND hotel_dining.hotelId = ?";
		ArrayList<HotelDining> hotelDining = (ArrayList<HotelDining>) jdbcTemplate.query(sql, new Object[] {resortId, hotelId}, new DiningRowMapper());
		return hotelDining;
	}

	@Override
	public HotelDining getHotelDiningById(String resortId, String hotelId, String diningId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
				+ " WHERE resort.resortId = ? AND hotel_dining.hotelId = ? AND hotel_dining.diningId = ?";
		HotelDining hotelDining = (HotelDining) jdbcTemplate.queryForObject(sql, new Object[] {resortId, hotelId, diningId}, new DiningRowMapper());
		return hotelDining;
	}
	@Override
	public ArrayList<HotelDining> getHotelDiningByLocation(double longitude, double latitude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<LocatableResource> getAllDining() {
		ArrayList<LocatableResource> allDining = new ArrayList<LocatableResource>();

		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)";

		ArrayList<LocatableResource> hotelDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new DiningRowMapper());	

		sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		ArrayList<LocatableResource> parkDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new DiningRowMapper());

		allDining.addAll(parkDining);
		allDining.addAll(hotelDining);

		return allDining;
	}

	public ArrayList<LocatableResource> getDiningByLocation(double longitude, double latitude,double radius) {

		ArrayList<LocatableResource> filteredDining = new ArrayList<LocatableResource>();
		ArrayList<Double> distances = new ArrayList<Double>();

		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_DINING_COLUMNS
				+ " FROM ((hotel_dining"
				+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId)";

		ArrayList<LocatableResource> hotelDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new DiningRowMapper());	

		for (LocatableResource dining: hotelDining) {
			double distance = getDistance(longitude, latitude, dining.getLongitude(), dining.getLatitude());
			if (distance <= radius) {
				filteredDining.add(dining);
				distances.add(distance);
			}
		}

		sql = PARK_DINING_COLUMNS
				+ " FROM ((park_dining"
				+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
				+ " INNER JOIN resort ON park.resortId = resort.resortId)";

		ArrayList<LocatableResource> parkDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new DiningRowMapper());

		for (LocatableResource dining: parkDining) {
			double distance = getDistance(longitude, latitude, dining.getLongitude(), dining.getLatitude());
			if (distance <= radius) {
				filteredDining.add(dining);
			}
		}

		return filteredDining;	
	}

	@Override
	public ArrayList<LocatableResource> getAllDining(String diningName, String resortName, String hotelName, String parkName) {
		
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

		ArrayList<LocatableResource> allDining = new ArrayList<LocatableResource>();

		jdbcTemplate.execute("use ParkBenchDB");
		
		if (hotelName != null && parkName == null) {
			hotelName = "%" + hotelName + "%";
			String sql = HOTEL_DINING_COLUMNS
					+ " FROM ((hotel_dining"
					+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
					+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
					+ " WHERE hotel_dining.name LIKE ? AND hotel.name LIKE ? AND resort.name LIKE ?";

			ArrayList<LocatableResource> hotelDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new Object[] {diningName, hotelName, resortName}, new DiningRowMapper());	
			allDining.addAll(hotelDining);
		}
		else if (parkName != null && hotelName == null) {
			parkName = "%" + parkName + "%";
			String sql = PARK_DINING_COLUMNS
					+ " FROM ((park_dining"
					+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
					+ " INNER JOIN resort ON park.resortId = resort.resortId)"
					+ " WHERE park_dining.name LIKE ? AND park.name LIKE ? AND resort.name LIKE ?";
			
			ArrayList<LocatableResource> parkDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new Object[] {diningName, parkName, resortName}, new DiningRowMapper());
			allDining.addAll(parkDining);
		}
		else {
			hotelName = "%" + hotelName + "%";
			String sql = HOTEL_DINING_COLUMNS
					+ " FROM ((hotel_dining"
					+ " INNER JOIN hotel ON hotel_dining.hotelId = hotel.hotelId)"
					+ " INNER JOIN resort ON hotel.resortId = resort.resortId)"
					+ " WHERE hotel_dining.name LIKE ? AND resort.name LIKE ?";

			ArrayList<LocatableResource> hotelDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new Object[] {diningName, resortName}, new DiningRowMapper());	
			allDining.addAll(hotelDining);
			
			sql = PARK_DINING_COLUMNS
					+ " FROM ((park_dining"
					+ " INNER JOIN park ON park_dining.parkId = park.parkId)"
					+ " INNER JOIN resort ON park.resortId = resort.resortId)"
					+ " WHERE park_dining.name LIKE ? AND resort.name LIKE ?";
			
			ArrayList<LocatableResource> parkDining = (ArrayList<LocatableResource>) jdbcTemplate.query(sql, new Object[] {diningName, resortName}, new DiningRowMapper());
			allDining.addAll(parkDining);
		}

		return allDining;
	}
	
	@Override
	public ArrayList<CharacterMeet> getCharacters(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.resortId = ?";
		ArrayList<CharacterMeet> characters = (ArrayList<CharacterMeet>) jdbcTemplate.query(sql, new Object[] {resortId}, new CharacterRowMapper());
		return characters;
	}
	
	@Override
	public CharacterMeet getCharacterById(String resortId, String characterId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.resortId = ? AND character_meet.characterId = ?";
		CharacterMeet character = (CharacterMeet) jdbcTemplate.queryForObject(sql, new Object[] {resortId, characterId}, new CharacterRowMapper());
		return character;
	}
	
	@Override
	public ArrayList<CharacterMeet> getAllCharacters() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId";
		ArrayList<CharacterMeet> characters = (ArrayList<CharacterMeet>) jdbcTemplate.query(sql, new CharacterRowMapper());
		return characters;
	}
	
	@Override
	public ArrayList<CharacterMeet> getAllCharacters(String characterName, String resortName) {
		if (characterName == null) {
			characterName = "%";
		}
		else {
			characterName = "%" + characterName + "%";
		}

		if (resortName == null) {
			resortName = "%";
		}
		else {
			resortName = "%" + resortName + "%";
		}
		
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = CHARACTER_COLUMNS
				+ " FROM character_meet"
				+ " INNER JOIN resort ON character_meet.resortId = resort.resortId"
				+ " WHERE character_meet.name LIKE ? AND resort.name LIKE ?";
		ArrayList<CharacterMeet> characters = (ArrayList<CharacterMeet>) jdbcTemplate.query(sql, new Object[] {characterName, resortName}, new CharacterRowMapper());
		return characters;
	}
	
	@Override
	public ArrayList<Snack> getSnacks(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = snack.resortId"
				+ " WHERE snack.resortId = ?";
		ArrayList<Snack> snacks = (ArrayList<Snack>) jdbcTemplate.query(sql, new Object[] {resortId}, new SnackRowMapper());
		return snacks;
	}
	
	@Override
	public Snack getSnackById(String resortId, String snackId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId"
				+ " WHERE snack.resortId = ? AND snack.snackId = ?";
		Snack snack = (Snack) jdbcTemplate.queryForObject(sql, new Object[] {resortId, snackId}, new SnackRowMapper());
		return snack;
	}
	
	@Override
	public ArrayList<Snack> getAllSnacks() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId";
		ArrayList<Snack> snacks = (ArrayList<Snack>) jdbcTemplate.query(sql, new SnackRowMapper());
		return snacks;
	}
	
	@Override
	public ArrayList<Snack> getAllSnacks(String snackName, String resortName) {
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
		
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = SNACK_COLUMNS
				+ " FROM snack"
				+ " INNER JOIN resort ON snack.resortId = resort.resortId"
				+ " WHERE snack.name LIKE ? AND resort.name LIKE ?";
		ArrayList<Snack> snacks = (ArrayList<Snack>) jdbcTemplate.query(sql, new Object[] {snackName, resortName}, new SnackRowMapper());
		return snacks;
	}

	private double getDistance(double long1, double lat1, double long2, double lat2) {
		double longDiff = long2-long1;
		longDiff = Math.toRadians(longDiff);
		double latDiff = lat2-lat1;
		latDiff = Math.toRadians(latDiff);
		double distance = (Math.sin(latDiff/2) * Math.sin(latDiff/2) + Math.sin(longDiff/2) * Math.sin(longDiff/2)) * Math.cos(lat1) * Math.cos(lat2);
		distance = 2 * Math.atan2(Math.sqrt(distance), Math.sqrt(1 - distance));
		return distance * 20903520;
	}
}