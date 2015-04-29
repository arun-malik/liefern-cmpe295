package com.liefern.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Payments;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class PaymentsActivity extends LiefernBaseActivity {
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	BaseAdapter adaptor;
	private ListView lstRequests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments);
		lstRequests = (ListView) findViewById(R.id.cardList);
		execute();
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
		Intent i = new Intent(this, AddPaymentCardActivity.class);
		startActivityForResult(i, 1);
		/*final Dialog dialog = new Dialog(PaymentsActivity.this);
		dialog.setContentView(R.layout.activity_payments_add_card_dialog);
		dialog.setTitle("Add Card");


		Button dialogButton = (Button) dialog.findViewById(R.id.dialogAddPaymentButton);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener( new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Payments card = new Payments();
				EditText text = (EditText) dialog.findViewById(R.id.cardNumberText);
				if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
					card.setCreditCardNo(Integer.parseInt(text.getText().toString().trim()) );
				}	
				text = (EditText) dialog.findViewById(R.id.expireMonthText);
				if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
					card.setExpiryMonth(Integer.parseInt(text.getText().toString().trim()) );
				}
				text = (EditText) dialog.findViewById(R.id.expireYearText);
				if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
					card.setExpiryYear(Integer.parseInt(text.getText().toString().trim()) );
				}
				
				text = (EditText) dialog.findViewById(R.id.securityCodeText);
				if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
					card.setCvv(Integer.parseInt(text.getText().toString().trim()) );
				}
				text = (EditText) dialog.findViewById(R.id.nameOnCardText);
				card.setNameOnCard(text.getText().toString().trim());
				
				card.setUserId(LiefernRepository.getInstance().getLoggedInUser().getUserId());
				
				try {
					card.setCreatedDate(sdf.parse(sdf.format(new Date())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				LiefernRepository.getInstance().setNewCard(card);
				//LiefernRepository.getInstance().addPaymentCard(card);
				execute();
				dialog.dismiss();
			}
		});

		dialog.show();*/
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            execute();
	        }
	    }
	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		//Integer i = (Integer) getRequestType();
		//if(i.intValue() == 1) {
		if(LiefernRepository.getInstance().getNewCard() == null){
			return wsImpl.requestPaymentCards();
		} else {
			return wsImpl.postPaymentCard(LiefernRepository.getInstance().getNewCard());
		}
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		 adaptor = new PaymentCardListAdaptor(this, R.layout.request_list_item, LiefernRepository.getInstance().getPaymentCardList());
	     lstRequests.setAdapter(adaptor);
	     LiefernRepository.getInstance().setNewCard(null);
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub

	}
}
