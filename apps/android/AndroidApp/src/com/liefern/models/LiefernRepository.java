package com.liefern.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.liefern.constants.Constants;


public final class LiefernRepository {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private static final Object obj = new Object();
	private static LiefernRepository instance;

	private User loggedInUser;
	private String authToken;
	private List<Order> requestOrderList = new ArrayList<Order>();
	private Order builtOrder = new Order(); 
	private List<Payments> paymentCardList = new ArrayList<Payments>();
	private Payments newCard;
	private LiefernRepository() {}
	private List<Geos> nearestUsers = new ArrayList<Geos>();

	public static LiefernRepository getInstance() {
		if(instance == null) {
			synchronized (obj) {
				if(instance == null) {
					instance = new LiefernRepository();
				}
			}
		}
		return instance;
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void clear() {
		authToken = null;
		instance = null;
	}

	public void addOrderToRequestList(Order order){
		requestOrderList.add(order);
	}

	public void addToNearestUserLocationList(Geos geo){
		nearestUsers.add(geo);
	}
	public void clearOrderList(){
		requestOrderList.clear();
	}
	
	public void clearNearestUsersLocationList(){
		nearestUsers.clear();
	}



	public List<Order> getRequestOrderList(){
		return requestOrderList;
	}

	public void createUser(JSONObject responseJson){
		if(		this.loggedInUser == null){
			this.loggedInUser = new User();
			this.loggedInUser.setActive(Integer.parseInt(responseJson.optString(Constants.ACTIVE)) == 1 ? true : false); 
			this.loggedInUser.setAvgRating((responseJson.optString(Constants.AVG_RATING) == null ||responseJson.optString(Constants.AVG_RATING) == "") ? 0 
					: responseJson.optInt(Constants.AVG_RATING));
			this.loggedInUser.setEmailId(responseJson.optString(Constants.EMAIL));
			this.loggedInUser.setMobile(responseJson.optString(Constants.MOBILE));
			this.loggedInUser.setUserId(responseJson.optInt(Constants.USEER_ID));
			this.loggedInUser.setSessiontoken(responseJson.optString(Constants.SESSION_TOKEN));
		}
		this.loggedInUser.setName(responseJson.optString(Constants.NAME));
		try {
			JSONObject add = responseJson.getJSONObject(Constants.ADDRESS);
			if(add!=null) {
				this.loggedInUser.getAddress().setAddress1(add.optString(Constants.ADDRESS1));
				this.loggedInUser.getAddress().setAddress2(add.optString(Constants.ADDRESS2));
				this.loggedInUser.getAddress().setCity(add.optString(Constants.CITY));
				this.loggedInUser.getAddress().setState(add.optString(Constants.STATE));
				this.loggedInUser.getAddress().setCountry(add.optString(Constants.COUNTRY));
				this.loggedInUser.getAddress().setZip(add.optInt(Constants.ZIP));
				this.loggedInUser.getAddress().setAddressId(add.optInt(Constants.ADDRESS_ID));
				this.loggedInUser.getAddress().setHome(add.optInt(Constants.HOME)==1);
				try {
					this.loggedInUser.getAddress().setCreatedDate(sdf.parse(add.optString(Constants.CREATE_DATE)));
					this.loggedInUser.getAddress().setModifiedDate(sdf.parse(add.optString(Constants.MODIFIED_DATE)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Order getBuiltOrder() {
		return builtOrder;
	}

	public void setBuiltOrder(Order builtOrder) {
		this.builtOrder = builtOrder;
	}

	public List<Payments> getPaymentCardList() {
		return paymentCardList;
	}

	public void setPaymentCardList(List<Payments> paymentCardList) {
		this.paymentCardList = paymentCardList;
	}

	public void addPaymentCard(Payments paymentCard) {
		this.paymentCardList.add(paymentCard);
	}

	public void clearPaymentCardList(){
		this.paymentCardList.clear();
	}

	public Payments getNewCard() {
		return newCard;
	}

	public void setNewCard(Payments newCard) {
		this.newCard = newCard;
	}

	public List<Geos> getNearestUsers() {
		return nearestUsers;
	}

	public void setNearestUsers(List<Geos> nearestUsers) {
		this.nearestUsers = nearestUsers;
	}


	//	public void createRequestOrderResult(JSONObject json){
	//		try {
	//			
	//			JSONObject responseJson = (JSONObject) json.get("response");
	//			
	//			
	//		} catch (JSONException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}	
	//		this.requestOrderList = new ArrayList<Order>();
	//	}

}
