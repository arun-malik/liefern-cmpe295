package com.liefern.views;

import android.os.Bundle;

import com.liefern.R;
import com.liefern.webservices.models.WebServiceModel;

public class MainActivity extends LiefernBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}


	@Override
	public WebServiceModel processService() throws Exception {
		return null;
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		
	}
}
