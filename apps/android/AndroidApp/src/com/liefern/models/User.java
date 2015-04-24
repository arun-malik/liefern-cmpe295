package com.liefern.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;


public class User {
	private int userId;
	private String name;
	private String emailId;
	private String mobile;
//	private String creditCard;
	private String password;
	private Boolean active;
	private float avgRating = 0.0f;
	private String sessiontoken;
	private Date createdDate;
	private Date modifiedDate;
	private Address address = new Address();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
//	public String getCreditCard() {
//		return creditCard;
//	}
//	public void setCreditCard(String creditCard) {
//		this.creditCard = creditCard;
//	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.USEER_ID, this.getUserId());
		jsonObject.put(Constants.EMAIL, this.getEmailId());
		jsonObject.put(Constants.PASSWORD, this.getPassword());
		jsonObject.put(Constants.MOBILE, this.getMobile());
		return jsonObject;
	}
	/**
	 * @return the sessiontoken
	 */
	public String getSessiontoken() {
		return sessiontoken;
	}
	/**
	 * @param sessiontoken the sessiontoken to set
	 */
	public void setSessiontoken(String sessiontoken) {
		this.sessiontoken = sessiontoken;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Object toJSONWithAddress() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject = this.toJSON();
		jsonObject.put(Constants.NAME, this.name);
		jsonObject.put(Constants.ADDRESS, this.address.toJSON());
		return jsonObject;
	}
	
}
