package com.liefern.views;

import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Packages;
import com.liefern.webservices.models.WebServiceModel;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import android.view.View.OnClickListener;

public class ShopperPage2Activity extends LiefernBaseActivity {

	private List<ApplicationInfo> mAppList;
	BaseAdapter adaptor;
	private SwipeMenuListView lstRequests;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopper_page2);
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

		lstRequests = (SwipeMenuListView) findViewById(R.id.listViewSwipe);//findViewById(R.id.packageList);
		
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
	}
	
	private void delete(int position) {
		LiefernRepository.getInstance().getBuiltOrder().getPackages().remove(position);
	}
	
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	public void nextShopperPage3(View view) {
		Intent intent = new Intent(this, ShopperPage3Activity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(LiefernRepository.getInstance().getBuiltOrder().getPackages() != null && LiefernRepository.getInstance().getBuiltOrder().getPackages().size() > 0) {
			adaptor = new PackageListAdapter(ShopperPage2Activity.this, R.layout.request_list_item, LiefernRepository.getInstance().getBuiltOrder().getPackages());
			lstRequests.setAdapter(adaptor);
			findViewById(R.id.packageListLayout).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.packageListLayout).setVisibility(View.GONE);
		}
	}

	public void addPackagesButton(View view) {
		Intent intent = new Intent(this, AddPackageActivity.class);
		startActivity(intent);
		// custom dialog
		/*final Dialog dialog = new Dialog(ShopperPage2Activity.this);
		dialog.setContentView(R.layout.activity_shopper_add_package_dialog);
		dialog.setTitle("Add Package");


		Button dialogButton = (Button) dialog.findViewById(R.id.addPackDialog);
		dialogButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Packages pack = new Packages();
				EditText text = (EditText) dialog.findViewById(R.id.packageDescText);
				pack.setDescription( text.getText().toString() );
				text = (EditText) dialog.findViewById(R.id.packageContentText);
				pack.setContent( text.getText().toString() );
				text = (EditText) dialog.findViewById(R.id.packageSizeText);
				pack.setSize( text.getText().toString() );
				text = (EditText) dialog.findViewById(R.id.packageWeightText);
				if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
					pack.setWeight( Integer.parseInt(text.getText().toString()) );
				}

				LiefernRepository.getInstance().getBuiltOrder().addPackage(pack);
				adaptor = new PackageListAdapter(ShopperPage2Activity.this, R.layout.request_list_item, LiefernRepository.getInstance().getBuiltOrder().getPackages());
				lstRequests.setAdapter(adaptor);
				dialog.dismiss();
			}
		});
		dialog.show();*/

	}

	@Override
	public WebServiceModel processService() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyWebResponse(WebServiceModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyWebResponseError(WebServiceModel model) {
		// TODO Auto-generated method stub

	}
}
