package com.liefern.webservices.models;

import org.json.JSONObject;

public class LogoutResult extends WebServiceModel {

	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
		parse(jsonObject);
	}

}
