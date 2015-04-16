package com.liefern.utility;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liefern.LiefernApplication;

public final class Utility {
	
	public static boolean validate(final View view, final int errorMessageResourceId) {
		if (view instanceof EditText) {
			EditText editText = (EditText) view;
			String text = editText.getText().toString().trim();
			if (!TextUtils.isEmpty(text)) {
				return true;
			} else {
				showToast("Please Enter " + view.getContext().getString(errorMessageResourceId));
				editText.setText("");
				editText.requestFocus();
				return false;
			}
		}
		return false;
	}

	public static void showToast(String message) {
		Toast.makeText(LiefernApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
	}

}
