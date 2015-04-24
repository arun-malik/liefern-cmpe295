package com.liefern.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.liefern.R;
import com.liefern.models.User;
import com.liefern.utility.SharedPreferenceStore;
import com.liefern.utility.Utility;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

public class LoginActivity extends LiefernBaseActivity {
	private EditText emailIdEditText;
	private EditText passwordEditText;
	private User user;
	private static LoginActivity instance;
	private CheckBox rememberMeCheckBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		instance = this;
		emailIdEditText = (EditText) findViewById(R.id.email_id);
		passwordEditText = (EditText) findViewById(R.id.password);
		rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me);
		user = SharedPreferenceStore.getInstance().getStoredAccountDetails();
		if(user.getEmailId().length() > 0) {
			emailIdEditText.setText(user.getEmailId());
			passwordEditText.setText(user.getPassword());
			rememberMeCheckBox.setChecked(true);
			execute();
		}
	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.login(user);
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {

	}
	
	public static LoginActivity getLoginActivity() {
		return instance;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		
		case R.id.login:
			user = new User();
			if(Utility.validate(emailIdEditText, R.string.email_id) && Utility.validate(passwordEditText, R.string.password)) {
				String email = emailIdEditText.getText().toString().trim();
				String password = passwordEditText.getText().toString().trim();
				user.setEmailId(email);
				user.setPassword(password);
				if(rememberMeCheckBox.isChecked()) {
					SharedPreferenceStore.getInstance().storeAccountDetails(user);
				} else {
					SharedPreferenceStore.getInstance().clearStoredAccountDetails();
				}
				execute();
			}
			break;
		
		case R.id.sign_up:
			Intent signUpIntent = new Intent(this, SignUpActivity.class);
			startActivity(signUpIntent);
			break;
			
		}
	}

}
