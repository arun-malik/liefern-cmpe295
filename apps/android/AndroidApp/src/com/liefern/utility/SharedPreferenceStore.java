package com.liefern.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.liefern.LiefernApplication;
import com.liefern.models.User;

public class SharedPreferenceStore {
	// Shared preferences keys
	private static final String EMAIL_ID_KEY = "USER_NAME";
	private static final String PASSWORD_KEY = "PASSWORD";
	private static final String ALARM_SET_KEY = "ALARM_SET_KEY";

	// Login preference file name
	private static String LOGIN_PREFERENCE = "login_preference";
	private static SharedPreferences sharedPrefs;
	private static SharedPreferenceStore instance;
	private static final Object obj = new Object();

	public static synchronized SharedPreferenceStore getInstance() {
		if(instance == null) {
			synchronized (obj) {
				if(instance == null) {
					instance = new SharedPreferenceStore();
					sharedPrefs = LiefernApplication.getAppContext().getSharedPreferences(
							LOGIN_PREFERENCE, Context.MODE_PRIVATE);
				}
			}
		}
		return instance;
	}

	/**
	 * Returns stored user details
	 * 
	 * @return
	 */
	public User getStoredAccountDetails() {
		User user = new User();
		user.setEmailId(sharedPrefs.getString(EMAIL_ID_KEY, ""));
		user.setPassword(sharedPrefs.getString(PASSWORD_KEY, ""));
		return user;
	}

	/**
	 * Stores User details in the shared preferences object
	 * 
	 * @param user
	 *            Which is to be stored
	 */
	public void storeAccountDetails(User user) {
		if (user != null) {
			SharedPreferences.Editor e = sharedPrefs.edit();
			e.putString(EMAIL_ID_KEY, user.getEmailId());
			e.putString(PASSWORD_KEY, user.getPassword());
			e.commit();
		}
	}

	/**
	 * Removes stored user account details
	 */
	public void clearStoredAccountDetails() {
		SharedPreferences.Editor e = sharedPrefs.edit();
		e.remove(EMAIL_ID_KEY);
		e.remove(PASSWORD_KEY);
		e.commit();
	}
	
	/**
	 * Sets flag indicating repeating alarm is set
	 * @param alarmSet
	 */
	public void setAlarmSet(boolean alarmSet) {
		SharedPreferences.Editor e = sharedPrefs.edit();
		e.putBoolean(ALARM_SET_KEY, alarmSet);
		e.commit();
	}
	
	/**
	 * Returns value flag indicating repeating alarm
	 * @return
	 */
	public boolean isAlarmSet() {
		return sharedPrefs.getBoolean(ALARM_SET_KEY, false);
	}
}
