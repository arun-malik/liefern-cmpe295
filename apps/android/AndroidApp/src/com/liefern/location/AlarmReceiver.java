package com.liefern.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(this.getClass().getSimpleName(), "Alarm received");
		doPeriodicTask(context);
	}

	private void doPeriodicTask(Context context) {
		LocationPollerThread thread = new LocationPollerThread(context);
		thread.run();
	}

}
