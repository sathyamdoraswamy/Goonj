package com.example.goonjnew;

import com.example.framework.Set;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import serversync.*;


public class TopicActivity extends ListActivity {

	ArrayList<Set> tl;
	
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
		//String[] topic_list = getResources().getStringArray(R.array.topics);
		
		tl = GlobalData.dh.getObjects("serversync.TopicChannel");
		
		
		if(tl==null)
		{
			Toast.makeText(this, "No Topic Items to show currently", Toast.LENGTH_LONG).show();	
		}
		else
		{
		String[] topic_list = new String[tl.size()];
		
		for(int i=0;i<tl.size();i++)
		{
			TopicChannel t = (TopicChannel)tl.get(i).obj;
			topic_list[i] =  t.topic;
		}
		
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_topic, R.id.topic, topic_list));
		
		ListView lv = getListView();
		
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
 
              // selected item
              String product = ((TextView) view).getText().toString();
              
              System.out.println("TOPIC SELECTED IS: "+ product);
              
              //UPDATE get name to number mapping for the location
              int num = 0;
      		  //GlobalData.fetched.location_id = num;
      		  //UPDATE make this available somewhere
              
              //update location field
              
              TopicChannel tc = (TopicChannel)tl.get(position).obj;
              
              System.out.println("POSITION SELECTED WAS: "+position);
              System.out.println("ID IF topic IS :"+ tc.topic_channel_id);
              
              StoryTcRelation stc = new StoryTcRelation();
              stc.stc_id = Integer.parseInt(getValue(PK_TOPIC)) ;
              int m = stc.stc_id + 1;
              putValue(PK_TOPIC, "" + m);
              //stc.topic_channel_id = position+1;
              stc.topic_channel_id = tc.topic_channel_id;
              stc.story_id = GlobalData.fetched.story_id;
              
              System.out.println("TOPIC SELECTED IS "+ stc.topic_channel_id);
              
              //Toast.makeText(this, "StoryTc id is "+ stc.stc_id, Toast.LENGTH_LONG).show();
              
              String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
              String d = "/Goonj/data";
              String s = "/Goonj/data/topic_"+ stc.story_id;
              File dir = new File(extStorageDirectory + d);
              
              try
              {
            	  if(!dir.exists())
            		  dir.mkdirs();
            	  
                  File f = new File(extStorageDirectory + s);
            	  
                  if(!f.exists())
                  	f.createNewFile();
                  
              	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
              	oos.writeObject(stc);
              	oos.close();
              }
              catch(Exception e)
              {
              	e.printStackTrace();
              }
              
              GlobalData.fetched.topicStoryPath = extStorageDirectory + s ;
              
              System.out.println("TOPIC PATH IS:"+ GlobalData.fetched.topicStoryPath);
              
 
          }
        }); 
        
        //num is assigned the issue id from the name to issue mapping
        int num = 0;
        
//        StoryTcRelation stc = new StoryTcRelation();
//        stc.stc_id = Integer.parseInt(getValue(PK_TOPIC)) ;
//        int m = stc.stc_id + 1;
//        putValue(PK_TOPIC, "" + m);
//        stc.topic_channel_id = num;
//        stc.story_id = GlobalData.fetched.story_id;
//        
//        Toast.makeText(this, "StoryTc id is "+ stc.stc_id, Toast.LENGTH_LONG).show();
//        
//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        
//        File f = new File(extStorageDirectory + "/Goonj/data/topic");
//
//        try
//        {
//            if(!f.exists())
//            	f.createNewFile();
//        	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
//        	oos.writeObject(stc);
//        	oos.close();
//        }
//        catch(Exception e)
//        {
//        	e.printStackTrace();
//        }
//        
//        GlobalData.fetched.topicStoryPath = extStorageDirectory + "/Goonj/data/topic" ;
//        
//        System.out.println("TOPIC PATH IS:"+ GlobalData.fetched.topicStoryPath);
		//tap what clicked and update db
		 
		 //this.finish();
		}//setContentView(R.layout.activity_topic);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	String ACCOUNT_PREFS_NAME = "prefs";
	String PK_TOPIC = "topiv";
	
	 private String getValue(String key)
	    {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME,
	0);
	        String value = prefs.getString(key, "1");
	        //String secret = prefs.getString(ACCESS_SECRET_NAME, null);
//	        if (key != null && secret != null)
//	        {
//	                String[] ret = new String[2];
//	                ret[0] = key;
//	                ret[1] = secret;
//	                return ret;
//	        }
//	        else
//	        {
//	                return null;
//	        }
	        return value;
	    }

	    /**
	     * Shows keeping the access keys returned from Trusted Authenticator
	in a local
	     * store, rather than storing user name & password, and
	re-authenticating each
	     * time (which is not to be done, ever).
	     */
	    private void putValue(String key, String value)
	    {
	        // Save the access key for later use
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME,
	0);
	        Editor edit = prefs.edit();
	        edit.putString(key, value);
	        //edit.putString(ACCESS_SECRET_NAME, secret);
	        edit.commit();
	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_topic, menu);
		return true;
	}

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

}
