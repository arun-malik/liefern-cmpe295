package com.liefern.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.User;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class ProfileActivity extends LiefernBaseActivity {
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setContent();
		
	}

	private void setContent() {
		User usr = LiefernRepository.getInstance().getLoggedInUser();
		EditText text = (EditText) findViewById(R.id.profName);
		text.setText(usr.getName());
		text = (EditText) findViewById(R.id.profileEmailText);
		text.setText(usr.getEmailId());
		text = (EditText) findViewById(R.id.profilePhoneText);
		text.setText(usr.getMobile());
		text = (EditText) findViewById(R.id.profileAddress1Text);
		text.setText(usr.getAddress().getAddress1());
		text = (EditText) findViewById(R.id.profileAddress2Text);
		text.setText(usr.getAddress().getAddress2());
		text = (EditText) findViewById(R.id.profileCityText);
		text.setText(usr.getAddress().getCity());
		text = (EditText) findViewById(R.id.profileCountryText);
		text.setText(usr.getAddress().getCountry());
		text = (EditText) findViewById(R.id.profileStateText);
		text.setText(usr.getAddress().getState());
		text = (EditText) findViewById(R.id.profZip);
		Log.d("zip on profile", ""+usr.getAddress().getZip());
		System.out.println("zip on profile"+usr.getAddress().getZip());
		if(usr.getAddress().getZip() != 0) {
			Log.d("zip on profile", ""+usr.getAddress().getZip());
			System.out.println("zip on profile"+usr.getAddress().getZip());
			text.setText(usr.getAddress().getZip()+"");
		}
		
		
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar.setRating(usr.getAvgRating());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		return wsImpl.updateProfile(setUser());
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		Toast.makeText(getApplicationContext(), "Profile updated successfully !",
				Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		Toast.makeText(getApplicationContext(), "Profile update failed. Please try again.",
				Toast.LENGTH_LONG).show();
		
	}
	
	public void updateProfileButton(View view) {
		execute();
	}
	
	public User setUser() {
		User usr = LiefernRepository.getInstance().getLoggedInUser();
		EditText text = (EditText) findViewById(R.id.profName);
		usr.setName(text.getText().toString());
		text = (EditText) findViewById(R.id.profileAddress1Text);
		usr.getAddress().setAddress1(text.getText().toString());
		text = (EditText) findViewById(R.id.profileAddress2Text);
		usr.getAddress().setAddress2(text.getText().toString());
		text = (EditText) findViewById(R.id.profileCityText);
		usr.getAddress().setCity(text.getText().toString());
		text = (EditText) findViewById(R.id.profileCountryText);
		usr.getAddress().setCountry(text.getText().toString());
		text = (EditText) findViewById(R.id.profileStateText);
		usr.getAddress().setState(text.getText().toString());
		text = (EditText) findViewById(R.id.profZip);
		if(text.getText()!=null && !text.getText().toString().isEmpty())
			usr.getAddress().setZip(Integer.parseInt(text.getText().toString()));
		usr.getAddress().setHome(true);
		usr.getAddress().setUser(LiefernRepository.getInstance().getLoggedInUser().getUserId());
		try {
			if(usr.getAddress().getCreatedDate() == null) {
				usr.getAddress().setCreatedDate(sdf.parse(sdf.format(new Date())));
			}else{
				usr.getAddress().setModifiedDate(sdf.parse(sdf.format(new Date())));
			}
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usr;
	}
}
