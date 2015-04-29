/*package com.liefern.views;

public class AddPaymentCardActivity {

}
*/

package com.liefern.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Payments;
import com.liefern.webservices.models.WebServiceModel;

public class AddPaymentCardActivity extends LiefernBaseActivity {
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments_add_card_dialog);
		
		// adaptor = new PackageListAdapter(ShopperPage2Activity.this, R.layout.request_list_item, LiefernRepository.getInstance().getBuiltOrder().getPackages());
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
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.dialogAddPaymentButton:
			Payments card = new Payments();
			EditText text = (EditText) findViewById(R.id.cardNumberText);
			if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
				Log.d("Card No:", ""+Long.parseLong(text.getText().toString().trim()));
				card.setCreditCardNo(Long.parseLong(text.getText().toString().trim()) );
				Log.d("Card No added:", ""+card.getCreditCardNo());
			}	
			text = (EditText) findViewById(R.id.expireMonthText);
			if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
				card.setExpiryMonth(Integer.parseInt(text.getText().toString().trim()) );
			}
			text = (EditText) findViewById(R.id.expireYearText);
			if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
				card.setExpiryYear(Integer.parseInt(text.getText().toString().trim()) );
			}
			
			text = (EditText) findViewById(R.id.securityCodeText);
			if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
				card.setCvv(Integer.parseInt(text.getText().toString().trim()) );
			}
			text = (EditText) findViewById(R.id.nameOnCardText);
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
			//execute();
			Intent returnIntent = new Intent();
			setResult(RESULT_OK,returnIntent);
			finish();
			break;
		}
	}

}
