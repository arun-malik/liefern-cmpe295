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
import com.liefern.models.Packages;

public class PackageListAdapter extends BaseAdapter {
	private Activity activity;
	private int resourceId;
	private List<Packages> list;

	public PackageListAdapter(Activity requestActivity, int resourceId,
			List<Packages> list) {
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

			deliveryTitle.setText("Package: " + list.get(position).getDescription().toString());

			TextView fromToLocation = (TextView) convertView
					.findViewById(R.id.from_to_location);

			fromToLocation.setText( "Size: " + list.get(position).getSize() + " - "
					+  "Weight: "+ list.get(position).getWeight() );


		} catch (Exception ex) {
			Log.d("Arun Malik","Exception : "+ ex.getMessage());

		}


		return convertView;
	}

}
