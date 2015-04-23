package com.liefern.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.liefern.constants.Constants;

public class Payments {

	 private int paymentId = -1;
	 private int userId = -1;
	 private int creditCardNo = -1;
	 private int cvv = -1 ;
	 private int expiryMonth = -1;
	 private int expiryYear = -1;
	 private String NameOnCard;
	 private Date createdDate;
	 
	 
	
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if(this.getPaymentId() != -1)
			jsonObject.put(Constants.PAYMENT_ID, this.getPaymentId());
		jsonObject.put(Constants.USEER_ID, this.getUserId());
		jsonObject.put(Constants.CREDIT_CARD_NO, this.getCreditCardNo());
		jsonObject.put(Constants.CVV, this.getCvv());
		jsonObject.put(Constants.EXPIRY_MONTH, this.getExpiryMonth());
		jsonObject.put(Constants.EXPIRY_YEAR, this.getExpiryYear());
		jsonObject.put(Constants.NAME_ON_CARD, this.getNameOnCard());
		jsonObject.put(Constants.CREATE_DATE, this.getCreatedDate());
		return jsonObject;
	}




	public int getPaymentId() {
		return paymentId;
	}




	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}




	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public int getCreditCardNo() {
		return creditCardNo;
	}




	public void setCreditCardNo(int creditCardNo) {
		this.creditCardNo = creditCardNo;
	}




	public int getCvv() {
		return cvv;
	}




	public void setCvv(int cvv) {
		this.cvv = cvv;
	}




	public int getExpiryMonth() {
		return expiryMonth;
	}




	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}




	public int getExpiryYear() {
		return expiryYear;
	}




	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}




	public String getNameOnCard() {
		return NameOnCard;
	}




	public void setNameOnCard(String nameOnCard) {
		NameOnCard = nameOnCard;
	}




	public Date getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	 
}
