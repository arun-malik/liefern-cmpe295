package com.liefern.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.liefern.R;
import com.liefern.models.Address;
import com.liefern.models.LiefernRepository;
import com.liefern.models.Order;
import com.liefern.models.Packages;
import com.liefern.webservices.impl.WebsevicesImpl;
import com.liefern.webservices.models.WebServiceModel;

import java.util.ArrayList;
import java.util.List;

public class ViewRequest extends LiefernBaseActivity {



    private ArrayAdapter<String> itemAdapter;
    private static final String ACKNOWLEDGE = "Acknowledge";
    private static final String CANCEL = "Cancel";

    private static final int REQUEST_TYPE = 1;

    BaseAdapter adapter;
    private ListView lstRequests;

    private int buttonPress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_view_request);

        Button ackBtn = (Button) findViewById(R.id.acknowledgeButton);
        Button cancelBtn = (Button) findViewById(R.id.cancelButton);

        int position = 0;
        position = getIntent().getExtras().getInt("position");

        int btnRecognizer = getIntent().getExtras().getInt("currentActivity");

        Log.d("Arun Malik ", "Position :"+ position);
        Log.d("Arun Malik ", "btnRecognizer :"+ btnRecognizer);
        
        enableButton(btnRecognizer, ackBtn, cancelBtn);

        List<Order> orderList = LiefernRepository.getInstance().getRequestOrderList();

        Order currentOrder = null;
        if(position != 0) {
            currentOrder = orderList.get(position);
            Log.d("Arun Malik ", "Current OrderId :"+ currentOrder.getOrderId());
        }

        if(currentOrder != null) {

            ListView itemList = (ListView) findViewById(R.id.itemList);

            final int orderId = currentOrder.getOrderId();
            TextView orderText = (TextView) findViewById(R.id.orderId);
            orderText.setText(currentOrder.getOrderId());

            TextView fromAddrText = (TextView) findViewById(R.id.fromAddress);
            Address fromAddr = currentOrder.getFromlocation();
            fromAddrText.setText(fromAddr.getAddress1() + " " + fromAddr.getCity() + " " +fromAddr.getCountry());

            TextView toAddrText = (TextView) findViewById(R.id.toAddress);
            Address toAddr = currentOrder.getTolocation();
            toAddrText.setText(toAddr.getAddress1() + " " + toAddr.getCity() + " " + toAddr.getCountry());

            // To populate the ListView with the packages(items) in that order

            List<Packages> packageList = currentOrder.getPackages();
            ArrayList<String> itemArrayList = new ArrayList<String>();
            for(Packages singlePackage : packageList)
            {
                itemArrayList.add(singlePackage.getContent());
            }
            itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,itemArrayList);
            itemList.setAdapter(itemAdapter);

            TextView estCostText = (TextView) findViewById(R.id.estimatedCost);
            estCostText.setText(currentOrder.getSuggestAmount());

            TextView custQuoteText = (TextView) findViewById(R.id.customerQuote);
            custQuoteText.setText(currentOrder.getCustomerAmount());


            ackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAckButtonPress(orderId);
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCancelButtonPress(orderId);
                }
            });

        }


        else
        {
            // when there is no information to show
            ackBtn.setVisibility(View.INVISIBLE);
            cancelBtn.setVisibility(View.INVISIBLE);
        }


    }

    private void enableButton(int btnRecognizer, Button ackBtn, Button cancelBtn)
    {
        switch(btnRecognizer)
        {
            // Delivery-1 and Request-3
            case 1:
            case 3:
                cancelBtn.setVisibility(View.VISIBLE);
                ackBtn.setEnabled(false);
                break;
            // Receipt-2
            case 2:
                ackBtn.setVisibility(View.INVISIBLE);
                cancelBtn.setVisibility(View.INVISIBLE);
                ackBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                break;
            // Traveler- 4 to acknowledge
            case 4:
                ackBtn.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void onAckButtonPress(int orderId) {

        // Acknowledge
        buttonPress = 1;
        execute(orderId);
    }

    private void onCancelButtonPress(int orderId) {

        // Cancel
        buttonPress = 2;
        execute(orderId);
    }


    /**
     * Holds implementation of web service which you want to execute in background thread.
     * @return Result
     * @throws Exception
     */
    public WebServiceModel processService() throws Exception{
        WebsevicesImpl wsImpl = new WebsevicesImpl();
        int orderId = Integer.parseInt(this.getRequestType().toString());

        if(buttonPress == 1) {
            return wsImpl.acknowledgeOrder(orderId);
        }
        else
        {
            return wsImpl.cancelOrder(orderId);
        }
    }

    @Override
    public void notifyWebResponse(WebServiceModel model) {

        adapter = new RequestListAdapter(this, R.layout.request_list_item, LiefernRepository.getInstance().getRequestOrderList(), REQUEST_TYPE);
        lstRequests.setAdapter(adapter);
    }

    /**
     * Notifies response error.
     * @param model
     */
    public void notifyWebResponseError(WebServiceModel model){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_view_request, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
