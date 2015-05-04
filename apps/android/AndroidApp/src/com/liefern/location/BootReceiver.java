package com.liefern.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.liefern.utility.SharedPreferenceStore;

public class BootReceiver extends BroadcastReceiver {
	
	private AlarmManager alarmManager;
	private PendingIntent alarmIntent;
	private static final int POLLING_INTERVAL = 30 * 1000;

	@Override
	public void onReceive(Context context, Intent data) {
		Log.d(this.getClass().getSimpleName(), "----- Device booted. Scheduling alarm. -----");
		
		// If device is restarted then ignore alarm set flag and set repeating alarm
		if(data.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			setRepeatingAlarm(context);
		} else {
			
			// If some other broadcast is received (ex. from BaseActivity) then set alarm only if it is not set.
			// Useful when user installs app at that time we don't have boot completed broadcast but we want to set repeating alarm
			if(!SharedPreferenceStore.getInstance().isAlarmSet()) {
				setRepeatingAlarm(context);
			} else {
				SharedPreferenceStore.getInstance().setAlarmSet(true);
			}
		}
	}

	private void setRepeatingAlarm(Context context) {
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime(),
				POLLING_INTERVAL, alarmIntent);
	}
	
	// Might be required in future
	/*private void stopRepeatingAlarm() {
		if(alarmManager != null && alarmIntent != null) {
			alarmManager.cancel(alarmIntent);
		}
	}*/

}
