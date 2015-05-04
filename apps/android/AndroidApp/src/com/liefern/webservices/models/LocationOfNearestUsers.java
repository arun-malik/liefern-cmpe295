package com.liefern.webservices.models;

import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.liefern.constants.Constants;
import com.liefern.models.Geos;
import com.liefern.models.LiefernRepository;

public class LocationOfNearestUsers extends WebServiceModel {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressLint("NewApi")
	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
		
		LiefernRepository.getInstance().clearNearestUsersLocationList();
		
		parse(jsonObject);
		JSONArray array = jsonObject.getJSONArray(Constants.RESPONSE);
		
		for (int i = 0; i < array.length(); i++) {
		    JSONObject row = array.getJSONObject(i);
		    Geos geo = new Geos();
		    
		    //geo.setCreatedDate((row.has(Constants.CREATE_DATE) == true && !row.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(row.getString(Constants.CREATE_DATE)) : null);
		    geo.setUserId((row.has(Constants.USEER_ID) == true &&  !row.get(Constants.USEER_ID).equals(null)) ? row.getInt(Constants.USEER_ID) : -1);
		    geo.setLat((row.has(Constants.LATITUDE) == true && !row.get(Constants.LATITUDE).equals(null)) ? Float.parseFloat(row.getString(Constants.LATITUDE)) : 0f);
		    geo.setLng((row.has(Constants.LONGITUDE) == true && !row.get(Constants.LONGITUDE).equals(null)) ? Float.parseFloat(row.getString(Constants.LONGITUDE)) : 0f);
		    LiefernRepository.getInstance().addToNearestUserLocationList(geo);
		}
	}
}
