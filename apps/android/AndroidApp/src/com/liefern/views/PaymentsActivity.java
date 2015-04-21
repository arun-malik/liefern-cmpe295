package com.liefern.views;

import com.liefern.R;
import com.liefern.webservices.models.WebServiceModel;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PaymentsActivity extends LiefernBaseActivity {

	private Spinner spinnerMonth;
	private static final String[]months = {"Jan", "Feb", "Mar" , "Apr", "May", "Jun","July","Aug","Sep","Oct","Nov", "Dec"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments);

		spinnerMonth = (Spinner)findViewById(R.id.spinnerMonth);
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(PaymentsActivity.this,
				android.R.layout.simple_spinner_item,months);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMonth.setAdapter(adapter);
		spinnerMonth.setOnItemSelectedListener((OnItemSelectedListener) this);
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

		switch (position) {
		case 0:
			// Whatever you want to happen when the first item gets selected
			break;
		case 1:
			// Whatever you want to happen when the second item gets selected
			break;
		case 2:
			// Whatever you want to happen when the thrid item gets selected
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payments, menu);
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

	public void addPaymentsDialog(View view) {
		final Dialog dialog = new Dialog(getBaseContext());
		dialog.setContentView(R.layout.activity_payments_add_card_dialog);
		dialog.setTitle("Add Card");


		Button dialogButton = (Button) dialog.findViewById(R.id.addPayment);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener( new OnClickListener() {

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
