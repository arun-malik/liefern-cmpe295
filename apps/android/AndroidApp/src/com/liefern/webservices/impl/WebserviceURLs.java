package com.liefern.webservices.impl;

import java.net.URI;

public interface WebserviceURLs {
	String BASE_URL = "http://liefern.cloudapp.net:3000/api/";
	String LOGIN_URL		= BASE_URL + "Liefernusers/login";
	String SIGN_UP_URL		= BASE_URL + "Liefernusers";
	String LOG_OUT_URL		= BASE_URL + "Liefernusers/logout";
	String REQUEST_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation"
			+ "&filter[include]=packages&filter[include]=customer&filter[include]=traveler"
			+ "&filter[where][orderStatus][inq]=0&filter[where][orderStatus][inq]=1"
			+ "&filter[where][customerid]=";
	
	String DELIVERY_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation"
			+ "&filter[where][orderStatus][inq]=1"
			+ "&filter[include]=packages&filter[include]=customer&filter[include]=traveler&filter[where][travlerid]=";
	
	
	String RECEIPT_ORDER_RESULT = BASE_URL + "Orders/?filter[include]=fromlocation&filter[include]=tolocation&"
			+ "filter[include]=packages&filter[include]=customer&filter[include]=traveler"
			+ "&filter[where][orderStatus][inq]=2&filter[where][orderStatus][inq]=3&filter[where][orderStatus][inq]=4&filter[where][orderStatus][inq]=5"
			+ "&filter[where][or][0][customerid]=%s&filter[where][or][1][travlerid]=%s";
	
	String REQUEST_PAYMENT_CARD_RESULT = BASE_URL + "payments/?filter[where][userid]=";
	
	String POST_ORDER_URL = BASE_URL + "Orders/saveAll/";
	
	String POST_PAYMENT_CARD_URL = BASE_URL + "payments";
	
	String UPDATE_PROFILE_URL = BASE_URL + "Liefernusers/profile?userid=";
	
	String TRAVELER_ORDER_RESULT = BASE_URL +  "Orders/?filter[include]=fromlocation&filter[include]=tolocation&filter[include]=packages"
			+ "&filter[include]=customer&filter[include]=traveler"
			+ "&filter[where][orderStatus]=0&filter[where][customerid][neq]=";
	
	String UPDATE_ORDER_STATUS = BASE_URL + "Orders/";
	
	String POST_USER_GEO_LOCATION = BASE_URL + "Geos/upsert";
	
	String GET_USER_GEO_LOCATION = BASE_URL + "Geos/";
	

}
