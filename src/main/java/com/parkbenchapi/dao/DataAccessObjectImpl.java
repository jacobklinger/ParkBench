package com.parkbenchapi.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.parkbenchapi.model.Attraction;
import com.parkbenchapi.model.Character;
import com.parkbenchapi.model.Entity;
import com.parkbenchapi.model.Hotel;
import com.parkbenchapi.model.LocatableEntity;

@Service
public class DataAccessObjectImpl implements DataAccessObject {

	@Override
	public ArrayList<LocatableEntity> getParkByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getParkByLocation(double longitude, double latititude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getAllParks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hotel> getHotels(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hotel> gethotelByResortArea(String resortArea) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hotel> getHotelByLocation(double longitude, double latititude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Hotel> getAllHotels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByPark(String parkName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByLand(String landName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Attraction> getAttractionsByLocation(double longitude, double latititude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Attraction> getAllAttractions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getDiningByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getDiningByPark(String parkName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getDiningByLand(String landName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getDiningByLocation(double longitude, double latititude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getAllDining() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entity> getSnacksByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entity> getAllSnacks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Character> getCharactersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Character> getAllCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getEntertainmentByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getEntertainmentByPark(String parkName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getEntertainmentByLand(String landName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getEntertainmentByLocation(double longitude, double latititude, double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<LocatableEntity> getAllEntertainment() {
		// TODO Auto-generated method stub
		return null;
	}

}
