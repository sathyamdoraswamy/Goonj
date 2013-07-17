package com.example.goonjnew;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class ModerateActivity extends Activity {
	
	MediaPlayer player;
	
	@Override
	public void onResume()
	{
		super.onResume();
		if(GlobalData.done_moderating)
		{
			GlobalData.done_moderating = false;
			this.finish();
		}
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
		if(GlobalData.done_moderating)
		{
			GlobalData.done_moderating = false;
			this.finish();
		}
		GlobalData.dh.open();
		Intent intent = getIntent();
		
		setContentView(R.layout.activity_moderate);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_moderate, menu);
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
	
	public void play(View v)
	{
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		//String path = extStorageDirectory + "/Goonj/song.mp3";
		//instead of above line
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
	}
	
	public void onRadioButtonClicked(View v)
	{
	    // Is the button now checked?
	    boolean checked = ((RadioButton) v).isChecked();
	    
	    
	    // Check which radio button was clicked
	    switch(v.getId()) {
	        case R.id.junk:
	            if (checked)
	            {
	            	//update status field. End of Moderation
	            	//return to click to DisplayItems
	            	GlobalData.fetched.status_tag1 = "junk call";
	            	//UPDATE put back to sathyam db
	            	GlobalData.fetched.status_tag2 = "Moderated";
	            	System.out.println("NAMESPACE IS:"+GlobalData.namespace);
	            	GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
	            	this.finish();
	            }
	            break;
	        case R.id.guidance_needed:
	            if (checked)
	            {
	            	//update status field
	            	GlobalData.fetched.status_tag1 = "guidance call needed";
	            	Intent callIntent = new Intent(this, CallActivity.class);
	        		startActivity(callIntent);
	            }
	            break;
	        case R.id.audio_edit:
	            if (checked)
	            {
	            	//update status field
	            	GlobalData.fetched.status_tag1 = "needs audio editing";
	            	Intent assignIntent = new Intent(this, AssignActivity.class);
	            	startActivity(assignIntent);
	            }  
	            break;
	        case R.id.publish:
	            if (checked)
	            {
	            	//update status field
	            	GlobalData.fetched.status_tag1 = "publishable";
	            	Intent assignIntent = new Intent(this, AssignActivity.class);
	            	startActivity(assignIntent);
	            }
	               
	     }
	}
	
	
	public void assignLocation(View v)
	{
		Intent locationIntent = new Intent(this, LocationActivity.class);
		startActivity(locationIntent);
	}
	
	public void assignTopic(View v)
	{
		Intent topicIntent = new Intent(this, TopicActivity.class);
		startActivity(topicIntent);
	}
	
	public void assignStatus(View v)
	{
		Intent statusIntent = new Intent(this, StatusActivity.class);
		startActivity(statusIntent);
	}

}
