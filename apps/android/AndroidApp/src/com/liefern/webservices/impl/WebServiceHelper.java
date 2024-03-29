package com.liefern.webservices.impl;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.liefern.constants.Constants;
import com.liefern.models.LiefernRepository;

import android.util.Log;

public final class WebServiceHelper {
	private static final String TAG =  "WebServiceHelper";
	private static final Object obj = new Object();

	/**
	 * Executes Http request
	 * @param oHttpRequestBase Request to execute
	 * @return JSON response object
	 * @throws Exception
	 */
	public static JSONObject executeRequest(HttpRequestBase oHttpRequestBase, int responseType) throws  Exception
	{
		synchronized (obj) {
			JSONObject oResponseJSON = null;
			try {
				if(oHttpRequestBase != null) {
					oHttpRequestBase.setHeader("Content-type", "application/json");
					if(LiefernRepository.getInstance().getLoggedInUser()!= null ){
						oHttpRequestBase.setHeader(Constants.TOKEN, LiefernRepository.getInstance().getLoggedInUser().getSessiontoken());
					}

					HttpResponse oHttpResponse = NetworkUtility.getInstance().getNewHttpClient().execute(oHttpRequestBase);

					if(oHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ||
							oHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR ||
							oHttpResponse.getStatusLine().getStatusCode() == 422 || oHttpResponse.getStatusLine().getStatusCode() == 204) {

						if(responseType == 0){ //JSON_OBJECT
							oResponseJSON = new JSONObject(EntityUtils.toString(oHttpResponse.getEntity()));
							return oResponseJSON;
						}

						if(responseType ==1){ //JSON_ARRAY
							JSONArray oResponseJSONArray = new JSONArray(EntityUtils.toString(oHttpResponse.getEntity()));
							oResponseJSON = new JSONObject();
							oResponseJSON.put(Constants.RESPONSE, oResponseJSONArray);
						}

						if(responseType ==2){ //Empty response
							oResponseJSON = new JSONObject();
							oResponseJSON.put(Constants.RESPONSE, "Delete Success");
						}


						return oResponseJSON;
					} else {
						Log.e(TAG, "Response ---> " + EntityUtils.toString(oHttpResponse.getEntity()));
						Log.e(TAG, "Incorrect Status code --> " + oHttpResponse.getStatusLine().getStatusCode());

						// ERROR!! Incorrect Http response code
						IOException ioe = new IOException(oHttpResponse.getStatusLine().getReasonPhrase());
						throw ioe;
					}
				}

			} catch(Exception e) {

				e.printStackTrace();
				throw e;

			}
		}
		return null;
	}
}
