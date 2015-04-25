package com.liefern.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.liefern.R;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

public class BGWorker extends Thread {

	private SSPollBGService _parent;
	private SBCastRecvr  recv = null;
	private HttpURLConnection urlc = null; 
	

	public BGWorker(SSPollBGService parent)
	{
		_parent = parent;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
		recv = new SBCastRecvr();
		_parent.registerReceiver(recv, new IntentFilter(Intent.ACTION_TIME_TICK) );
		
//		_CreateHttpConnection();
		
		while(_parent.get_ThreadRun())
		{
			JSONObject data = _readCommand();
			
			if(data != null )
			{
				_processCommand(data);
			}
			

			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private class PostReturn
	{
		byte[] data;
		/**
		 * @return the data
		 */
		public byte[] getData() {
			return data;
		}
		/**
		 * @param data the data to set
		 */
		public void setData(byte[] data) {
			this.data = data;
		}
		/**
		 * @return the len
		 */
		public int getLen() {
			return len;
		}
		/**
		 * @param len the len to set
		 */
		public void setLen(int len) {
			this.len = len;
		}
		int    len;
		
		public PostReturn()
		{
			len = 0;
		}
	};
	
	private PostReturn DoPost(byte[] data)
	{
		
		HttpURLConnection urlc = _CreateHttpConnection();
		urlc.setFixedLengthStreamingMode(data.length);
		urlc.setDoOutput(true);
		
		int response_code =0;
		int bytesRead = -1;
		
		byte[] inData = null;
		try
		{
			
		    OutputStream os = urlc.getOutputStream();
			os.write(data);
			response_code = urlc.getResponseCode();
			
			if(response_code != 200)
			{
				throw new Exception("Got response code:" + response_code);
			}
			os.close();
		}
		catch(Exception ex)
		{
			Log.e("Communicator","Failed sending post:" + ex.getLocalizedMessage());
			return null;
		} 
		
		int content_length  = -1;
		try
		{

			int bufferSize = 4096;
			inData = new byte[bufferSize];
			InputStream is = urlc.getInputStream();
			 content_length = urlc.getContentLength();
			int indexCtr =0;
			do
			{
				bytesRead = is.read(inData,indexCtr, bufferSize - indexCtr );
				
				if(bytesRead > 0)
				{
					indexCtr += bytesRead;
					
					if(indexCtr >= bufferSize)
					{
						byte[] _temp = inData;
						bufferSize = bufferSize + 4096;
						inData = new byte[bufferSize];
						System.arraycopy(_temp, 0, inData, 0, _temp.length);
					}
				}
				
			} while(bytesRead >= 0);
			
			Log.e("Data:" ,"Content-Length:" + content_length);// + " " +  new String(inData));
			
			if(indexCtr <= 0)
			{
				inData = null;
			}
			
			inData[content_length] = 0x00;
			is.close();
		}
		catch(Exception ex)
		{
			Log.e("Communicator", "Failed reading response: " + ex.getLocalizedMessage());
		}
		finally
		{
			urlc.disconnect();
		}
		
		PostReturn pr = new PostReturn();
		pr.setData(inData);
		pr.setLen(content_length);
		return pr; 
	}
	
	private HttpURLConnection _CreateHttpConnection() {
		
		URL u;
		HttpURLConnection urlc;
		try {
			
			Resources res = _parent.getApplicationContext().getResources();
			String  urlmain =res.getString(R.string.UrlMain);
			u = new URL( urlmain + "/Geo");
			urlc = (HttpURLConnection) u.openConnection();
			urlc.setInstanceFollowRedirects(true);
			urlc.setRequestMethod("POST");
			return urlc;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Communicator", e.getLocalizedMessage() );
			return null;
		}
	}
	
	private void _processCommand(JSONObject data) {

		try
		{
			
			
		}
		catch(Exception ex)
		{
			Log.e("SSPOLBG", "Error in process command : " + ex.getLocalizedMessage());
		}
		return;
	}
	
		private void _SendData(JSONObject retval) {
		
		try
		{
		final String inti = "&XDEBUG_SESSION_START=netbeans-xdebug";
		retval.put("GeoDATA:", 1);
	    String optStr = "CMD=" + retval.toString() + inti; 
		byte [] data = optStr.getBytes(); //jo.toString().getBytes();
		
		PostReturn pr = DoPost( data );
			if(pr != null && pr.getData()!= null)
			{
				data = pr.getData();
				String s = new String(data,0,pr.getLen());
				JSONTokener jt = new JSONTokener(s);
				while(jt.more())
				{
					Object j = jt.nextValue();

					if (j == null) {
						break;
					}

					if (j instanceof JSONObject) {
						_processCommand((JSONObject) j);
					} else if (j instanceof JSONArray) {
						JSONArray ja = (JSONArray) j;
						for (int z = 0; z < ja.length(); z++) {
							_processCommand(ja.getJSONObject(z));
						}
					}
				}
			}
		}
		catch (JSONException e) {
			Log.w("SendData", "JSON Error:" + e.getLocalizedMessage());
		}
		catch(Exception ex)
		{
			Log.e("SendData", ex.getLocalizedMessage());
		}
	}
	
	
	private JSONObject _readCommand() {
		JSONObject jo = new JSONObject();
		try
		{
			jo.put("CMD", 1);
			_SendData(jo);
		}
		catch(Exception ex)
		{
			Log.e("Communicator", "Error forming JSON Request: " + ex.getLocalizedMessage());
		}
		return null;
	}
}
