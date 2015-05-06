package com.liefern.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liefern.R;
import com.liefern.models.LiefernRepository;

public class SliderMenuListAdapter extends BaseAdapter {
	// Slider items
	public static final String USER_INFO = "USER_INFO";
	public static final String RECEIPT = "RECEIPT";
	public static final String DELIVERY = "DELIVERY";
	public static final String REQUEST = "REQUEST";
	public static final String PAYMENT = "PAYMENT";
	public static final String LOGOUT = "LOGOUT";
	public static final String HOME = "HOME";
	
	private Context context;
	public static final String sliderMenuItems[] = {USER_INFO, HOME, REQUEST, DELIVERY, RECEIPT, PAYMENT, LOGOUT};
	private static final int sliderMenuIcons[] = {}; 
	private static SliderMenuListAdapter instance;
	private static final Object obj = new Object();
	
	private SliderMenuListAdapter(Context context) {
		this.context 	= context;
	}
	
	public static SliderMenuListAdapter getInstance(Context context) {
		if(instance == null) {
			synchronized (obj) {
				if(instance == null) {
					instance = new SliderMenuListAdapter(context);
				}
			}
		}
		return instance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView == null) {
			if(position == 0) {
				convertView = inflater.inflate(R.layout.user_info_slider_menu_list_item, parent,false);
				TextView name = (TextView) convertView.findViewById(R.id.slider_menu_text); 
				
				if(LiefernRepository.getInstance().getLoggedInUser().getName().equals(null)){	
						name.setText(LiefernRepository.getInstance().getLoggedInUser().getName());
				}
			} else {
				convertView = inflater.inflate(R.layout.slider_menu_list_item, null);
				TextView oSliderMenuText = (TextView) convertView.findViewById(R.id.slider_menu_text);
				
				oSliderMenuText.setText(sliderMenuItems[position]);
			}
		}
		

//		oSliderMenuText.setTextColor(context.getResources().getColor(android.R.color.white));
		/*ImageView oSliderMenuIcon = (ImageView) convertView.findViewById(R.id.slider_menu_icon);
		oSliderMenuIcon.setBackgroundResource(sliderMenuIcons[position]);
		oSliderMenuIcon.setVisibility(View.VISIBLE);*/
			
		return convertView;
	}


	@Override
	public int getCount() {
		return sliderMenuItems.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}