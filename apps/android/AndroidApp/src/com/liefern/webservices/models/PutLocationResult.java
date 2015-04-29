package com.liefern.webservices.models;

import org.json.JSONObject;

import android.util.Log;

public class PutLocationResult extends WebServiceModel {

	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
		
		Log.d("Json object in result", jsonObject.toString());

	}

}
