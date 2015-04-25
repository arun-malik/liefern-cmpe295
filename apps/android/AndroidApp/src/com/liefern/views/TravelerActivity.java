package com.liefern.views;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class TravelerActivity extends LiefernBaseActivity implements OnItemClickListener {

	private ListView orderList;
	private static int REQUEST_TYPE  =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traveler);
		 orderList = (ListView) findViewById(R.id.orderList);

		orderList.setOnItemClickListener(this);
		 execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.traveler, menu);
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
		return wsImpl.getTravelerDeliveryOrders(); 

	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		 RequestListAdapter adaptor = new RequestListAdapter(this, R.layout.request_list_item, LiefernRepository.getInstance().getRequestOrderList(),REQUEST_TYPE);
	     orderList.setAdapter(adaptor);
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		Intent iOrderSelected =  new Intent(this, ViewRequest.class);
		iOrderSelected.putExtra("selected_item", position);
		startActivity(iOrderSelected);
		finish();
		
	}


}
