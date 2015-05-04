package com.liefern.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;

public class Geos {
	private int userId = -1;
	private int geoId = -1;
	
	private float lat = 0.0f;
	private float lng = 0.0f;
	
	private float radius;
	
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if(this.getUserId()!=-1)
			jsonObject.put(Constants.USEER_ID, this.getUserId());
		jsonObject.put(Constants.RADIUS, this.getRadius());
		jsonObject.put(Constants.LATITUDE, this.getLat());
		jsonObject.put(Constants.LONGITUDE, this.getLng());
		jsonObject.put(Constants.CREATE_DATE, this.getCreatedDate());
		
		return jsonObject;
	}




	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public int getGeoId() {
		return geoId;
	}




	public void setGeoId(int geoId) {
		this.geoId = geoId;
	}




	public float getLat() {
		return lat;
	}




	public void setLat(float lat) {
		this.lat = lat;
	}




	public float getLng() {
		return lng;
	}




	public void setLng(float lng) {
		this.lng = lng;
	}




	public float getRadius() {
		return radius;
	}




	public void setRadius(float radius) {
		this.radius = radius;
	}




	public Date getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}




	public Date getModifiedDate() {
		return modifiedDate;
	}




	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
