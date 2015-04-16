package com.liefern.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liefern.R;
import com.liefern.models.User;
import com.liefern.utility.Utility;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

public class SignUpActivity extends LiefernBaseActivity {
	private EditText nameEditText;
	private EditText mobileEditText;
	private EditText emailIdEditText;
	private EditText passwordEditText;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		nameEditText = (EditText) findViewById(R.id.name);
		mobileEditText = (EditText) findViewById(R.id.mobile_no);
		emailIdEditText = (EditText) findViewById(R.id.email_id);
		passwordEditText = (EditText) findViewById(R.id.password);
	}
	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.signUp(user);
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
		LoginActivity.getLoginActivity().finish();
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {

	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.sign_up:
			if(Utility.validate(nameEditText, R.string.name) && Utility.validate(mobileEditText, R.string.mobile) &&
					Utility.validate(emailIdEditText, R.string.email_id) && Utility.validate(passwordEditText, R.string.password)) {
				user = new User();
				user.setName(nameEditText.getText().toString().trim());
				user.setMobile(mobileEditText.getText().toString().trim());
				user.setEmailId(emailIdEditText.getText().toString());
				user.setPassword(passwordEditText.getText().toString());
				execute("signup");
			}
			break;
		}
	}

}
