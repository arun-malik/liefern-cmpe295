package com.liefern.location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liefern.LiefernApplication;
import com.liefern.models.Geos;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.impl.WebsevicesImpl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationPollerThread implements Runnable, LocationListener {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	static final String TAG = "LocationPollerThread";
	private LocationManager locationManager;
	
	public LocationPollerThread(Context context) {
		
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void run() {
		//Log.d(this.getClass().getSimpleName(), "In Location Thread");
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		/*Toast.makeText(LiefernApplication.getAppContext(), "Location Changed",
				Toast.LENGTH_LONG).show();*/
		if(LiefernRepository.getInstance().getLoggedInUser() !=null)
		manageLocation(location);
		stopLocationSearch();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		stopLocationSearch();
	}

	@Override
	public void onProviderEnabled(String provider) {
		stopLocationSearch();
	}

	@Override
	public void onProviderDisabled(String provider) {
		stopLocationSearch();
	}

	private void manageLocation(Location location) {
		Log.d(getClass().getSimpleName(), "Current location --> " + location.toString());
		Geos geo = new Geos();
		geo.setLat((float)location.getLatitude());
		geo.setLng((float)location.getLongitude());
		geo.setRadius(location.getAccuracy());
		geo.setUserId(LiefernRepository.getInstance().getLoggedInUser().getUserId());
		try {
			geo.setCreatedDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WebsevicesImpl updateLocationService = new WebsevicesImpl();
		try {
			updateLocationService.updateCurrentLocation(geo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void stopLocationSearch() {
		try {
			if (locationManager != null) {
				Log.d(TAG, "Location search stopped");
				locationManager.removeUpdates(this);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
