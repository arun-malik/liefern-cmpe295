package com.liefern.webservices.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.location.Location;
import android.util.Log;

import com.liefern.models.Geos;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.models.Payments;
import com.liefern.models.User;
import com.liefern.webservices.models.LocationOfNearestUsers;
import com.liefern.webservices.models.LoginResult;
import com.liefern.webservices.models.LogoutResult;
import com.liefern.webservices.models.PaymentCardResult;
import com.liefern.webservices.models.PostOrderResult;
import com.liefern.webservices.models.PostPaymentCardResult;
import com.liefern.webservices.models.PutLocationResult;
import com.liefern.webservices.models.PutOrderStatusResult;
import com.liefern.webservices.models.RequestOrderResult;
import com.liefern.webservices.models.SignUpResult;
import com.liefern.webservices.models.WebServiceModel;

@SuppressWarnings("deprecation")
public final class WebsevicesImpl {
	static final String TAG = "WebsevicesImpl";

	public LoginResult login(User user) throws Exception {
		LoginResult loginResult = new LoginResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.LOGIN_URL);
		httpPost.setEntity(new StringEntity(user.toJSON().toString()));
		loginResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		// LiefernRepository.getInstance().setLoggedInUser(!loginResult.isError()
		// ? user : null);
		return loginResult;
	}

	public LoginResult updateProfile(User user) throws Exception {
		LoginResult loginResult = new LoginResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.UPDATE_PROFILE_URL
				+ LiefernRepository.getInstance().getLoggedInUser().getUserId());
		httpPost.setEntity(new StringEntity(user.toJSONWithAddress().toString()));
		loginResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		return loginResult;
	}

	public SignUpResult signUp(User user) throws Exception {
		SignUpResult signUpResult = new SignUpResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.SIGN_UP_URL);
		httpPost.setEntity(new StringEntity(user.toJSON().toString()));
		signUpResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		LiefernRepository.getInstance().setLoggedInUser(
				!signUpResult.isError() ? user : null);
		return signUpResult;
	}

	public LogoutResult logOut() throws Exception {
		LogoutResult logoutResult = new LogoutResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.LOG_OUT_URL);

		logoutResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		if (!logoutResult.isError()) {
			LiefernRepository.getInstance().setLoggedInUser(null);
		}
		return logoutResult;
	}

	public PostOrderResult postOrder(Order order) throws Exception {
		PostOrderResult postOrderResult = new PostOrderResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.POST_ORDER_URL);
		httpPost.setEntity(new StringEntity(order.toJSON().toString()));
		postOrderResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		return postOrderResult;
	}

	public RequestOrderResult requestOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(WebserviceURLs.REQUEST_ORDER_RESULT
				+ LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper
				.executeRequest(httpGet, 1));
		return requestOrderResult;
	}

	public PaymentCardResult requestPaymentCards() throws Exception {
		PaymentCardResult requestPaymentCardsResult = new PaymentCardResult();
		HttpGet httpGet = new HttpGet(
				WebserviceURLs.REQUEST_PAYMENT_CARD_RESULT
						+ LiefernRepository.getInstance().getLoggedInUser()
								.getUserId());
		requestPaymentCardsResult.parseJSON(WebServiceHelper.executeRequest(
				httpGet, 1));
		return requestPaymentCardsResult;
	}

	public PostPaymentCardResult postPaymentCard(Payments card)
			throws Exception {
		PostPaymentCardResult postPaymentCardResult = new PostPaymentCardResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.POST_PAYMENT_CARD_URL);
		Log.d("post card: ", card.toJSON().toString());
		httpPost.setEntity(new StringEntity(card.toJSON().toString()));
		postPaymentCardResult.parseJSON(WebServiceHelper.executeRequest(
				httpPost, 0));
		return postPaymentCardResult;
	}

	public RequestOrderResult deliveryOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(WebserviceURLs.DELIVERY_ORDER_RESULT
				+ LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper
				.executeRequest(httpGet, 1));
		return requestOrderResult;
	}

	public RequestOrderResult receiptsOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(String.format( WebserviceURLs.RECEIPT_ORDER_RESULT, LiefernRepository.getInstance().getLoggedInUser().getUserId(),LiefernRepository.getInstance().getLoggedInUser().getUserId()));
		requestOrderResult.parseJSON(WebServiceHelper
				.executeRequest(httpGet, 1));
		return requestOrderResult;
	}

	public RequestOrderResult getTravelerDeliveryOrders() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(WebserviceURLs.TRAVELER_ORDER_RESULT
				+ LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper
				.executeRequest(httpGet, 1));
		return requestOrderResult;
	}

	public PutOrderStatusResult updateOrderStatus(int orderId, String orderObj) throws Exception {

		PutOrderStatusResult orderStatusResult = new PutOrderStatusResult();
		HttpPut httpPut = new HttpPut(WebserviceURLs.UPDATE_ORDER_STATUS
				+ orderId);
		httpPut.setEntity(new StringEntity(orderObj));
		orderStatusResult.parse(WebServiceHelper.executeRequest(httpPut, 0));
		return orderStatusResult;
	}
	
	public PutLocationResult updateCurrentLocation(Geos location) throws Exception {
		PutLocationResult putLocationResult = new PutLocationResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.POST_USER_GEO_LOCATION);
		httpPost.setEntity(new StringEntity(location.toJSON().toString()));
		Log.d("Json object", location.toJSON().toString());
		putLocationResult.parseJSON(WebServiceHelper.executeRequest(httpPost, 0));
		return putLocationResult;
	}

	public LocationOfNearestUsers reqestNearestUsers() throws Exception {
		LocationOfNearestUsers nearestUserLocationResult = new LocationOfNearestUsers();
		HttpGet httpGet = new HttpGet(String.format( WebserviceURLs.GET_USER_GEO_LOCATION));
		nearestUserLocationResult.parseJSON(WebServiceHelper
				.executeRequest(httpGet, 1));
		return nearestUserLocationResult;
	}

}
