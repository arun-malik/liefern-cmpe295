package com.liefern.location;

import com.liefern.webservices.impl.WebsevicesImpl;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationPollerThread implements Runnable, LocationListener {
	static final String TAG = "LocationPollerThread";
	private LocationManager locationManager;
	
	public LocationPollerThread(Context context) {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void run() {
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
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
		WebsevicesImpl updateLocationService = new WebsevicesImpl();
		try {
			updateLocationService.updateCurrentLocation(location);
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
