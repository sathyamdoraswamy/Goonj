package com.example.goonjnew;

import serversync.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AssignActivity extends Activity {


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
		setContentView(R.layout.activity_assign);
		
		System.out.println("IN ASSIGN ACTIVITY");
		System.out.println("LOCATION ID : "+ GlobalData.fetched.location_id);
		System.out.println("TOPICSTORY PATH : "+ GlobalData.fetched.topicStoryPath);
		System.out.println("ISSUE ASSIGNED  : "+ GlobalData.issue_assigned);
		System.out.println("ISSUESTORYPATH : "+ GlobalData.fetched.issueStoryPath);
		
		if(GlobalData.fetched.location_id != 0  && !GlobalData.fetched.topicStoryPath.equals("") )
		{
			if(GlobalData.issue_assigned && !GlobalData.fetched.issueStoryPath.equals(""))
			{
				//UPDATE Sathyam db
				GlobalData.fetched.status_tag2 = "Moderated";
				System.out.println("ABOUT TO UPDATE IN THE IF CLAUSE");
				GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
				GlobalData.done_moderating = true;
				this.finish();
			}
//			else 
//			{
//				//UPDATE db
//				GlobalData.fetched.status_tag2 = "Moderated";
//				System.out.println("ABOUT TO UPDATE IN THE ELLSE CLAUSE");
//				GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
//			}
			
			
		}
		
	}  
	
	@Override
	protected void onResume() {
		super.onResume();
		
		GlobalData.dh.open();
		setContentView(R.layout.activity_assign);
		
		System.out.println("IN ASSIGN ACTIVITY");
		System.out.println("LOCATION ID : "+ GlobalData.fetched.location_id);
		System.out.println("TOPICSTORY PATH : "+ GlobalData.fetched.topicStoryPath);
		System.out.println("ISSUE ASSIGNED  : "+ GlobalData.issue_assigned);
		System.out.println("ISSUESTORYPATH : "+ GlobalData.fetched.issueStoryPath);
		
		if(GlobalData.fetched.location_id !=0  && !GlobalData.fetched.topicStoryPath.equals("") )
		{
			if(GlobalData.issue_assigned && !GlobalData.fetched.issueStoryPath.equals(""))
			{
				//UPDATE Sathyam db
				GlobalData.fetched.status_tag2 = "Moderated";
				System.out.println("ABOUT TO UPDATE IN THE IF CLAUSE");
				GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
				GlobalData.done_moderating = true;
				this.finish();
			}
//			else 
//			{
//				//UPDATE db
//				GlobalData.fetched.status_tag2 = "Moderated";
//				System.out.println("ABOUT TO UPDATE IN THE ELLSE CLAUSE");
//				GlobalData.dh.updateObject(GlobalData.groupid, GlobalData.fetched, GlobalData.namespace);
//			}
			
			
		}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_assign, menu);
		return true;
	}
	
	
	public void assignLocation(View v)
	{
		Intent locationIntent = new Intent(this, LocationActivity.class);
		startActivity(locationIntent);
	}
	
	public void assignIssue(View v)
	{
		Intent issueIntent = new Intent(this, IssueActivity.class);
		startActivity(issueIntent);
	}
	
	public void assignTopic(View v)
	{
		Intent topicIntent = new Intent(this, TopicActivity.class);
		startActivity(topicIntent);
	}

}
