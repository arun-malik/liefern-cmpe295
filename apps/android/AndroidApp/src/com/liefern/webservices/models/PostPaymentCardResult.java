package com.liefern.webservices.models;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.liefern.constants.Constants;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Payments;

public class PostPaymentCardResult extends WebServiceModel {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressLint("NewApi")
	@Override
	public void parseJSON(JSONObject row) throws Exception {

		parse(row);

		Payments card = new Payments();
		card.setPaymentId((row.has(Constants.PAYMENT_ID) == true &&  !row.get(Constants.PAYMENT_ID).equals(null)) ? row.getInt(Constants.PAYMENT_ID) : -1);
		card.setUserId((row.has(Constants.USEER_ID) == true &&  !row.get(Constants.USEER_ID).equals(null)) ? row.getInt(Constants.USEER_ID) : -1);
		card.setCreditCardNo((row.has(Constants.CREDIT_CARD_NO) == true &&  !row.get(Constants.CREDIT_CARD_NO).equals(null)) ? row.getLong(Constants.CREDIT_CARD_NO) : -1);
		card.setExpiryMonth((row.has(Constants.EXPIRY_MONTH) == true &&  !row.get(Constants.EXPIRY_MONTH).equals(null)) ? row.getInt(Constants.EXPIRY_MONTH) : -1);
		card.setExpiryYear((row.has(Constants.EXPIRY_YEAR) == true &&  !row.get(Constants.EXPIRY_YEAR).equals(null)) ? row.getInt(Constants.EXPIRY_YEAR) : -1);
		card.setCvv((row.has(Constants.CVV) == true &&  !row.get(Constants.CVV).equals(null)) ? row.getInt(Constants.CVV) : -1);
		card.setNameOnCard((row.has(Constants.NAME_ON_CARD) == true &&  !row.get(Constants.NAME_ON_CARD).equals(null)) ? row.getString(Constants.NAME_ON_CARD) : null);
		LiefernRepository.getInstance().addPaymentCard(card);

	}
}
