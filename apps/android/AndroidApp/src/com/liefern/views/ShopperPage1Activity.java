package com.liefern.views;

import com.liefern.R;
import com.liefern.webservices.models.WebServiceModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ShopperPage1Activity extends LiefernBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopper_page1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopper_page1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void nextShopperPage2Button(View view) {
		  Intent intent = new Intent(this, ShopperPage2Activity.class);
		  startActivity(intent);
		}

	@Override
	public WebServiceModel processService() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub
		
	}
}
