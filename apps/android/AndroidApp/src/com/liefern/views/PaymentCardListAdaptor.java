package com.liefern.views;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liefern.R;
import com.liefern.models.Payments;

public class PaymentCardListAdaptor extends BaseAdapter {
	private Activity activity;
	private int resourceId;
	private List<Payments> list;
	

	public PaymentCardListAdaptor(Activity requestActivity, int resourceId,
			List<Payments> list) {
		this.activity = requestActivity;
		this.resourceId = resourceId;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resourceId, null);
		}


		try {
			TextView deliveryTitle = (TextView) convertView
					.findViewById(R.id.delivery_title);

			deliveryTitle.setText("Card: " + list.get(position).getCreditCardNo());

			TextView fromToLocation = (TextView) convertView
					.findViewById(R.id.from_to_location);

			fromToLocation.setText( "" + list.get(position).getNameOnCard() + " - "
					+  list.get(position).getExpiryMonth()+"/"+ list.get(position).getExpiryYear());


		} catch (Exception ex) {
			Log.d("","Exception : "+ ex.getMessage());

		}


		return convertView;
	}

}

