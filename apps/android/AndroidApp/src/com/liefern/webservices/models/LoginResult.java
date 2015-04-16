package com.liefern.webservices.models;

import org.json.JSONObject;

import android.util.Log;

import com.liefern.constants.Constants;
import com.liefern.models.LiefernRepository;
import com.liefern.models.User;

public class LoginResult extends WebServiceModel {

	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
		parse(jsonObject);
		
		JSONObject responseJson = (JSONObject) jsonObject.get("response");		
		LiefernRepository.getInstance().createUser(responseJson);
		
		Log.d("Arun Malik","UserId : "+ LiefernRepository.getInstance().getLoggedInUser().getUserId());
		Log.d("Arun Malik","User Object : "+ LiefernRepository.getInstance().getLoggedInUser().toJSON());
	}

}
