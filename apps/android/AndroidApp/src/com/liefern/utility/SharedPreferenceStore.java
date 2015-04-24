package com.liefern.utility;

import com.liefern.LiefernApplication;
import com.liefern.models.User;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceStore {
	// Shared preferences keys
	private static final String EMAIL_ID_KEY = "USER_NAME";
	private static final String PASSWORD_KEY = "PASSWORD";

	// Login preference file name
	private static String LOGIN_PREFERENCE = "login_preference";
	private static SharedPreferences sharedPrefs;
	private static SharedPreferenceStore instance;


	public static synchronized SharedPreferenceStore getInstance() {
		if (instance == null)
			instance = new SharedPreferenceStore();
		return instance;
	}

	/**
	 * Returns stored user details
	 * 
	 * @return
	 */
	public User getStoredAccountDetails() {
		sharedPrefs = LiefernApplication.getAppContext().getSharedPreferences(
				LOGIN_PREFERENCE, Context.MODE_PRIVATE);
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
			sharedPrefs = LiefernApplication.getAppContext()
					.getSharedPreferences(LOGIN_PREFERENCE,
							Context.MODE_PRIVATE);
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
		sharedPrefs = LiefernApplication.getAppContext().getSharedPreferences(
				LOGIN_PREFERENCE, Context.MODE_PRIVATE);
		SharedPreferences.Editor e = sharedPrefs.edit();
		e.remove(EMAIL_ID_KEY);
		e.remove(PASSWORD_KEY);
		e.commit();
	}
}
