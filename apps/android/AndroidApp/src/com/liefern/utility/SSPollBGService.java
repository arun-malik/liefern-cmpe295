package com.liefern.utility;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SSPollBGService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private boolean _ThreadRun;

	/**
	 * @return the _ThreadRun
	 */
	public boolean get_ThreadRun() {
		return _ThreadRun;
	}

	/**
	 * @param _ThreadRun the _ThreadRun to set
	 */
	public void set_ThreadRun(boolean _ThreadRun) {
		this._ThreadRun = _ThreadRun;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("SSPollBGService", "GotOnCreate");
		BGWorker bgw = new  BGWorker(this);
		this.set_ThreadRun(true);
		bgw.start();
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onRebind(android.content.Intent)
	 */
	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("SSPolBGService", "GotOnStartCommand:" + intent.toString());
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onUnbind(android.content.Intent)
	 */
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

}
