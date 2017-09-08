package com.parkbenchapi.dao;

import java.util.ArrayList;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.Character;

public interface DataAccessObject {
	
	public ArrayList<LocatableEntity> getParkByName(String name);
	public ArrayList<LocatableEntity> getParkByLocation(double longitude, double latititude, double radius);
	public ArrayList<LocatableEntity> getAllParks();
	
	public ArrayList<Hotel> getHotels(String name);
	public ArrayList<Hotel> gethotelByResortArea(String resortArea);
	public ArrayList<Hotel> getHotelByLocation(double longitude, double latititude, double radius);
	public ArrayList<Hotel> getAllHotels();

	public ArrayList<Attraction> getAttractionsByName(String name);
	public ArrayList<Attraction> getAttractionsByPark(String parkName);
	public ArrayList<Attraction> getAttractionsByLand(String landName);
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latititude,double radius);
	public ArrayList<Attraction> getAllAttractions();
	
	public ArrayList<LocatableEntity> getDiningByName(String name);
	public ArrayList<LocatableEntity> getDiningByPark(String parkName);
	public ArrayList<LocatableEntity> getDiningByLand(String landName);
	public ArrayList<LocatableEntity> getDiningByLocation(double longitude, double latititude,double radius);
	public ArrayList<LocatableEntity> getAllDining();
	
	public ArrayList<Entity> getSnacksByName(String name);
	public ArrayList<Entity> getAllSnacks();
	
	public ArrayList<Character> getCharactersByName(String name);
	public ArrayList<Character> getAllCharacters();
	
	public ArrayList<LocatableEntity> getEntertainmentByName(String name);
	public ArrayList<LocatableEntity> getEntertainmentByPark(String parkName);
	public ArrayList<LocatableEntity> getEntertainmentByLand(String landName);
	public ArrayList<LocatableEntity> getEntertainmentByLocation(double longitude, double latititude,double radius);
	public ArrayList<LocatableEntity> getAllEntertainment();
	
}
