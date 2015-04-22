package com.liefern.views;

import java.util.ArrayList;
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
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;

public class RequestListAdapter extends BaseAdapter {
	private Activity activity;
	private int resourceId;
	private List<Order> list;
	private int requestType;

	public RequestListAdapter(Activity requestActivity, int resourceId,
			List<Order> list, int requestType) {
		this.activity = requestActivity;
		this.resourceId = resourceId;
		this.list = list;
		this.requestType = requestType;
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

		// if(requestType ==1){ // request is from Request Activity
		//
		// }

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resourceId, null);
		}

		if (this.requestType == 1) {

			try {
				TextView deliveryTitle = (TextView) convertView
						.findViewById(R.id.delivery_title);

				deliveryTitle.setText("Order Identifier : " + Integer.toString(list.get(position).getOrderId()));

				TextView fromToLocation = (TextView) convertView
						.findViewById(R.id.from_to_location);

				fromToLocation.setText( list.get(position).getFromlocation().getCity() + " - "
						+  list.get(position).getTolocation().getCity() );

				TextView price = (TextView) convertView
						.findViewById(R.id.price);

				price.setText(Integer.toString(list.get(position).getCustomerAmount()));

			} catch (Exception ex) {
				Log.d("Arun Malik","Exception : "+ ex.getMessage());

			}

		}
		
		return convertView;
	}

}
