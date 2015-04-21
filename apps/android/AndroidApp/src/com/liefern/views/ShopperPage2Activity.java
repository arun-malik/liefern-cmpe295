package com.liefern.views;

import com.liefern.R;
import com.liefern.webservices.models.WebServiceModel;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class ShopperPage2Activity extends LiefernBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopper_page2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopper_page2, menu);
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

	public void nextShopperPage3(View view) {
		Intent intent = new Intent(this, ShopperPage3Activity.class);
		startActivity(intent);
	}

	public void addPackagesButton(View view) {
		// TODO Auto-generated method stub
		// custom dialog
		final Dialog dialog = new Dialog(getBaseContext());
		dialog.setContentView(R.layout.activity_shopper_add_package_dialog);
		dialog.setTitle("Add Package");


		Button dialogButton = (Button) dialog.findViewById(R.id.addPackDialog);
		dialogButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();

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
