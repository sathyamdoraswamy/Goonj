package com.example.goonjnew;

import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class CallActivity extends Activity {

	ListenToPhoneState listener;
	long start_ts = 0;
	long end_ts = 0;
	boolean picked_up = false;
	long timestamp;
	
	@Override
	public void onResume()
	{
		super.onResume();
		GlobalData.dh.open();
	}


	@Override
	public void onPause()
	{
		super.onPause();
		GlobalData.dh.close();
	}


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GlobalData.dh.open();
		timestamp = (new Date()).getTime();
		setContentView(R.layout.activity_call);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_call, menu);
		return true;
	}
	
	public void call(View v)
	{
		try
		{
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			String test = GlobalData.fetched.call_number;
			if(test.equals("EMPTY"))
			{
				Toast.makeText(this, "नंबर उपलब्ध नहीं है", Toast.LENGTH_LONG).show();
				GlobalData.fetched.status_tag1 = "guidance call needed, no number";
            	//UPDATE put story item back
            	GlobalData.fetched.status_tag2 = "Moderated";
            	//GlobalData.fetched.gcall_duration = (int)duration;
            	GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
            	GlobalData.done_moderating = true;
            	this.finish();
				
			}
			else
			{
				String call_number = "tel:+91"+ GlobalData.fetched.call_number;
				callIntent.setData(Uri.parse(call_number));
			//start_ts = System.currentTimeMillis();
				startActivity(callIntent);
			}
			//TelephonyManager tManager = (TelephonyManager) 
		    //getSystemService(Context.TELEPHONY_SERVICE);
		    //listener = new ListenToPhoneState();
		    //tManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		 } catch (ActivityNotFoundException activityException) {
		            System.out.println("telephony-example Call failed" + activityException);
		        }
		}
	
	
	 private class ListenToPhoneState extends PhoneStateListener {

	        public void onCallStateChanged(int state, String incomingNumber) {
	        	//end_ts = System.currentTimeMillis();
	        	if(picked_up)
	        	{
	        		if(stateName(state).equals("Idle"))
	        		{
	        			//call has ended now
	        			end_ts = System.currentTimeMillis();
	    	            System.out.println("CALL DURATION WAS: "+ (end_ts-start_ts));
	    	            picked_up =false;
	    	        }
	        	}
	        	
	        	if(stateName(state).equals("Off hook"))
	        	{
	        		picked_up = true;
	        		start_ts = System.currentTimeMillis();
	        	}
	        	System.out.println("telephony-example State changed: " + stateName(state));
	            //System.out.println("CALL DURATION WAS: "+ (end_ts-start_ts));
	        }

	        String stateName(int state) {
	            switch (state) {
	                case TelephonyManager.CALL_STATE_IDLE: return "Idle";
	                case TelephonyManager.CALL_STATE_OFFHOOK: return "Off hook";
	                case TelephonyManager.CALL_STATE_RINGING: return "Ringing";
	            }
	            return Integer.toString(state);
	        }
	    }
	
	
	
	public void onYNRadioButtonClicked(View v)
	{
	    // Is the button now checked?
	    boolean checked = ((RadioButton) v).isChecked();
	    
	    String[] projection = new String[] {CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
	    Uri contacts =  CallLog.Calls.CONTENT_URI;
	    Cursor managedCursor = getContentResolver().query(contacts, projection, null, null, CallLog.Calls.DATE + " DESC");
	    long duration = this.getColumnData(managedCursor);
	    
	    System.out.println("CALL DURATION THROUGH LOGS WAS : " + duration);
	    
	    
	    // Check which radio button was clicked
	    switch(v.getId()) {
	        case R.id.yes:
	            if (checked)
	            {
	            	//do Nothing. End of Moderation
	            	if(duration>0)
	            	{
	            		GlobalData.fetched.status_tag1 = "guidance call given";
		            	//UPDATE put story item back
		            	GlobalData.fetched.status_tag2 = "Moderated";
		            	GlobalData.fetched.gcall_duration = (int)duration;
		            	GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
		            	GlobalData.done_moderating = true;
		            	this.finish();
	            	}
	            	else
	            	{
	            		Toast.makeText(this, "आपका फोन कॉल पर्याप्त अवधि का नहीं था", Toast.LENGTH_LONG).show();
	            		GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
		            	GlobalData.done_moderating = true;
	            		this.finish();
	            	}
	            
	            }
	            break;
	        case R.id.no:
	            if (checked)
	            {
	            	//UPDATE put the story in ur db
	            	//UPDATE add to guidance call items list
	            	GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
	            	GlobalData.done_moderating = true;
	            	this.finish();
	            }

	               
	     }
	}
	
	private long getColumnData(Cursor cur)
	   {
	     String bill = "";
	     long duration = 0;
	     long curr_time = 0;
	     long date = 0;
	     try
	     {
	        if (cur.moveToFirst()) 
	        {
	          String type;
	          //long date;
	          String number;           
	          //long duration;
	          int typeColumn = cur.getColumnIndex(CallLog.Calls.TYPE);
	          int dateColumn = cur.getColumnIndex(CallLog.Calls.DATE);
	          int numberColumn = cur.getColumnIndex(CallLog.Calls.NUMBER);
	          int durationColumn = cur.getColumnIndex(CallLog.Calls.DURATION);           
	          System.out.println("Reading Call Details: ");
	          //do 
	          //{
	              type = cur.getString(typeColumn);
	              date = cur.getLong(dateColumn);
	              number = cur.getString(numberColumn);
	              duration = cur.getLong(durationColumn);
	              curr_time = (new Date()).getTime();
	              System.out.println("DATE OF LAST CALL IS: "+date);
	              System.out.println("CURRENT TIME IS: "+ curr_time);
	              
	              //if(type.equals("2") && duration!=0)
	              //{
	                //      String temp = new Date(date) + "\t" + number + "\t" + Math.ceil(duration/60.0) + "\n";
	                 //     System.out.print(temp);
	                 //     bill += temp;
	             // }
	                                     
	          //} 
	          //while (cur.moveToNext());
	        }
	     } 
	     finally
	     {
	             cur.close();
	     }
	     System.out.println("TIME OF LAST CALL MADE WAS:"+ date);
	     System.out.println("TIMESTAMP VALUE IS:"+timestamp);
	     if(date < timestamp)
	    	 return 0;
	     //System.out.print(bill);
	     if( date < (curr_time - 1800000))
	     	return 0;
	     return duration;
	   }

}
