package com.liefern.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;

public class Address {
	
	  private int addressId = -1;
	  private String address1;
	  private String address2;
	  private String city;
	  private String state;
	  private String country;
	  private int zip;
	  private int user;
	  private Boolean home;
	  private Date createdDate; 
	  private Date modifiedDate;
	  
	/**
	 * @return the addressId
	 */
	public int getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the zip
	 */
	public int getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	/**
	 * @return the user
	 */
	public int getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(int user) {
		this.user = user;
	}
	/**
	 * @return the home
	 */
	public Boolean getHome() {
		return home;
	}
	/**
	 * @param home the home to set
	 */
	public void setHome(Boolean home) {
		this.home = home;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if(this.getAddressId() != -1)
			jsonObject.put(Constants.ADDRESS_ID, this.getAddressId());
		
		jsonObject.put(Constants.ADDRESS1, this.getAddress1());
		jsonObject.put(Constants.ADDRESS2, this.getAddress2());
		jsonObject.put(Constants.CITY, this.getCity());
		jsonObject.put(Constants.STATE, this.getState());
		jsonObject.put(Constants.COUNTRY, this.getCountry());
		jsonObject.put(Constants.ZIP, this.getZip());
		jsonObject.put(Constants.HOME, this.getHome());
		
		return jsonObject;
	}
	 

}
