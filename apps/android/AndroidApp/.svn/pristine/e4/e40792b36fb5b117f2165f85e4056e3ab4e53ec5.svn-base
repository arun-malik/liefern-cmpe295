package com.liefern.webservices.models;

import org.json.JSONObject;

public class SignUpResult extends LoginResult {

	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
		super.parseJSON(jsonObject);
		if(isError()) {
			if(message.contains("ER_DUP_ENTRY")) {
				message = "User already exists";
			}
		}
	}

}

