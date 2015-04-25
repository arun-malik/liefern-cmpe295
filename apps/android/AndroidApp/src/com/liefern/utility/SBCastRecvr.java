package com.liefern.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SBCastRecvr extends BroadcastReceiver {
	

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Recvr-Thread:", "Recd:" + intent.toString() );
 	}

}
