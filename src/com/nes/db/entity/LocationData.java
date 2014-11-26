package com.nes.db.entity;

public class LocationData {

	private int locationId;
	private String pinCode;
	private String place;
	private String province;
	private String state;
	private String country;
	private String countryCode; 
	private String cannonicalName;
	private double latitude;
	private double longitude;
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocationData() {
		// TODO Auto-generated constructor stub
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public String getProvince() {
		return province;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCannonicalName() {
		return cannonicalName;
	}

	public void setCannonicalName(String cannonicalName) {
		this.cannonicalName = cannonicalName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getLocationId() {
		return locationId;
	}
	
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
}
