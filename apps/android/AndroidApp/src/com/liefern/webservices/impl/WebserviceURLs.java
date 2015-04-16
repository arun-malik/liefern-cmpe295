package com.liefern.webservices.impl;

public interface WebserviceURLs {
//	String BASE_URL = "http://liefern.cloudapp.net:3000/api/Liefernusers/";
	String BASE_URL = "http://10.0.0.14:3000/api/";
//	String BASE_URL = "http://172.22.29.32:3000/api/";
	String LOGIN_URL		= BASE_URL + "Liefernusers/login";
	String SIGN_UP_URL		= BASE_URL + "Liefernusers";
	String LOG_OUT_URL		= BASE_URL + "Liefernusers/logout";
//	String REQUEST_ORDER_RESULT = BASE_URL + "Orders?filter={\"where\":{\"customerid\":";
	String REQUEST_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation&filter[include]=packages&filter[include]=customer&filter[include]=traveler&filter[where][customerid]=";
}
