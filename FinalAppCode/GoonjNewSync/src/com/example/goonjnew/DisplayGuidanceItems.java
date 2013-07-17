package com.example.goonjnew;

import java.util.ArrayList;

import com.example.framework.Set;

import serversync.Story;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayGuidanceItems extends ListActivity implements OnItemClickListener {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	ArrayList<Story> stories = new ArrayList<Story>();
	ArrayList<Set> st;
	ArrayList<Integer> orig_pos=new ArrayList<Integer>();
	


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
		System.out.println("ENTERING GCALL FUNCTION");
		st = GlobalData.dh.getObjects("serversync.Story");
		System.out.println("FETCHED THE LIST OF STORIES NEEDING GCALL");
		if(st == null)
		{
			System.out.println("ST is NULL!!");
			Toast.makeText(this, "गाइडेन्स कॉल देने के ली कोई नया आइटम नहीं है", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else
		{
			System.out.println("SIZE OF ST " + st.size());
		
		int i;
		
		ArrayList<String> gnStories = new ArrayList<String>();
		
		for(i=0;i<st.size();i++)
		{
			Story temp = (Story)st.get(i).obj;
			if(temp==null)
				System.out.println("TEMP IS NULL!!");
			else if(temp.status_tag1==null || temp.status_tag2==null)
			{
				System.out.println("MODERATED STORY RESYNCED");
			}
			
			else if(temp.status_tag2.equals("Assigned") && temp.status_tag1.equals("guidance call needed"))
			{
				System.out.println("STORY "+temp.story_id + "HAS STATUS TAG1= "+ temp.status_tag1);
				System.out.println("STORY "+temp.story_id + "HAS STATUS TAG2= "+ temp.status_tag2);
				stories.add(temp);
				orig_pos.add(i);
				gnStories.add("आइटम " + temp.story_id);
			}
			
		}
		
		if(stories.size()==0)
		{
			System.out.println("ST is NULL!!");
			Toast.makeText(this, "गाइडेन्स कॉल देने के ली कोई नया आइटम नहीं है", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else
		{
		System.out.println("OUT FROM LOOP");
		if(gnStories==null)
			System.out.println("GNSTORIES IS NULL");
		else{
			for(i=0;i<gnStories.size();i++)
				System.out.println("ENTRY "+ i +"IS "+ gnStories.get(i));
		}
		
		//gnStories = getResources().getStringArray(R.array.guidance_needing_stories);
		//String[] s = (String[]) gnStories.toArray();
		
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_display_guidance_items, R.id.label, gnStories));
		
		 ListView lv = getListView();
		 
		 
		 
	        // listening to single list item on click
	 /*       lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	 
	              // selected item
	              String product = ((TextView) view).getText().toString();
	 
	              int num =  Integer.parseInt(product.substring(product.indexOf(" ")+1));
	              System.out.println("NUM IS FOUND TO BE: "+ num);
	              GlobalData.fetched = stories[position];
	              // Launching new Activity on selecting single List Item
	              Intent i = new Intent(getApplicationContext(), GuidanceActivity.class);
	              // sending data to new activity
	              i.putExtra(EXTRA_MESSAGE, product);
	              startActivity(i);
	 
	          }
	        }); */
		 lv.setOnItemClickListener(this);
		}
		}
		
		//setContentView(R.layout.activity_display_guidance_items);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	/* @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        super.onListItemClick(l, v, position, id);
	        // Get the item that was clicked
	        Object o = this.getListAdapter().getItem(position);
	        String keyword = o.toString();
	        String product = ((TextView) v).getText().toString();
	        Intent i = new Intent(getApplicationContext(), GuidanceActivity.class);
	        i.putExtra(EXTRA_MESSAGE, product);
         startActivity(i);
	 }*/
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_guidance_items, menu);
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
	
	public void giveGuidance(View view)
	{
		Intent intent = new Intent(this, GuidanceActivity.class);
		startActivity(intent);
	}

	
	 public void onItemClick(AdapterView<?> parent, View view,
             int position, long id) {

             // selected item
             String product = ((TextView) view).getText().toString();

             int num =  Integer.parseInt(product.substring(product.indexOf(" ")+1));
             System.out.println("NUM IS FOUND TO BE: "+ num);
             GlobalData.fetched = stories.get(position);
             //GlobalData.groupid = stories.get(position).groupid;
             GlobalData.groupid = st.get(orig_pos.get(position)).groupid;
             GlobalData.namespace = st.get(orig_pos.get(position)).namespace;
             if(GlobalData.fetched == null)
            	 System.out.println("THE CLICKED STORY WAS FOUND NULL");
             // Launching new Activity on selecting single List Item
             Intent i = new Intent(getApplicationContext(), GuidanceActivity.class);
             // sending data to new activity
             i.putExtra(EXTRA_MESSAGE, product);
             startActivity(i);

         }
	
	@Override
	protected void onResume() {
		super.onResume();
		GlobalData.dh.open();
		System.out.println("ENTERING GCALL FUNCTION");
		st = GlobalData.dh.getObjects("serversync.Story");
		System.out.println("FETCHED THE LIST OF STORIES NEEDING GCALL");
		if(st == null)
		{
			System.out.println("ST is NULL!!");
			Toast.makeText(this, "You have no items to give guidance calls too :)", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else
		{
			System.out.println("SIZE OF ST " + st.size());
		
		int i;
		
		ArrayList<String> gnStories = new ArrayList<String>();
		stories = new ArrayList<Story>();
		
		for(i=0;i<st.size();i++)
		{
			Story temp = (Story)st.get(i).obj;
			if(temp==null)
				System.out.println("TEMP IS NULL!!");
			else if(temp.status_tag1==null || temp.status_tag2==null)
			{
				System.out.println("MODERATED STORY RESYNCED");
			}
			
			else if(temp.status_tag2.equals("Assigned") && temp.status_tag1.equals("guidance call needed"))
			{
				System.out.println("STORY "+temp.story_id + "HAS STATUS TAG1= "+ temp.status_tag1);
				System.out.println("STORY "+temp.story_id + "HAS STATUS TAG2= "+ temp.status_tag2);
				stories.add(temp);
				orig_pos.add(i);
				gnStories.add("आइटम " + temp.story_id);
			}

			
		}
		
		if(stories.size()==0)
		{
			System.out.println("ST is NULL!!");
			Toast.makeText(this, "गाइडेन्स कॉल देने के ली कोई नया आइटम नहीं है", Toast.LENGTH_LONG).show();
			this.finish();
		}
		else
		{
		System.out.println("OUT FROM LOOP");
		if(gnStories==null)
			System.out.println("GNSTORIES IS NULL");
		else{
			for(i=0;i<gnStories.size();i++)
				System.out.println("ENTRY "+ i +"IS "+ gnStories.get(i));
		}
		
		//gnStories = getResources().getStringArray(R.array.guidance_needing_stories);
		//String[] s = (String[]) gnStories.toArray();
		
		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_display_guidance_items, R.id.label, gnStories));
		
		 ListView lv = getListView();
		 
		 
		 
	        // listening to single list item on click
	 /*       lv.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	              int position, long id) {
	 
	              // selected item
	              String product = ((TextView) view).getText().toString();
	 
	              int num =  Integer.parseInt(product.substring(product.indexOf(" ")+1));
	              System.out.println("NUM IS FOUND TO BE: "+ num);
	              GlobalData.fetched = stories[position];
	              // Launching new Activity on selecting single List Item
	              Intent i = new Intent(getApplicationContext(), GuidanceActivity.class);
	              // sending data to new activity
	              i.putExtra(EXTRA_MESSAGE, product);
	              startActivity(i);
	 
	          }
	        }); */
		 lv.setOnItemClickListener(this);
		}
	}
	}

}
