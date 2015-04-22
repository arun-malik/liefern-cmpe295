package com.liefern.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShopperPage3Activity extends LiefernBaseActivity {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopper_page3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopper_page3, menu);
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

	public void placeOrder(View view) {
		LiefernRepository.getInstance().getBuiltOrder().setCustomerId(LiefernRepository.getInstance().getLoggedInUser().getUserId());
		LiefernRepository.getInstance().getBuiltOrder().setOrderType(1);
		LiefernRepository.getInstance().getBuiltOrder().setOrderStatus(0);
		try {
			LiefernRepository.getInstance().getBuiltOrder().setCreatedDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EditText text = (EditText) findViewById(R.id.totalMilesText);
		if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
			LiefernRepository.getInstance().getBuiltOrder().setDistance(Integer.parseInt(text.getText().toString()));
		}

		text = (EditText) findViewById(R.id.recommendedCostText);
		if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
			LiefernRepository.getInstance().getBuiltOrder().setSuggestAmount(Integer.parseInt(text.getText().toString()));;
		}

		text = (EditText) findViewById(R.id.shopperQuoteCostText);
		if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
			LiefernRepository.getInstance().getBuiltOrder().setCustomerAmount(Integer.parseInt(text.getText().toString()));;
		}

		execute();

		/*		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);*/


	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.postOrder(LiefernRepository.getInstance().getBuiltOrder());
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		Toast.makeText(getApplicationContext(), "Request Placed Successfully !",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, RequestActivity.class);
		startActivity(intent);
		

	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		Toast.makeText(getApplicationContext(), "Sorry, error in placing your request. Please try again !",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);

	}
}
