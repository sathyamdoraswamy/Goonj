package com.example.goonjnew;


import serversync.*;
import java.util.ArrayList;
import com.example.framework.Set;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
//import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class DisplayItems extends Activity {
  
	//static Story fetched = new Story();
	
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
	public void onDestroy()
	{
		super.onDestroy();
		GlobalData.dh.close();
	}
	
    @Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GlobalData.dh.open();
		Intent intent = getIntent();
		
		setContentView(R.layout.activity_display_items);
		// Show the Up button in the action bar.
		//getActionBar().setDisplayHomeAsUpEnabled(true);
	}

    /*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_display_items, menu);
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
	
	public void moderateItems(View view)
	{
		//UPDATE call saPair<F, S>m's api here
		System.out.println("ENTERING THE FUNCTION");
		ArrayList<Set> st = GlobalData.dh.getObjects("serversync.Story");
		System.out.println("FETCHED THE LIST OF STORIES");
		if(st == null)
		{
			System.out.println("ST is NULL!!");
			Toast.makeText(this, "मॉडरेट करने के लिए कोई नया आइटम नहीं है", Toast.LENGTH_LONG).show();
		}
		else
		{
			System.out.println("SIZE OF ST " + st.size());
			int i=0,min_index = -1;
			if(st.size()>0)
			{
				Story s = new Story();
				Long l1,l2;
				l1 = Long.MAX_VALUE;
				String min_date, date;
				boolean flag = false;
				min_date="5000-13-32 00:00:00";
				
				System.out.println("ENTERING LOOP NOW");
				for (i=0; i< st.size();i++)
				{
					Story temp = (Story)st.get(i).obj;
					
					if(temp==null)
						System.out.println("TEMP IS NULL");
					else if((temp.status_tag1==null) || (temp.status_tag2==null))
						System.out.println("MODERATED STORY RESYNCED");
					else if(temp.status_tag2.equals("Assigned"))
					{
						System.out.println("FETCHED STORY "+ temp.story_id + " HAS STATUS_TAG2 "+temp.status_tag2);
						System.out.println("FETCHED STORY HAS TIME ASSIGNED AS: "+temp.time_assigned);

						if(temp.status_tag1.equals("guidance call needed"))
							continue;
//						if(temp.status_tag1.equals(""))
//							continue;
						if(!flag)
						{
							s = (Story)st.get(i).obj;
							//l1 = Long.parseLong(s.time_assigned);
							min_date = s.time_assigned;
							flag = true;
							min_index = i;
						}
						else 
						{
							//l2 = Long.parseLong(temp.time_assigned);
							date = temp.time_assigned;
							//if(l2<l1)
							if(isSmaller(date,min_date))
							{
								//l1 = l2;
								min_date = date;
								min_index = i;
							}
						}
							
		
					}
								
				}
				if(min_index!= -1)
				{
					GlobalData.fetched = (Story)st.get(min_index).obj;
					System.out.println("EXXITING LOOP NOW");
					System.out.println("STORY ID " + GlobalData.fetched.story_id + "FETCHED");
					
					GlobalData.groupid = st.get(min_index).groupid;
					
					GlobalData.namespace = st.get(min_index).namespace;
					System.out.println("NAMESPACE IS:"+GlobalData.namespace);
					//GlobalData.fetched = new Story();
					Intent intent = new Intent(this, ModerateActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(this, "मॉडरेट करने के लिए कोई नया आइटम नहीं है", Toast.LENGTH_LONG).show();
				}
				
			}
		}

	}
	
	
	public boolean isSmaller(String s1, String s2)
    {
        int y1,y2,m1,m2,d1,d2,h1,h2,min1,min2,sec1,sec2;

        String[] arr1 = s1.split(" ");
        String[] arr2 = s2.split(" ");

        System.out.println("Date1 is : "+arr1[0] + " And Time1 is: " + arr1[1]);
        System.out.println("Date2 is : "+arr2[0] + " And Time2 is: " + arr2[1]);

        String[] date1 = arr1[0].split("-");
        String[] time1 = arr1[1].split(":");

        String[] date2 = arr2[0].split("-");
        String[] time2 = arr2[1].split(":");

        y1 = Integer.parseInt(date1[0]);
        m1 = Integer.parseInt(date1[1]);
        d1 = Integer.parseInt(date1[2]);
        h1 = Integer.parseInt(time1[0]);
        min1 = Integer.parseInt(time1[1]);
        sec1 = Integer.parseInt(time1[2]);

        y2 = Integer.parseInt(date2[0]);
        m2 = Integer.parseInt(date2[1]);
        d2 = Integer.parseInt(date2[2]);
        h2 = Integer.parseInt(time2[0]);
        min2 = Integer.parseInt(time2[1]);
        sec2 = Integer.parseInt(time2[2]);

        System.out.println("year:"+y1+" month:"+m1+" date:"+d1+" hour:"+h1+" min:"+min1+" sec:"+sec1);
        System.out.println("year:"+y2+" month:"+m2+" date:"+d2+" hour:"+h2+" min:"+min2+" sec:"+sec2);

        if(y1<y2)
            return true;
        else if(y1>y2)
            return false;
        else
        {
            if(m1<m2)
                return true;
            else if(m1>m2)
                return false;
            else
            {
                if(d1<d2)
                    return true;
                else if(d1>d2)
                    return false;
                else
                {
                    if(h1<h2)
                        return true;
                    else if(h1>h2)
                        return false;
                    else
                    {
                        if(min1<min2)
                            return true;
                        else if(min1 >min2)
                            return false;
                        else
                        {
                            if(sec1<sec2)
                                return true;
                            else if(sec1>sec2)
                                return false;
                            else
                                return true;
                         }
                        }
                    }
                }
            }
        }

}
