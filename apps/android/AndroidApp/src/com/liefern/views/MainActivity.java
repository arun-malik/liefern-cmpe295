package com.liefern.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
	
	public void loadTravelerActivity(View view) {
		Intent travelerIntent = new Intent(this, TravelerActivity.class);
	    startActivity(travelerIntent);
	}
	
	public void loadShopperActivity(View view) {
		Intent intent = new Intent(this, ShopperPage1Activity.class);
	    startActivity(intent);
	}
	
}
