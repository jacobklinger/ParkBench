package com.parkbenchapi.dao;

import java.util.ArrayList;
import com.parkbenchapi.model.*;
import com.parkbenchapi.model.CharacterMeet;

public interface DataAccessObject {
	
	public ArrayList<Resort> getResorts();
	public Resort getResortById(String resortId);
	public ArrayList<Resort> getResortsByLocation(double longitude, double latitude, double radius);
	public ArrayList<Resort> getAllResorts(String name);
	
	public ArrayList<Park> getParks(String resortId);
	public Park getParkById(String resortId, String parkId);
	public ArrayList<Park> getParkByLocation(double longitude, double latitude, double radius);
	public ArrayList<Park> getAllParks();
	public ArrayList<Park> getAllParks(String parkName, String resortName);
	
	public ArrayList<Hotel> getHotels(String resortId);
	public Hotel getHotelById(String resortId, String hotelId);
	public ArrayList<Hotel> getHotelsByLocation(double longitude, double latitude, double radius);
	public ArrayList<Hotel> getAllHotels();
	public ArrayList<Hotel> getAllHotels(String hotelName, String resortName);
	
	public ArrayList<Attraction> getAttractions(String resortId, String parkId);
	public Attraction getAttractionById(String resortId, String parkId, String attractionId);
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latitude,double radius);
	public ArrayList<Attraction> getAllAttractions();
	public ArrayList<Attraction> getAllAttractions(String attractionName, String resortName, String parkName);
	
	public ArrayList<ParkDining> getParkDining(String resortId, String parkId);
	public ParkDining getParkDiningById(String resortId, String parkId, String diningId);
	public ArrayList<ParkDining> getParkDiningByLocation(double longitude, double latitude,double radius);
	
	public ArrayList<Entertainment> getEntertainment(String resortId, String parkId);
	public Entertainment getEntertainmentById(String resortId, String parkId, String entertainmentId);
	public ArrayList<Entertainment> getAllEntertainment();
	public ArrayList<Entertainment> getAllEntertainment(String entertainmentName, String resortName, String parkName);
	
	public ArrayList<HotelDining> getHotelDining(String resortId, String hotelId);
	public HotelDining getHotelDiningById(String resortId, String hotelId, String diningId);
	public ArrayList<HotelDining> getHotelDiningByLocation(double longitude, double latitude,double radius);
	
	public ArrayList<LocatableResource> getDiningByLocation(double longitude, double latitude,double radius);
	public ArrayList<LocatableResource> getAllDining();
	public ArrayList<LocatableResource> getAllDining(String diningName, String resortName, String hotelName, String parkName);
	
	public ArrayList<CharacterMeet> getCharacters(String resortId);
	public CharacterMeet getCharacterById(String resortId, String characterId);
	public ArrayList<CharacterMeet> getAllCharacters();
	public ArrayList<CharacterMeet> getAllCharacters(String characterName, String resortName);
	
	public ArrayList<Snack> getSnacks(String resortId);
	public Snack getSnackById(String resortId, String snackId);
	public ArrayList<Snack> getAllSnacks();
	public ArrayList<Snack> getAllSnacks(String snackName, String resortName);
	
}
