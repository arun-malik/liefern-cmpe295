package com.liefern.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import java.util.ArrayList;

public class ReceiptActivity extends LiefernBaseActivity {

	private static ReceiptActivity instance;
	private int REQUEST_TYPE = 1;
	
	ArrayList<Order> orderArray;
    BaseAdapter adaptor;
	private ListView lstReceipts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt);
		instance = this;
		lstReceipts = (ListView) findViewById(R.id.lstReceipts);

        lstReceipts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // currentActivity 2 -> receipt
                startActivity(new Intent(getApplicationContext(), ViewRequest.class).putExtra("position", position).putExtra("currentActivity", 2));
            }
        });

		execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receipt, menu);
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
		return wsImpl.receiptsOrder(); 
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		adaptor = new RequestListAdapter(this, R.layout.request_list_item, LiefernRepository.getInstance().getRequestOrderList(),REQUEST_TYPE);
	    lstReceipts.setAdapter(adaptor);
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub
		
	}
}
