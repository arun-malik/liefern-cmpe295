package com.liefern.widgets;

import com.liefern.R;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomDialog extends ProgressDialog {

	public CustomDialog(Context context) {
		super(context, R.style.NewDialog);
	}

}
