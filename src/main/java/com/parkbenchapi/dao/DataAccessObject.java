package com.parkbenchapi.dao;

import java.util.ArrayList;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.CharacterMeet;

public interface DataAccessObject {
	
	public ArrayList<Resort> getAllResorts();
	public Resort getResortById(String name);
	public ArrayList<Resort> getResortsByLocation(double longitude, double latitude, double radius);
	
	public ArrayList<Park> getAllParks();
	public Park getParkById(String resortId, String name);
	public ArrayList<Park> getParksByResort(String resortId);
	public ArrayList<Park> getParkByLocation(double longitude, double latitude, double radius);
	
	public Hotel getHotelById(String name);
	public ArrayList<Hotel> getHotelsByResort(String resortId);
	public ArrayList<Hotel> getHotelByResortArea(String resortArea);
	public ArrayList<Hotel> getHotelsByLocation(double longitude, double latitude, double radius);
	public ArrayList<Hotel> getAllHotels();

	public Attraction getAttractionById(String id);
	public Attraction getAttractionParkAndById(String parkId, String resortId);
	public Attraction getAttractionResortAndById(String parkId, String resortId);
	public ArrayList<Attraction> getAttractionsByPark(String parkId);
	public ArrayList<Attraction> getAttractionsByResort(String resortId);
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latitude,double radius);
	public ArrayList<Attraction> getAllAttractions();
	
	
	public Dining getDiningById(String id);
	public ArrayList<Dining> getDiningByResort(String resortId);
	public ArrayList<Dining> getDiningByPark(String parkId);
	public ArrayList<Dining> getDiningByHotel(String hotelName);
	public ArrayList<Dining> getDiningByLocation(double longitude, double latitude,double radius);
	public ArrayList<Dining> getAllDining();
	
	public Entertainment getEntertainmentById(String id);
	public ArrayList<Entertainment> getEntertainmentByResort(String resortId);
	public ArrayList<Entertainment> getEntertainmentByPark(String parkName);
	public ArrayList<Entertainment> getAllEntertainment();
	
	public CharacterMeet getCharacterById(String id);
	public ArrayList<CharacterMeet> getCharactersByResort(String resortId);
	public ArrayList<CharacterMeet> getAllCharacters();
	
	public Snack getSnackById(String id);
	public ArrayList<Snack> getSnacksByResort(String resortId);
	public ArrayList<Snack> getAllSnacks();
	
}
