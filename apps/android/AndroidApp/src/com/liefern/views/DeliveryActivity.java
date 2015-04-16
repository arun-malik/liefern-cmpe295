package com.liefern.views;

import java.util.ArrayList;

import com.liefern.R;
import com.liefern.R.id;
import com.liefern.R.layout;
import com.liefern.R.menu;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class DeliveryActivity extends LiefernBaseActivity {
	

	private static DeliveryActivity instance;
	private int REQUEST_TYPE = 1;
	
	ArrayList<Order> orderArray;
    BaseAdapter adaptor;
	private ListView lstDelivery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delivery);
		instance = this;
		lstDelivery = (ListView) findViewById(R.id.lstDelivery);
		execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delivery, menu);
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
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.deliveryOrder(); 
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		 adaptor = new RequestListAdapter(this, R.layout.request_list_item, LiefernRepository.getInstance().getRequestOrderList(),REQUEST_TYPE);
	     lstDelivery.setAdapter(adaptor);
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub
		
	}
}
