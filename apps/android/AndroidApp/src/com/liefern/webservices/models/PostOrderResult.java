package com.liefern.webservices.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.liefern.constants.Constants;
import com.liefern.models.Address;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.models.Packages;
import com.liefern.models.User;

public class PostOrderResult extends WebServiceModel {

	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

	@SuppressLint("NewApi")
	@Override
	public void parseJSON(JSONObject jsonObject) throws Exception {

		LiefernRepository.getInstance().clearOrderList();

		parse(jsonObject);
		JSONObject row = jsonObject.getJSONObject(Constants.RESPONSE);

		Order ord = new Order();
		List<Packages> packageList = new ArrayList<Packages>();

		//ord.setCreatedDate((row.has(Constants.CREATE_DATE) == true && !row.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(row.getString(Constants.CREATE_DATE)) : null);
		ord.setCustomerAmount((row.has(Constants.CUSTOMER_AMOUNT) == true && !row.get(Constants.CUSTOMER_AMOUNT).equals(null)) ? row.getInt(Constants.CUSTOMER_AMOUNT) : 0);
		ord.setCustomerId((row.has(Constants.CUSTOMER_ID) == true &&  !row.get(Constants.CUSTOMER_ID).equals(null)) ? row.getInt(Constants.CUSTOMER_ID) : 0);
		ord.setDistance((row.has(Constants.DISTANCE) == true && !row.get(Constants.DISTANCE).equals(null)) ? row.getInt(Constants.DISTANCE) : 0);
		ord.setFromLoc((row.has(Constants.FROM_LOC) == true && !row.get(Constants.FROM_LOC).equals(null)) ? row.getInt(Constants.FROM_LOC) : 0);
		//ord.setModifiedDate((row.has(Constants.MODIFIED_DATE) == true && !row.get(Constants.MODIFIED_DATE).equals(null)) ? sdf.parse(row.getString(Constants.MODIFIED_DATE)) : null);
		ord.setOrderId((row.has(Constants.ORDER_ID) == true && !row.get(Constants.ORDER_ID).equals(null)) ? row.getInt(Constants.ORDER_ID) : 0);
		ord.setOrderStatus ((row.has(Constants.ORDER_STATUS) == true && !row.get(Constants.ORDER_STATUS).equals(null)) ? row.getInt(Constants.ORDER_STATUS) : 0);
		ord.setOrderType((row.has(Constants.ORDER_TYPE) == true && !row.get(Constants.ORDER_TYPE).equals(null)) ? row.getInt(Constants.ORDER_TYPE) : 0);
		ord.setSuggestAmount((row.has(Constants.SUGGEST_AMOUNT) == true && !row.get(Constants.SUGGEST_AMOUNT).equals(null)) ? row.getInt(Constants.SUGGEST_AMOUNT) : 0);
		ord.setTimeToLive((row.has(Constants.TIME_TO_LIVE) == true && !row.get(Constants.TIME_TO_LIVE).equals(null)) ? row.getInt(Constants.TIME_TO_LIVE) : -100);
		ord.setToLoc((row.has(Constants.TO_LOC) == true && !row.get(Constants.TO_LOC).equals(null)) ? row.getInt(Constants.TO_LOC) : 0);

		if(row.has(Constants.TRAVLER_ID) && !row.get(Constants.TRAVLER_ID).equals(null)){
			ord.setTravlerId(row.getInt(Constants.TRAVLER_ID));
		}

		if(row.has(Constants.PACKAGES_ARRAY)){
			JSONArray packages = row.getJSONArray(Constants.PACKAGES_ARRAY);

			if(packages.length() !=0){
				for (int j = 0; j < packages.length(); j++) {
					JSONObject pack = packages.getJSONObject(j);

					Packages packObject = new Packages();

					packObject.setPackageId((pack.has(Constants.TO_LOC) == true && !pack.get(Constants.TO_LOC).equals(null)) ? pack.getInt(Constants.TO_LOC) : 0);
					packObject.setContent((pack.has(Constants.CONTENT) == true && !pack.get(Constants.CONTENT).equals(null)) ? pack.getString(Constants.CONTENT) : null);
					packObject.setDescription((pack.has(Constants.DESCRIPTION) == true && !pack.get(Constants.DESCRIPTION).equals(null)) ? pack.getString(Constants.DESCRIPTION) : null);
					packObject.setIsFragile((pack.has(Constants.IS_FRAGILE) == true && !pack.get(Constants.IS_FRAGILE).equals(null)) 
							? pack.getInt(Constants.IS_FRAGILE) ==1 ? true : false : false);
					packObject.setOrderId((pack.has(Constants.ORDER_ID) == true && !pack.get(Constants.ORDER_ID).equals(null)) ? pack.getInt(Constants.ORDER_ID) : 0);
					packObject.setSize((pack.has(Constants.SIZE) == true && !pack.get(Constants.SIZE).equals(null)) ? pack.getString(Constants.SIZE) : null);
					packObject.setWeight((pack.has(Constants.WEIGHT) == true && !pack.get(Constants.WEIGHT).equals(null)) ? pack.getInt(Constants.WEIGHT) : 0);

					packageList.add(packObject);
				}
			}
		}

		if(packageList.size() > 0){
			ord.setPackages(packageList);
		}

		if(row.has(Constants.FROM_LOCATION_OBJECT) && !row.get(Constants.FROM_LOCATION_OBJECT).equals(null)){
			JSONObject fromLocationObject = (JSONObject) row.get(Constants.FROM_LOCATION_OBJECT);

			Address fromAddress = new Address();
			fromAddress.setAddress1((fromLocationObject.has(Constants.ADDRESS1) == true && !fromLocationObject.get(Constants.ADDRESS1).equals(null)) ? fromLocationObject.getString(Constants.ADDRESS1) : null);
			fromAddress.setAddress2((fromLocationObject.has(Constants.ADDRESS2) == true && !fromLocationObject.get(Constants.ADDRESS2).equals(null)) ? fromLocationObject.getString(Constants.ADDRESS2) : null);
			fromAddress.setAddressId((fromLocationObject.has(Constants.ADDRESS_ID) == true && !fromLocationObject.get(Constants.ADDRESS_ID).equals(null)) ? fromLocationObject.getInt(Constants.ADDRESS_ID) : -100);
			fromAddress.setCity((fromLocationObject.has(Constants.CITY) == true && !fromLocationObject.get(Constants.CITY).equals(null)) ? fromLocationObject.getString(Constants.CITY) : null);
			fromAddress.setCountry((fromLocationObject.has(Constants.COUNTRY) == true && !fromLocationObject.get(Constants.COUNTRY).equals(null)) ? fromLocationObject.getString(Constants.COUNTRY) : null);
			//fromAddress.setCreatedDate((fromLocationObject.has(Constants.CREATE_DATE) == true && !fromLocationObject.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(fromLocationObject.getString(Constants.CREATE_DATE)) : null);
			fromAddress.setHome((fromLocationObject.has(Constants.HOME) == true && !fromLocationObject.get(Constants.HOME).equals(null)) 
					? fromLocationObject.getInt(Constants.HOME) ==1 ? true : false : false);
			//fromAddress.setModifiedDate((fromLocationObject.has(Constants.MODIFIED_DATE) == true && !fromLocationObject.get(Constants.MODIFIED_DATE).equals(null)) ? sdf.parse(fromLocationObject.getString(Constants.MODIFIED_DATE)) : null);
			fromAddress.setState((fromLocationObject.has(Constants.STATE) == true && !fromLocationObject.get(Constants.STATE).equals(null)) ? fromLocationObject.getString(Constants.STATE) : null);
			fromAddress.setZip((fromLocationObject.has(Constants.ZIP) == true && !fromLocationObject.get(Constants.ZIP).equals(null)) ? fromLocationObject.getInt(Constants.ZIP) : 00000);

			ord.setFromlocation(fromAddress);
		}

		if(row.has(Constants.TO_LOCATION_OBJECT) && !row.get(Constants.TO_LOCATION_OBJECT).equals(null)){

			JSONObject toLocationObject = (JSONObject) row.get(Constants.TO_LOCATION_OBJECT);

			Address toAddress = new Address();
			toAddress.setAddress1((toLocationObject.has(Constants.ADDRESS1) == true && !toLocationObject.get(Constants.ADDRESS1).equals(null)) ? toLocationObject.getString(Constants.ADDRESS1) : null);
			toAddress.setAddress2((toLocationObject.has(Constants.ADDRESS2) == true && !toLocationObject.get(Constants.ADDRESS2).equals(null)) ? toLocationObject.getString(Constants.ADDRESS2) : null);
			toAddress.setAddressId((toLocationObject.has(Constants.ADDRESS_ID) == true && !toLocationObject.get(Constants.ADDRESS_ID).equals(null)) ? toLocationObject.getInt(Constants.ADDRESS_ID) : -100);
			toAddress.setCity((toLocationObject.has(Constants.CITY) == true && !toLocationObject.get(Constants.CITY).equals(null)) ? toLocationObject.getString(Constants.CITY) : null);
			toAddress.setCountry((toLocationObject.has(Constants.COUNTRY) == true && !toLocationObject.get(Constants.COUNTRY).equals(null)) ? toLocationObject.getString(Constants.COUNTRY) : null);
			//toAddress.setCreatedDate((toLocationObject.has(Constants.CREATE_DATE) == true && !toLocationObject.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(toLocationObject.getString(Constants.CREATE_DATE)) : null);
			toAddress.setHome((toLocationObject.has(Constants.HOME) == true && !toLocationObject.get(Constants.HOME).equals(null)) 
					? toLocationObject.getInt(Constants.HOME) ==1 ? true : false : false);
			//toAddress.setModifiedDate((toLocationObject.has(Constants.MODIFIED_DATE) == true && !toLocationObject.get(Constants.MODIFIED_DATE).equals(null)) ? sdf.parse(toLocationObject.getString(Constants.MODIFIED_DATE)) : null);
			toAddress.setState((toLocationObject.has(Constants.STATE) == true && !toLocationObject.get(Constants.STATE).equals(null)) ? toLocationObject.getString(Constants.STATE) : null);
			toAddress.setZip((toLocationObject.has(Constants.ZIP) == true && !toLocationObject.get(Constants.ZIP).equals(null)) ? toLocationObject.getInt(Constants.ZIP) : 00000);

			ord.setTolocation(toAddress);
		}

		if(row.has(Constants.CUSTOMER_OBJECT) && !row.get(Constants.CUSTOMER_OBJECT).equals(null)){

			JSONObject customerObject = (JSONObject) row.get(Constants.CUSTOMER_OBJECT);

			User customer = new User();
			customer.setActive((customerObject.has(Constants.ACTIVE) == true && !customerObject.get(Constants.ACTIVE).equals(null)) 
					? customerObject.getInt(Constants.ACTIVE) ==1 ? true : false : false);
			customer.setAvgRating((customerObject.has(Constants.AVG_RATING) == true && !customerObject.get(Constants.AVG_RATING).equals(null)) ? customerObject.getLong(Constants.AVG_RATING) : 0);
			//customer.setCreatedDate((customerObject.has(Constants.CREATE_DATE) == true && !customerObject.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(customerObject.getString(Constants.CREATE_DATE)) : null);
			customer.setEmailId((customerObject.has(Constants.EMAIL) == true && !customerObject.get(Constants.EMAIL).equals(null)) ? customerObject.getString(Constants.EMAIL) : null);
			customer.setMobile((customerObject.has(Constants.MOBILE) == true && !customerObject.get(Constants.MOBILE).equals(null)) ? customerObject.getString(Constants.MOBILE) : null);
			//customer.setModifiedDate((customerObject.has(Constants.MODIFIED_DATE) == true && !customerObject.get(Constants.MODIFIED_DATE).equals(null)) ? sdf.parse(customerObject.getString(Constants.MODIFIED_DATE)) : null);
			customer.setName((customerObject.has(Constants.NAME) == true && !customerObject.get(Constants.NAME).equals(null)) ? customerObject.getString(Constants.NAME) : null);
			customer.setSessiontoken((customerObject.has(Constants.SESSION_TOKEN) == true && !customerObject.get(Constants.SESSION_TOKEN).equals(null)) 
					? customerObject.getString(Constants.SESSION_TOKEN) : null);
			customer.setUserId((customerObject.has(Constants.USEER_ID) == true && !customerObject.get(Constants.USEER_ID).equals(null)) ? customerObject.getInt(Constants.USEER_ID) : -100);

			ord.setCustomer(customer);
		}

		if(row.has(Constants.TRAVELER_OBJECT) && !row.get(Constants.TRAVELER_OBJECT).equals(null)){

			JSONObject travelerObject = (JSONObject) row.get(Constants.TRAVELER_OBJECT);

			User traveler = new User();
			traveler.setActive((travelerObject.has(Constants.ACTIVE) == true && !travelerObject.get(Constants.ACTIVE).equals(null)) 
					? travelerObject.getInt(Constants.ACTIVE) ==1 ? true : false : false);
			traveler.setAvgRating((travelerObject.has(Constants.AVG_RATING) == true && !travelerObject.get(Constants.AVG_RATING).equals(null)) ? travelerObject.getLong(Constants.AVG_RATING) : 0);
			//traveler.setCreatedDate((travelerObject.has(Constants.CREATE_DATE) == true && !travelerObject.get(Constants.CREATE_DATE).equals(null)) ? sdf.parse(travelerObject.getString(Constants.CREATE_DATE)) : null);
			traveler.setEmailId((travelerObject.has(Constants.EMAIL) == true && !travelerObject.get(Constants.EMAIL).equals(null)) ? travelerObject.getString(Constants.EMAIL) : null);
			traveler.setMobile((travelerObject.has(Constants.MOBILE) == true && !travelerObject.get(Constants.MOBILE).equals(null)) ? travelerObject.getString(Constants.MOBILE) : null);
			//traveler.setModifiedDate((travelerObject.has(Constants.MODIFIED_DATE) == true && !travelerObject.get(Constants.MODIFIED_DATE).equals(null)) ? sdf.parse(travelerObject.getString(Constants.MODIFIED_DATE)) : null);
			traveler.setName((travelerObject.has(Constants.NAME) == true && !travelerObject.get(Constants.NAME).equals(null)) ? travelerObject.getString(Constants.NAME) : null);
			traveler.setSessiontoken((travelerObject.has(Constants.SESSION_TOKEN) == true && !travelerObject.get(Constants.SESSION_TOKEN).equals(null)) 
					? travelerObject.getString(Constants.SESSION_TOKEN) : null);
			traveler.setUserId((travelerObject.has(Constants.USEER_ID) == true && !travelerObject.get(Constants.USEER_ID).equals(null)) ? travelerObject.getInt(Constants.USEER_ID) : -100);

			ord.setTraveler(traveler);
		}
		LiefernRepository.getInstance().addOrderToRequestList(ord);
	}
}
