package com.example.goonjnew;



import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.support.v4.app.NavUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class GuidanceActivity extends Activity {
	private static final String TAG = "AudioDemo";
	private static final String isPlaying = "Media is Playing"; 
	private static final String notPlaying = "Media has stopped Playing"; 
	long gtimestamp;
	MediaPlayer player;
	Button playerButton;

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
		gtimestamp = (new Date()).getTime();
	    this.setContentView(R.layout.activity_guidance) ;
	    
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    
	    Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra(DisplayGuidanceItems.EXTRA_MESSAGE);
        TextView txtProduct = (TextView) findViewById(R.id.story);
        txtProduct.setText(product);
        

	      /* 
	        TextView txtProduct = (TextView) findViewById(R.id.product_label);
	 
	        Intent i = getIntent();
	        // getting attached intent data
	        String product = i.getStringExtra(DisplayGuidanceItems.EXTRA_MESSAGE);
	        // displaying selected product name
	        txtProduct.setText(product);
	        */
	/*        Intent intent = getIntent();
		    String message = intent.getStringExtra(DisplayGuidanceItems.EXTRA_MESSAGE);

		    // Create the text view
		    TextView textView = new TextView(this);
		    textView.setTextSize(40);
		    textView.setText(message);
			setContentView(textView); */
	        //setContentView(R.layout.activity_guidance);
		
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_guidance, menu);
		return true;
	}
*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.make_call:
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:9818844417"));
				startActivity(callIntent);
				break;
			case R.id.play_audio:
				MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
				mediaPlayer.start();
				break;
		}
	}*/
	

	
	public void play(View v)
	{
		//String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		//String path = extStorageDirectory + "/Goonj/song.mp3";
		
		String path = GlobalData.fetched.audioPath;
		System.out.println("PATH IS :"+path);
		player = MediaPlayer.create(getApplicationContext(), Uri.parse(path));  

	    final Button test = (Button)this.findViewById(R.id.play_audio);
	    
	    player.start();
	    test.setText("स्टॉप");
	    
	    test.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	            if (player.isPlaying()) {
	                player.stop();
	                test.setText("प्ले ऑडियो");
	            } else {
	                player.start();
	            }
	        }
	    });
		
		
		/* old code
		player = MediaPlayer.create(getApplicationContext(), R.raw.song);
		//player.start();
		player.setLooping(false); // Set looping

		// Get the button from the view
		playerButton = (Button) this.findViewById(R.id.play_audio);
		playerButton.setText("Stop");
		playerButton.setOnClickListener(this);

		// Begin playing selected media
		//demoPlay();
		player.start();
		// Release media instance to system
		player.release(); */
	}
	
	public void call(View v)
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		String call_number = "tel:+91"+GlobalData.fetched.call_number;
		callIntent.setData(Uri.parse(call_number));
		startActivity(callIntent);
		
		
	}
	
	private class ListenToPhoneState extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {
        	//end_ts = System.currentTimeMillis();
            //System.out.println("telephony-example State changed: " + stateName(state));
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



public void onYN2RadioButtonClicked(View v)
{
    // Is the button now checked?
    boolean checked = ((RadioButton) v).isChecked();
    
    String[] projection = new String[] {CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
    Uri contacts =  CallLog.Calls.CONTENT_URI;
    Cursor managedCursor = getContentResolver().query(contacts, projection, null, null, CallLog.Calls.DATE + " DESC");
    long duration = this.getColumnData(managedCursor);
    
    // Check which radio button was clicked
    switch(v.getId()) {
        case R.id.gyes:
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
	            	this.finish();
            	}
            	else
            	{
            		Toast.makeText(this, "आपका फोन कॉल पर्याप्त अवधि का नहीं था", Toast.LENGTH_LONG).show();
            		//GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
	            	this.finish();
            	}
            }
            break;
        case R.id.gno:
            if (checked)
            {
            	//UPDATE put the story in ur db
            	//UPDATE add to guidance call items list
            	//GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
            	this.finish();
            }

               
     }
}

	
//	@Override
//	public void onPause() {
//		super.onPause();
//		player.pause();
//	}

	// Initiate media player pause
	private void demoPause(){
            player.pause();
            playerButton.setText("Play");
            Toast.makeText(this, notPlaying, Toast.LENGTH_LONG).show();
            Log.d(TAG, notPlaying);
	}
	
	// Initiate playing the media player
	private void demoPlay(){
            player.start();
            playerButton.setText("Stop");
            Toast.makeText(this, isPlaying, Toast.LENGTH_LONG).show();
            Log.d(TAG, isPlaying);
    }
	
	// Toggle between the play and pause
	private void playPause() {
		if(player.isPlaying()) {
		  demoPause();
		} else {
		  demoPlay();
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
	     System.out.println("GTIMESTAMP VALUE IS:"+gtimestamp);
	     if(date < gtimestamp)
	    	 return 0;
	     //System.out.print(bill);
	     if( date < (curr_time - 1800000))
	     	return 0;
	     return duration;
	   }

	
/*	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onClick: " + v);
		if (v.getId() == R.id.play_audio) {
			playPause();
		}
	}   */
	
	
}


