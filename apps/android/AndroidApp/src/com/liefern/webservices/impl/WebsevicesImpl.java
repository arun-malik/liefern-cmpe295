package com.liefern.webservices.impl;

import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;






import android.net.Uri;
import android.util.Log;

import com.liefern.models.LiefernRepository;
import com.liefern.models.User;
import com.liefern.webservices.models.LoginResult;
import com.liefern.webservices.models.LogoutResult;
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
		loginResult.parseJSON(WebServiceHelper.executeRequest(httpPost,0));
//		LiefernRepository.getInstance().setLoggedInUser(!loginResult.isError() ? user : null);
		return loginResult;
	}
	
	public SignUpResult signUp(User user) throws Exception {
		SignUpResult signUpResult = new SignUpResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.SIGN_UP_URL);
		httpPost.setEntity(new StringEntity(user.toJSON().toString()));
		signUpResult.parseJSON(WebServiceHelper.executeRequest(httpPost,0));
		LiefernRepository.getInstance().setLoggedInUser(!signUpResult.isError() ? user : null);
		return signUpResult;
	}
	
	public LogoutResult logOut() throws Exception {
		LogoutResult logoutResult = new LogoutResult();
		HttpPost httpPost = new HttpPost(WebserviceURLs.LOG_OUT_URL);
		
		logoutResult.parseJSON(WebServiceHelper.executeRequest(httpPost,0));
		if(!logoutResult.isError()) {
			LiefernRepository.getInstance().setLoggedInUser(null);
		}
		return logoutResult;
	}
	
	public RequestOrderResult requestOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet( WebserviceURLs.REQUEST_ORDER_RESULT   + LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper.executeRequest(httpGet,1));
		return requestOrderResult;
	}

	public RequestOrderResult deliveryOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(  WebserviceURLs.DELIVERY_ORDER_RESULT  + LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper.executeRequest(httpGet,1));
		return requestOrderResult;
	}

	public RequestOrderResult receiptsOrder() throws Exception {
		RequestOrderResult requestOrderResult = new RequestOrderResult();
		HttpGet httpGet = new HttpGet(  WebserviceURLs.RECEIPT_ORDER_RESULT  + LiefernRepository.getInstance().getLoggedInUser().getUserId());
		requestOrderResult.parseJSON(WebServiceHelper.executeRequest(httpGet,1));
		return requestOrderResult;
	}
}
