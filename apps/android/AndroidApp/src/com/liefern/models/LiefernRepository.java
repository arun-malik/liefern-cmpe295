package com.liefern.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;


public final class LiefernRepository {
	
	private static final Object obj = new Object();
	private static LiefernRepository instance;
	
	private User loggedInUser;
	private String authToken;
	private List<Order> requestOrderList = new ArrayList<Order>();
	private Order builtOrder = new Order(); 
	private List<Payments> paymentCardList = new ArrayList<Payments>();
	private Payments newCard;
	private LiefernRepository() {}
	
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
	
	public void clearOrderList(){
		requestOrderList.clear();
	}
	
	
	public List<Order> getRequestOrderList(){
		return requestOrderList;
	}
	
	public void createUser(JSONObject responseJson){
		this.loggedInUser = new User();
		
		this.loggedInUser.setActive(Integer.parseInt(responseJson.optString(Constants.ACTIVE)) == 1 ? true : false); 
		this.loggedInUser.setAvgRating((responseJson.optString(Constants.AVG_RATING) == null ||responseJson.optString(Constants.AVG_RATING) == "") ? 0 
				: responseJson.optInt(Constants.AVG_RATING));
		this.loggedInUser.setEmailId(responseJson.optString(Constants.EMAIL));
		this.loggedInUser.setMobile(responseJson.optString(Constants.MOBILE));
		this.loggedInUser.setName(responseJson.optString(Constants.NAME));
		this.loggedInUser.setUserId(responseJson.optInt(Constants.USEER_ID));
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
