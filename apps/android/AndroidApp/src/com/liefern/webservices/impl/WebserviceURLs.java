package com.liefern.webservices.impl;

import java.net.URI;

public interface WebserviceURLs {
	String BASE_URL = "http://liefern.cloudapp.net:3000/api/";
	//String BASE_URL = "http://10.0.0.14:3000/api/";
//	String BASE_URL = "http://172.22.29.32:3000/api/";
	String LOGIN_URL		= BASE_URL + "Liefernusers/login";
	String SIGN_UP_URL		= BASE_URL + "Liefernusers";
	String LOG_OUT_URL		= BASE_URL + "Liefernusers/logout";
//	String REQUEST_ORDER_RESULT = BASE_URL + "Orders?filter={\"where\":{\"customerid\":";
	String REQUEST_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation"
			+ "&filter[include]=packages&filter[include]=customer&filter[include]=traveler"
			+ "&filter[where][orderStatus][inq]=0&filter[where][orderStatus][inq]=1"
			+ "&filter[where][customerid]=";
	String DELIVERY_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation&"
			+ "filter[include]=packages&filter[include]=customer&filter[include]=traveler&filter[where][travlerid]=";
	String RECEIPT_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation&"
			+ "filter[include]=packages&filter[include]=customer&filter[include]=traveler"
			+ "&filter[where][orderStatus][inq]=2&filter[where][orderStatus][inq]=3&filter[where][orderStatus][inq]=4&filter[where][orderStatus][inq]=5"
			+ "&filter[where][customerid]=";
	
	String REQUEST_PAYMENT_CARD_RESULT = BASE_URL + "payments/?filter[where][userid]=";
	
	String POST_ORDER_URL = BASE_URL + "Orders/saveAll/";
	
	String POST_PAYMENT_CARD_URL = BASE_URL + "payments";
	
	String UPDATE_PROFILE_URL = BASE_URL + "Liefernusers/profile?userid=";
}
