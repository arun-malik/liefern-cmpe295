package com.liefern.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.models.WebServiceModel;

public class MainActivity extends LiefernBaseActivity implements OnMapReadyCallback {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
	}

	public void onMapReady(GoogleMap map) {
       LatLng sjsu = new LatLng(37.3353, -121.8813); 

        map.setMyLocationEnabled(true);
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sjsu, 13));

/*        map.addMarker(new MarkerOptions()
                .title("San Jose State university")
                .snippet("SJSU university")
                .position(sjsu));*/
        
        map.addMarker(new MarkerOptions()
        .title("Driver 1")
        .snippet("Liefern")
        .position(new LatLng(37.335847, -121.886016)));
        
        map.addMarker(new MarkerOptions()
        .title("Driver 2")
        .snippet("Liefern")
        .position(new LatLng(37.334551, -121.876510)));
        
        
    }

	@Override
	public WebServiceModel processService() throws Exception {
		return null;
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.travelerButton:
			Intent travelerIntent = new Intent(this, TravelerActivity.class);
		    startActivity(travelerIntent);
			break;
			
		case R.id.shopperButton:
			Intent intent = new Intent(this, ShopperPage1Activity.class);
		    startActivity(intent);
			break;
		}
	}
	
}
