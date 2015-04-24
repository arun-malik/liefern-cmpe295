package com.liefern.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liefern.R;
import com.liefern.models.Address;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.models.WebServiceModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ShopperPage1Activity extends LiefernBaseActivity {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.nextShopperPage2:
			nextShopperPage2Button();
			break;
		}
	}
	
	private void nextShopperPage2Button() {
		Address toAddress = new Address();
		EditText text = (EditText) findViewById(R.id.toAddress1Text);
		toAddress.setAddress1( text.getText().toString() );
		text = (EditText) findViewById(R.id.toAddress2Text);
		toAddress.setAddress2( text.getText().toString() );
		text = (EditText) findViewById(R.id.toCityText);
		toAddress.setCity( text.getText().toString() );
		text = (EditText) findViewById(R.id.toCountryText);
		toAddress.setCountry( text.getText().toString() );
		text = (EditText) findViewById(R.id.toCountryText);
		toAddress.setCountry( text.getText().toString() );
		text = (EditText) findViewById(R.id.toZipText);
		if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
			toAddress.setZip( Integer.parseInt(text.getText().toString()) );
		}
		
		try {
			toAddress.setCreatedDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toAddress.setUser(LiefernRepository.getInstance().getLoggedInUser().getUserId());
		
		Address fromAddress = new Address();
		text = (EditText) findViewById(R.id.fromAddress1Text);
		fromAddress.setAddress1( text.getText().toString() );
		text = (EditText) findViewById(R.id.fromAddress2Text);
		fromAddress.setAddress2( text.getText().toString() );
		text = (EditText) findViewById(R.id.fromCityText);
		fromAddress.setCity( text.getText().toString() );
		text = (EditText) findViewById(R.id.fromCountryText);
		fromAddress.setCountry( text.getText().toString() );
		text = (EditText) findViewById(R.id.fromCountryText);
		fromAddress.setCountry( text.getText().toString() );
		text = (EditText) findViewById(R.id.fromZipText);
		if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
			fromAddress.setZip( Integer.valueOf(text.getText().toString()) ); 
		}

		try {
			fromAddress.setCreatedDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fromAddress.setUser(LiefernRepository.getInstance().getLoggedInUser().getUserId());

		LiefernRepository.getInstance().getBuiltOrder().setTolocation(toAddress);
		LiefernRepository.getInstance().getBuiltOrder().setFromlocation(fromAddress);

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
