package com.parkbenchapi.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.parkbenchapi.model.*;
import com.parkbenchapi.rowmappers.*;

@Service
public class DataAccessObjectImpl implements DataAccessObject {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Resort getResortById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM resort WHERE resortId = ?";
		Resort resort = (Resort) jdbcTemplate.queryForObject(sql, new Object[] {id}, new ResortRowMapper());
		return resort;
	}

	@Override
	public ArrayList<Resort> getResortsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM resort";
		
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
	public ArrayList<Resort> getAllResorts() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM resort";
		ArrayList<Resort> resorts = (ArrayList<Resort>) jdbcTemplate.query(sql, new ResortRowMapper());
		return resorts;
	}

	@Override
	public ArrayList<Park> getAllParks() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS + 
				" FROM park " + 
				" INNER JOIN resort ON park.resortId = resort.resortId";
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new ParkRowMapper());
		return parks;
	}
	
	@Override
	public Park getParkById(String id, String parkId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS + 
				" FROM park " + 
				" INNER JOIN resort ON park.resortId = resort.resortId" + 
				" WHERE park.parkId = ?";
		Park park = (Park) jdbcTemplate.queryForObject(sql, new Object[] {id}, new ParkRowMapper());
		return park;
	}
	
	@Override
	public ArrayList<Park> getParksByResort(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS + 
				" FROM park " + 
				" INNER JOIN resort ON park.resortId = resort.resortId" + 
				" WHERE park.resortId = ?";
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new Object[] {resortId}, new ParkRowMapper());
		return parks;
	}

	@Override
	public ArrayList<Park> getParkByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = PARK_COLUMNS + 
				" FROM park " + 
				" INNER JOIN resort ON park.resortId = resort.resortId";
		
		ArrayList<Park> filteredParks = new ArrayList<Park>();	
		ArrayList<Park> parks = (ArrayList<Park>) jdbcTemplate.query(sql, new ParkRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();
		
		for (Park locatableEntity: parks) {
			double distance = getDistance(longitude, latitude, locatableEntity.getLongitude(), locatableEntity.getLatitude());
			if (distance <= radius) {
				filteredParks.add(locatableEntity);
				distances.add(distance);
			}
		}
			
		return filteredParks;
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
	public Hotel getHotelById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId "
				+ " WHERE hotelId = ?";
		Hotel hotel = (Hotel) jdbcTemplate.queryForObject(sql, new Object[] {id}, new HotelRowMapper());
		return hotel;
	}
	
	@Override
	public ArrayList<Hotel> getHotelsByResort(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId "
				+ " WHERE hotel.resortId = ?";
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new Object[] {resortId}, new HotelRowMapper());
		return hotels;
	}

	@Override
	public ArrayList<Hotel> getHotelByResortArea(String resortArea) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = HOTEL_COLUMNS
				+ " FROM hotel"
				+ " INNER JOIN resort ON hotel.resortId = resort.resortId "
				+ " WHERE resort_area = ?";
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) jdbcTemplate.query(sql, new Object[] {resortArea}, new HotelRowMapper());
		return hotels;
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
	public Attraction getAttractionById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction  "
				+ "INNER JOIN park on attraction.parkId = park.parkId)"
				+ "INNER JOIN resort on park.resortId = resort.resortId) "
				+ "WHERE attractionId = ?";
		Attraction attraction = (Attraction) jdbcTemplate.queryForObject(sql, new Object[] {id}, new AttractionRowMapper());
		return attraction;
	}
	
	@Override
	public Attraction getAttractionParkAndById(String parkId, String attractionId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction  "
				+ " INNER JOIN park on attraction.parkId = park.parkId)"
				+ " INNER JOIN resort on park.resortId = resort.resortId) "
				+ " WHERE park.parkId = ? AND attraction.attractionId = ?";
		Attraction attraction = (Attraction) jdbcTemplate.queryForObject(sql, new Object[] {parkId, attractionId}, new AttractionRowMapper());
		return attraction;
	}
	
	public Attraction getAttractionResortAndById(String resortId, String attractionId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS
				+ " FROM ((attraction  "
				+ " INNER JOIN park on attraction.parkId = park.parkId)"
				+ " INNER JOIN resort on park.resortId = resort.resortId) "
				+ " WHERE resort.resortId = ? AND attraction.attractionId = ?";
		Attraction attraction = (Attraction) jdbcTemplate.queryForObject(sql, new Object[] {resortId, attractionId}, new AttractionRowMapper());
		return attraction;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByPark(String parkName) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS + 
				" FROM ((attraction " + 
				" INNER JOIN park ON attraction.parkId = park.parkId)" +
				" INNER JOIN resort on park.resortId = resort.resortId)" + 
				" WHERE park.parkId = ?";
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new Object[] {parkName}, new AttractionRowMapper());
		return attractions;
	}
	
	@Override
	public ArrayList<Attraction> getAttractionsByResort(String resortId) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS + 
				" FROM ((attraction " + 
				" INNER JOIN park ON attraction.parkId = park.parkId)" +
				" INNER JOIN resort on park.resortId = resort.resortId) " + 
				" WHERE resort.resortId = ?";
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new Object[] {resortId}, new AttractionRowMapper());
		return attractions;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = ATTRACTION_COLUMNS + 
				" FROM ((attraction " + 
				" INNER JOIN park ON attraction.parkId = park.parkId)"
				+ "INNER JOIN resort on park.resortId = resort.resortId)";
		
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
		String sql = ATTRACTION_COLUMNS + 
				" FROM ((attraction " + 
				" INNER JOIN park ON attraction.parkId = park.parkId)"
				+ "INNER JOIN resort on park.resortId = resort.resortId)";
		ArrayList<Attraction> attractions = (ArrayList<Attraction>) jdbcTemplate.query(sql, new AttractionRowMapper());
		return attractions;
	}

	@Override
	public Dining getDiningById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT dining.diningId, dining.name, dining.short_name, dining.land, dining.longitude, dining.latitude, park.name as park, hotel.name as hotel"
				+ "FROM ((dining "
				+ "INNER JOIN dining ON dining.parkId = park.parkId) "
				+ "INNER JOIN dining ON dining.hotelId = hotel.hotelId) "
				+ "WHERE diningId = ?";
		Dining dining = (Dining) jdbcTemplate.queryForObject(sql, new Object[] {id}, new DiningRowMapper());
		return dining;
	}

	@Override
	public ArrayList<Dining> getDiningByPark(String parkName) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT dining.diningId, dining.name, dining.short_name, dining.land, dining.park, dining.hotel, dining.longitude, dining.latitude" + 
				" FROM dining" + 
				" INNER JOIN park ON dining.park=park.name" + 
				" WHERE park.parkId = ?";
		ArrayList<Dining> dining = (ArrayList<Dining>) jdbcTemplate.query(sql, new Object[] {parkName}, new DiningRowMapper());
		return dining;
	}
	
	

	@Override
	public ArrayList<Dining> getDiningByHotel(String hotelName) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT dining.diningId, dining.name, dining.short_name, dining.land, dining.park, dining.hotel, dining.longitude, dining.latitude" + 
				" FROM dining" + 
				" INNER JOIN park ON dining.hotel=park.hotel" + 
				" WHERE hotel.hotelId = ?";
		ArrayList<Dining> dining = (ArrayList<Dining>) jdbcTemplate.query(sql, new Object[] {hotelName}, new DiningRowMapper());
		return dining;
	}

	@Override
	public ArrayList<Dining> getDiningByLocation(double longitude, double latitude, double radius) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM dining";
		
		ArrayList<Dining> filteredDining = new ArrayList<Dining>();	
		ArrayList<Dining> dinings = (ArrayList<Dining>) jdbcTemplate.query(sql, new DiningRowMapper());
		ArrayList<Double> distances = new ArrayList<Double>();
		
		for (Dining dining: dinings) {
			double distance = getDistance(longitude, latitude, dining.getLongitude(), dining.getLatitude());
			if (distance <= radius) {
				filteredDining.add(dining);
				distances.add(distance);
			}
		}
			
		return filteredDining;
	}

	@Override
	public ArrayList<Dining> getAllDining() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM dining";
		ArrayList<Dining> dining = (ArrayList<Dining>) jdbcTemplate.query(sql, new DiningRowMapper());
		return dining;
	}
	
	@Override
	public Entertainment getEntertainmentById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "ENTERTAINMENT_COLUMNS" + 
				" FROM entertainment " + 
				" INNER JOIN park ON attraction.parkId = park.parkId "
				+ "WHERE entertainmentId = ?";
		Entertainment entertainment = (Entertainment) jdbcTemplate.queryForObject(sql, new Object[] {id}, new EntertainmentRowMapper());
		return entertainment;
	}

	@Override
	public ArrayList<Entertainment> getEntertainmentByPark(String parkName) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT entertainment.entertainmentId, entertainment.name, entertainment.short_name, entertainment.land, entertainment.park, entertainment.longitude, entertainment.latitude" + 
				" FROM attraction" + 
				" INNER JOIN park ON entertainment.park=park.name" + 
				" WHERE park.parkId = ?";
		ArrayList<Entertainment> entertainment = (ArrayList<Entertainment>) jdbcTemplate.query(sql, new Object[] {parkName}, new EntertainmentRowMapper());
		return entertainment;
	}

	@Override
	public ArrayList<Entertainment> getAllEntertainment() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM entertainment";
		ArrayList<Entertainment> entertainment = (ArrayList<Entertainment>) jdbcTemplate.query(sql, new EntertainmentRowMapper());
		return entertainment;
	}

	@Override
	public CharacterMeet getCharacterById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM character_meet WHERE characterId = ?";
		CharacterMeet character = (CharacterMeet) jdbcTemplate.queryForObject(sql, new Object[] {id}, new CharacterRowMapper());
		return character;
	}

	@Override
	public ArrayList<CharacterMeet> getAllCharacters() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM character_meet";
		ArrayList<CharacterMeet> characters = (ArrayList<CharacterMeet>) jdbcTemplate.query(sql, new CharacterRowMapper());
		return characters;
	}
	
	@Override
	public Snack getSnackById(String id) {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM snack WHERE snackId = ?";
		Snack snack = (Snack) jdbcTemplate.queryForObject(sql, new Object[] {id}, new SnackRowMapper());
		return snack;
	}

	@Override
	public ArrayList<Snack> getAllSnacks() {
		jdbcTemplate.execute("use ParkBenchDB");
		String sql = "SELECT * FROM snack";
		ArrayList<Snack> snacks = (ArrayList<Snack>) jdbcTemplate.query(sql, new SnackRowMapper());
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
	
	private String ATTRACTION_COLUMNS = "SELECT attraction.attractionId, attraction.name, attraction.short_name, attraction.land, attraction.longitude, attraction.latitude, park.parkId as parkId, park.name as park, resort.resortId as resortId, resort.name as resort";
	private String PARK_COLUMNS = "SELECT park.parkId, park.name, park.short_name, park.longitude, park.latitude, resort.name as resort_name, resort.resortId as resortId";
	private String HOTEL_COLUMNS = "SELECT hotel.hotelId, hotel.name, hotel.short_name, hotel.longitude, hotel.latitude, hotel.resort_area, resort.name as resort_name, resort.resortId as resortId";
	private String ENTERTAINMENT_COLUMNS = "SELECT entertainment.entertainmentId, entertainment.name, entertainment.short_name, entertainment.longitude, entertainment.latitude, park.parkId as parkId, park.name as park, resort.name as resort_name, resort.resortId as resortId";

	@Override
	public ArrayList<Dining> getDiningByResort(String resortId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entertainment> getEntertainmentByResort(String resortId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<CharacterMeet> getCharactersByResort(String resortId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Snack> getSnacksByResort(String resortId) {
		// TODO Auto-generated method stub
		return null;
	}

}
