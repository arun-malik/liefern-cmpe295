package com.liefern.views;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liefern.R;
import com.liefern.models.Address;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.models.Packages;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

public class ViewRequest extends LiefernBaseActivity {

	private ArrayAdapter<String> itemAdapter;
	private static final int ACKNOWLEDGE = 1;
	private static final int CANCEL = 4;
	private static final int DELIVERED = 5;
	BaseAdapter adapter;

	private int btnRecognizer;
	private Order currentOrder = null;
	private int orderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_view_request);

		Button deliveredBtn = (Button) findViewById(R.id.deliveredButton);
		Button ackBtn = (Button) findViewById(R.id.acknowledgeButton);
		Button cancelBtn = (Button) findViewById(R.id.cancelButton);

		int position = 0;
		position = getIntent().getExtras().getInt("position");

		btnRecognizer = getIntent().getExtras().getInt("currentActivity");

		enableButton(deliveredBtn, ackBtn, cancelBtn);

		List<Order> orderList = LiefernRepository.getInstance()
				.getRequestOrderList();

		currentOrder = orderList.get(position);
		

		if (currentOrder != null) {
			
			orderId = currentOrder.getOrderId();

			ListView itemList = (ListView) findViewById(R.id.itemList);

			final int orderId = currentOrder.getOrderId();
			TextView orderText = (TextView) findViewById(R.id.orderId);
			orderText.setText("Order Number "+String.valueOf(currentOrder.getOrderId()));

			TextView fromAddrText = (TextView) findViewById(R.id.fromAddress);
			Address fromAddr = currentOrder.getFromlocation();
			fromAddrText.setText(fromAddr.getAddress1() + " "
					+ fromAddr.getCity() + " " + fromAddr.getCountry());

			TextView toAddrText = (TextView) findViewById(R.id.toAddress);
			Address toAddr = currentOrder.getTolocation();
			toAddrText.setText(toAddr.getAddress1() + " " + toAddr.getCity()
					+ " " + toAddr.getCountry());

			
			// To populate the ListView with the packages(items) in that order
			List<Packages> packageList = currentOrder.getPackages();
			if (null != packageList && packageList.size() > 0) {
				int totalWeight = 0;
				for (Packages singlePackage : packageList) {
					totalWeight += singlePackage.getWeight();
				}

				adapter = new PackageListAdapter(this, R.layout.request_list_item, packageList);
				itemList.setAdapter(adapter);
				
				TextView totalWeightText = (TextView) findViewById(R.id.totalWeight);
				totalWeightText.setText(String.valueOf("Total Package Weight :"+totalWeight));
			}

			TextView estCostText = (TextView) findViewById(R.id.estimatedCost);
			estCostText
					.setText("Estimated Price $" + String.valueOf(currentOrder.getSuggestAmount()));

			TextView custQuoteText = (TextView) findViewById(R.id.customerQuote);
			custQuoteText.setText("Customer's Price $"+String.valueOf(currentOrder
					.getCustomerAmount()));
			
			TextView  orderStatus = (TextView) findViewById(R.id.orderStatus);
			orderStatus.setText(currentOrder.getOrderStatus() == 0 ? "PENDING" : currentOrder.getOrderStatus() == 1 ? "ACKNOWLEDGE" : currentOrder.getOrderStatus() == 4 ? "CANCELLED"  : currentOrder.getOrderStatus() == 5 ? "DELIVERED" : "INVALID ORDER STATUS" );

			ackBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onAckButtonPress(orderId);
				}
			});

			cancelBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onCancelButtonPress(orderId);
				}
			});
			
			deliveredBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onDeliveredButtonPress(orderId);
				}

				private void onDeliveredButtonPress(int orderId) {
					
					JSONObject json = new JSONObject();
					try {
						json.put("orderstatus", DELIVERED);
					} 
					catch (JSONException e) {
						e.printStackTrace();
					}
					
					execute(json);
					
				}
			});

		}
	}

	private void enableButton(Button deliveredBtn, Button ackBtn, Button cancelBtn) {
		switch (btnRecognizer) {
		// Delivery-1 and Request-3
		case 1:
			deliveredBtn.setVisibility(View.VISIBLE);
			ackBtn.setVisibility(View.INVISIBLE);
			cancelBtn.setVisibility(View.VISIBLE);
			break;
		case 3:
			deliveredBtn.setVisibility(View.INVISIBLE);
			cancelBtn.setVisibility(View.VISIBLE);
			ackBtn.setVisibility(View.INVISIBLE);
			break;
		// Receipt-2
		case 2:
			deliveredBtn.setVisibility(View.INVISIBLE);
			ackBtn.setVisibility(View.INVISIBLE);
			cancelBtn.setVisibility(View.INVISIBLE);
			break;
		// Traveler- 4 to acknowledge
		case 4:
			deliveredBtn.setVisibility(View.INVISIBLE);
			ackBtn.setVisibility(View.VISIBLE);
			cancelBtn.setVisibility(View.INVISIBLE);
			break;

		}
	}

	private void onAckButtonPress(int orderId){

		JSONObject json = new JSONObject();
		try {
			json.put("orderstatus", ACKNOWLEDGE);
			json.put("travlerid", LiefernRepository.getInstance().getLoggedInUser().getUserId());
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		execute(json);
	}

	private void onCancelButtonPress(int orderId) {

		JSONObject json = new JSONObject();
		try {
			json.put("orderstatus", CANCEL);
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		execute(json);
	}

	/**
	 * Holds implementation of web service which you want to execute in
	 * background thread.
	 * 
	 * @return Result
	 * @throws Exception
	 */
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.updateOrderStatus(orderId, this.getRequestType().toString());
		
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {

		Intent intent = null;

		Toast.makeText(getApplicationContext(),
				"Request Placed Successfully !", Toast.LENGTH_LONG).show();

		switch (btnRecognizer) {
		// Delivery-1 and Request-3
		case 1:
			intent = new Intent(this, DeliveryActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent = new Intent(this, RequestActivity.class);
			startActivity(intent);
			break;
		// Receipt-2
		case 2:
			intent = new Intent(this, ReceiptActivity.class);
			startActivity(intent);
			break;
		// Traveler- 4 to acknowledge
		case 4:
			intent = new Intent(this, DeliveryActivity.class);
			startActivity(intent);
			break;

		}
	}

	/**
	 * Notifies response error.
	 * 
	 * @param model
	 */
	public void notifyWebResponseError(WebServiceModel model) {

		Toast.makeText(getApplicationContext(), "Error. Please try again !",
				Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.menu_view_request, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
