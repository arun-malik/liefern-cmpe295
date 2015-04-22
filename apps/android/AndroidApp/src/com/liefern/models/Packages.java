package com.liefern.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;

public class Packages {

	 private int packageId = -1;
	 private int orderId;
	 private int weight;
	 private String size;
	 private String description;
	 private String content;
	 private Boolean isFragile;
	 
	 
	/**
	 * @return the packageId
	 */
	public int getPackageId() {
		return packageId;
	}
	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
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
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the isFragile
	 */
	public Boolean getIsFragile() {
		return isFragile;
	}
	/**
	 * @param isFragile the isFragile to set
	 */
	public void setIsFragile(Boolean isFragile) {
		this.isFragile = isFragile;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if(this.getPackageId() != -1)
			jsonObject.put(Constants.PACKAGE_ID, this.getPackageId());
		jsonObject.put(Constants.ORDER_ID, this.getOrderId());
		jsonObject.put(Constants.WEIGHT, this.getWeight());
		jsonObject.put(Constants.SIZE, this.getSize());
		jsonObject.put(Constants.DESCRIPTION, this.getDescription());
		jsonObject.put(Constants.CONTENT, this.getContent());
		jsonObject.put(Constants.IS_FRAGILE, this.getIsFragile());
		
		return jsonObject;
	}
	 
}
