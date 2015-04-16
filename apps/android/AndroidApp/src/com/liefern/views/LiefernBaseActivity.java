package com.liefern.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.liefern.R;
import com.liefern.webservices.models.WebServiceModel;

public abstract class LiefernBaseActivity extends Activity {
	static final String TAG = "LiefernBaseActivity";
	
	private static final int LOGOUT_INTENT_REQUEST_ID = 1;
	private static final int REQUEST_INTENT_REQUEST_ID = 2;
	
	protected LiefernBaseActivity instance;
	private static LiefernAsyncTask asyncTask;
	
	private ListView sliderList;
	private ActionBarDrawerToggle drawerToggle;
	
	private Object requestType;
	
	private DrawerLayout drawerLayout;
	
	private CharSequence drawerTitle;
    private CharSequence title;
    
    private LinearLayout contentLayout;
    private boolean errorDialogShowing = false;

    protected static final String LOGOUT_ACTION = "com.liefern.ACTION_LOGOUT";
    
    private static final IntentFilter logoutFilter = new IntentFilter(LOGOUT_ACTION);
    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};
	
	public LiefernAsyncTask getAsyncTask() {
		return asyncTask;
	}
	
	public void execute(Object requestType) {
		this.setRequestType(requestType);
		execute();
	}
	
	public void execute() {
		try {
			if(!errorDialogShowing) {
				asyncTask = new LiefernAsyncTask(this);
				asyncTask.execute(new Object());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		instance = this;
		LocalBroadcastManager.getInstance(this).registerReceiver(logoutReceiver, logoutFilter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(logoutReceiver);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		if(this instanceof LoginActivity || this instanceof SignUpActivity) {
			super.setContentView(layoutResID);
			return;
		}
		
		// Set base UI template
		super.setContentView(R.layout.activity_main);
		
		// Add inflated UI to container
		contentLayout = (LinearLayout) findViewById(R.id.content_layout);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View oView = inflater.inflate(layoutResID, null);
		
		oView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		contentLayout.addView(oView);
		
		
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			title = drawerTitle = getTitle();
	        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        sliderList = (ListView) findViewById(R.id.slider_list);

	        // set a custom shadow that overlays the main content when the drawer opens
	        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
	        
	        // set up the drawer's list view with items and click listener
	        sliderList.setAdapter(SliderMenuListAdapter.getInstance(this));
	        sliderList.setOnItemClickListener(new DrawerItemClickListener());

	        // enable ActionBar app icon to behave as action to toggle nav drawer
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	        

	        drawerToggle = new ActionBarDrawerToggle(
	                this,						// host Activity 
	                drawerLayout,				// DrawerLayout object 
	                R.drawable.ic_launcher,		// nav drawer image to replace 'Up' caret 
	                R.string.drawer_open,		// "open drawer" description for accessibility 
	                R.string.drawer_close		// "close drawer" description for accessibility 
	                ) {
	            public void onDrawerClosed(View view) {
	                getActionBar().setTitle(title);
	                invalidateOptionsMenu();
	            }

	            public void onDrawerOpened(View drawerView) {
	                getActionBar().setTitle(drawerTitle);
	                invalidateOptionsMenu();
	            }
	        };
	        drawerLayout.setDrawerListener(drawerToggle);
		}
	}

    @SuppressWarnings("deprecation")
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
	        if (drawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
    	}
    	return super.onOptionsItemSelected(item);
    }
	
	
	/**
	 * Displays dialog to show error
	 * @param msg Error message
	 */
	public void showErrorDialog(final String msg) {
		errorDialogShowing = true;
		AlertDialog.Builder builder = new AlertDialog.Builder(instance);
		builder.setTitle(instance.getString(R.string.attention));
		builder.setMessage(msg);
		builder.setCancelable(true);
		builder.setPositiveButton(instance.getString(R.string.ok),
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				errorDialogShowing = false;
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/**
	 * Displays dialog to show error
	 * @param msg Error message
	 */
	public void showSuccessDialog(final String msg) {

		AlertDialog.Builder builder = new AlertDialog.Builder(instance);
		builder.setMessage(msg);
		builder.setCancelable(true);
		builder.setPositiveButton(instance.getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						dialog.cancel();
						finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// Inform Pause event to AsyncTask
		if (asyncTask != null && asyncTask.isInProgress()) {
			asyncTask.doPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Inform Resume event to AsyncTask
		if (asyncTask != null && asyncTask.isInProgress()) {
			asyncTask.doResume();
		}
	}
	
	/**
	 * Holds implementation of web service which you want to execute in background thread.
	 * @return Result
	 * @throws Exception
	 */
	public abstract WebServiceModel processService() throws Exception;
	
	/**
	 * Notifies response.
	 * @param model
	 */
	public abstract void notifyWebResponse(WebServiceModel model);
	
	/**
	 * Notifies response error.
	 * @param model
	 */
	public abstract void notifyWebResponseError(WebServiceModel model);
	
	public LiefernBaseActivity getActivity() {
		return instance;
	}
	
	
	public void onClick(View v) {
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case LOGOUT_INTENT_REQUEST_ID:
			if(resultCode == RESULT_OK) {
				// LocalBroadcastManager is used as we want this broadcast to be application internal
				LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(LOGOUT_ACTION));
			}
			break;
			
//		case REQUEST_INTENT_REQUEST_ID:
//			if(resultCode == RESULT_OK) {
//				Intent requestIntent = new Intent(this, RequestActivity.class);
//				startActivity(requestIntent);
//			}
//			break;
			
		}
	}
	
	
	/**
	 *  The click listener for ListView in the slider menu
	 */
	class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	if(sliderList != null && drawerLayout != null) {
        		sliderList.setItemChecked(position, true);
            	setTitle(SliderMenuListAdapter.sliderMenuItems[position]);
                drawerLayout.closeDrawer(sliderList);
                handleMenuClick(position);
        	}
        }
    }
	
	/**
	 * Handles slider click. Open appropriate activity as per clicked slider list item position
	 * @param position
	 */
	private void handleMenuClick(int position) {
		switch(SliderMenuListAdapter.sliderMenuItems[position]) {
    		
    	case SliderMenuListAdapter.LOGOUT:
    		Intent oLogoutIntent = new Intent(instance, LogoutActivity.class);
    		startActivityForResult(oLogoutIntent, LOGOUT_INTENT_REQUEST_ID);
    		break;
    		
    	case SliderMenuListAdapter.REQUEST:
    		Intent oRequestIntent = new Intent(instance, RequestActivity.class);
    		startActivity(oRequestIntent);
    		break;
    		
    	default:
    		break;
    	}
	}

	public Object getRequestType() {
		return requestType;
	}

	public void setRequestType(Object requestType) {
		this.requestType = requestType;
	}
}