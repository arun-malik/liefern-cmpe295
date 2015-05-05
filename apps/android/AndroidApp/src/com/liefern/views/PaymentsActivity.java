package com.liefern.views;

import java.text.SimpleDateFormat;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;

public class PaymentsActivity extends LiefernBaseActivity {
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private List<ApplicationInfo> mAppList;
	BaseAdapter adaptor;
	private SwipeMenuListView lstRequests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payments);
		
		mAppList = getPackageManager().getInstalledApplications(0);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

		    @Override
		    public void create(SwipeMenu menu) {
		    	// create "delete" item
		        SwipeMenuItem deleteItem = new SwipeMenuItem(
		                getApplicationContext());
		        // set item background
		        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
		                0x3F, 0x25)));
		        // set item width
		        deleteItem.setWidth(dp2px(90));
		        // set a icon
		        deleteItem.setIcon(R.drawable.ic_delete);
		        // add to menu
		        menu.addMenuItem(deleteItem);
		    }

		};

		lstRequests = (SwipeMenuListView) findViewById(R.id.cardList);//findViewById(R.id.packageList);
		
		// set creator
		lstRequests.setMenuCreator(creator);
		
		lstRequests.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {

				case 0:
					// delete
					delete(position);
					mAppList.remove(position);
					adaptor.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
		
		execute();
	}
	
	private void delete(int position) {
		LiefernRepository.getInstance().setDeleteCard(LiefernRepository.getInstance().getPaymentCardList().get(position));
		LiefernRepository.getInstance().getPaymentCardList().remove(position);
		execute();
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payments, menu);
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

	public void addPaymentsDialog(View view) {
		Intent i = new Intent(this, AddPaymentCardActivity.class);
		startActivityForResult(i, 1);
		
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            execute();
	        }
	    }
	}

	@Override
	public WebServiceModel processService() throws Exception {
		WebsevicesImpl wsImpl = new WebsevicesImpl();
		if(LiefernRepository.getInstance().getDeleteCard() != null){
			return wsImpl.deletePaymentCard(LiefernRepository.getInstance().getDeleteCard().getPaymentId());
		} else if(LiefernRepository.getInstance().getNewCard() == null){
			return wsImpl.requestPaymentCards();
		} else {
			return wsImpl.postPaymentCard(LiefernRepository.getInstance().getNewCard());
		}
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		 
		 adaptor = new PaymentCardListAdaptor(this, R.layout.request_list_item, LiefernRepository.getInstance().getPaymentCardList());
	     lstRequests.setAdapter(adaptor);
	     LiefernRepository.getInstance().setNewCard(null);
	     LiefernRepository.getInstance().setDeleteCard(null);
	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub

	}
}
