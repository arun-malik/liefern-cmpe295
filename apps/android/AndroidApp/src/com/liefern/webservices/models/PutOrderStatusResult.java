/**
 * 
 */
package com.liefern.webservices.models;

import org.json.JSONObject;

/**
 * @author arun_malik
 *
 */
public class PutOrderStatusResult extends WebServiceModel {

	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {
	parse(jsonObject);
		
	}

}
