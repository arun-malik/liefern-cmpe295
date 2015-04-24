package com.liefern.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.liefern.R;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Packages;
import com.liefern.webservices.models.WebServiceModel;

public class AddPackageActivity extends LiefernBaseActivity {
	Packages pack = new Packages();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopper_add_package_dialog);
		
		// adaptor = new PackageListAdapter(ShopperPage2Activity.this, R.layout.request_list_item, LiefernRepository.getInstance().getBuiltOrder().getPackages());
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
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.addPackage:
			EditText text = (EditText) findViewById(R.id.packageDescText);
			pack.setDescription( text.getText().toString() );
			text = (EditText) findViewById(R.id.packageContentText);
			pack.setContent( text.getText().toString() );
			text = (EditText) findViewById(R.id.packageSizeText);
			pack.setSize( text.getText().toString() );
			text = (EditText) findViewById(R.id.packageWeightText);
			if(text.getText().toString()!= null && !text.getText().toString().isEmpty()){
				pack.setWeight( Integer.parseInt(text.getText().toString()) );
			}
			LiefernRepository.getInstance().getBuiltOrder().addPackage(pack);
			finish();
			break;
		}
	}

}
