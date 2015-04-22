package com.liefern.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;

public class Order {

	private int orderId;
	private int orderType;
	private int orderStatus;
	private int fromLoc;
	private int toLoc;
	private int customerId;
	private int travlerId = -1;
	private int distance;
	private int suggestAmount;
	private int customerAmount;
	private int timeToLive;
	private Date createdDate;
	private Date modifiedDate;
	private Address fromlocation;
	private Address tolocation;
	private User customer;
	private User traveler;
	private List<Packages> packages;
	
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the orderType
	 */
	public int getOrderType() {
		return orderType;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	/**
	 * @return the orderStatus
	 */
	public int getOrderStatus() {
		return orderStatus;
	}
	/**
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * @return the fromLoc
	 */
	public int getFromLoc() {
		return fromLoc;
	}
	/**
	 * @param fromLoc the fromLoc to set
	 */
	public void setFromLoc(int fromLoc) {
		this.fromLoc = fromLoc;
	}
	/**
	 * @return the toLoc
	 */
	public int getToLoc() {
		return toLoc;
	}
	/**
	 * @param toLoc the toLoc to set
	 */
	public void setToLoc(int toLoc) {
		this.toLoc = toLoc;
	}
	/**
	 * @return the customerId
	 */
	public int getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the travlerId
	 */
	public int getTravlerId() {
		return travlerId;
	}
	/**
	 * @param travlerId the travlerId to set
	 */
	public void setTravlerId(int travlerId) {
		this.travlerId = travlerId;
	}
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * @return the suggestAmount
	 */
	public int getSuggestAmount() {
		return suggestAmount;
	}
	/**
	 * @param suggestAmount the suggestAmount to set
	 */
	public void setSuggestAmount(int suggestAmount) {
		this.suggestAmount = suggestAmount;
	}
	/**
	 * @return the customerAmount
	 */
	public int getCustomerAmount() {
		return customerAmount;
	}
	/**
	 * @param customerAmount the customerAmount to set
	 */
	public void setCustomerAmount(int customerAmount) {
		this.customerAmount = customerAmount;
	}
	/**
	 * @return the timeToLive
	 */
	public int getTimeToLive() {
		return timeToLive;
	}
	/**
	 * @param timeToLive the timeToLive to set
	 */
	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put(Constants.ORDER_ID, this.getOrderId());
		jsonObject.put(Constants.ORDER_TYPE, this.getOrderType());
		jsonObject.put(Constants.ORDER_STATUS, this.getOrderStatus());
		jsonObject.put(Constants.FROM_LOC, this.getFromLoc());
		jsonObject.put(Constants.TO_LOC, this.getToLoc());
		jsonObject.put(Constants.DISTANCE, this.getDistance());
		jsonObject.put(Constants.SUGGEST_AMOUNT, this.getSuggestAmount());
		jsonObject.put(Constants.CUSTOMER_AMOUNT, this.getCustomerAmount());
		jsonObject.put(Constants.CUSTOMER_ID, this.getCustomerId());
		if(this.getTravlerId() != -1)
			jsonObject.put(Constants.TRAVLER_ID, this.getTravlerId());
		jsonObject.put(Constants.TIME_TO_LIVE, this.getTimeToLive());
		jsonObject.put(Constants.CREATE_DATE, this.getCreatedDate());
		jsonObject.put(Constants.MODIFIED_DATE, this.getModifiedDate());
		jsonObject.put(Constants.FROM_LOCATION_OBJECT, this.getFromlocation().toJSON());
		jsonObject.put(Constants.TO_LOCATION_OBJECT, this.getTolocation().toJSON());
		
		List<Packages> packages = this.getPackages();
		JSONArray packageArray = new JSONArray();
		for(Packages p: packages){
			packageArray.put(p.toJSON());		
		}
		jsonObject.put(Constants.PACKAGES_ARRAY, packageArray);
		return jsonObject;
	}
	/**
	 * @return the fromlocation
	 */
	public Address getFromlocation() {
		return fromlocation;
	}
	/**
	 * @param fromlocation the fromlocation to set
	 */
	public void setFromlocation(Address fromlocation) {
		this.fromlocation = fromlocation;
	}
	/**
	 * @return the tolocation
	 */
	public Address getTolocation() {
		return tolocation;
	}
	/**
	 * @param tolocation the tolocation to set
	 */
	public void setTolocation(Address tolocation) {
		this.tolocation = tolocation;
	}
	/**
	 * @return the customer
	 */
	public User getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	/**
	 * @return the traveler
	 */
	public User getTraveler() {
		return traveler;
	}
	/**
	 * @param traveler the traveler to set
	 */
	public void setTraveler(User traveler) {
		this.traveler = traveler;
	}
	/**
	 * @return the packages
	 */
	public List<Packages> getPackages() {
		return packages;
	}
	/**
	 * @param packages the packages to set
	 */
	public void setPackages(List<Packages> packages) {
		this.packages = packages;
	}
	
	public void addPackage(Packages packages) {
		if(this.packages == null) {
			this.packages = new ArrayList<Packages>();
		}
		this.packages.add(packages);
	}
	
}
