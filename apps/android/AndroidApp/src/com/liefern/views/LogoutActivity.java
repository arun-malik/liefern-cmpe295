package com.liefern.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

public class LogoutActivity extends LiefernBaseActivity {
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		showLogOutDialog();
	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.logOut();
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		dialog.cancel();
		dialog = null;
		LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGOUT_ACTION));
		LiefernRepository.getInstance().setAuthToken(null);
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		dialog.cancel();
		dialog = null;
//		finish();
	}
	
	public void showLogOutDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(instance);
		builder.setMessage("Do you want log out?");
		builder.setCancelable(false);
		builder.setPositiveButton(instance.getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						execute();
					}
				});
		builder.setNegativeButton(instance.getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						dialog.cancel();
						finish();
					}
				});
		dialog = builder.create();
		dialog.show();
	}

}
