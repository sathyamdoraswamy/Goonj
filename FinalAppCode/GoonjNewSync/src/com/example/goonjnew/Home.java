package com.example.goonjnew;

import com.example.framework.Config;
import com.example.framework.FrameworkService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class Home extends Activity
{			
	public void onCreate(Bundle savedInstanceState)
	{		
	   /* super.onCreate(savedInstanceState);
     
	    Helper dh = new Helper(this,1);
		dh.setIP("10.193.11.234");
		//dh.close();
		
		Log.d("ABCDEFGHI","STARTING SERVICES NOW!!!");

		Intent stubser=new Intent(this, FrameworkService.class);
		stubser.putExtra("com.androidbook.FrameworkService.UID","1525");
		startService(stubser);
		  
	//	Intent pullser=new Intent(this, ClientPullService.class);
	//	pullser.putExtra("com.androidbook.ClientPullService.UID","1525");
	//	startService(pullser);
		   		
    	Intent i = new Intent(this, Goonj.class);	    	
    	startActivity(i);	    	*/
		 
		super.onCreate(savedInstanceState);
	    
//        Helper dh = new Helper(this,1);
//            dh.setIP("10.193.11.234");
//            dh.close();
//            //setUser("SATHYAM");
//            Log.d("ABCDEFGHI","STARTING SERVICES NOW!!!");
		
		                                            
		
		   
        if(!isMyServiceRunning("com.example.framework.FrameworkService"))
        {
        	Config c = new Config(this);                        
            c.setUser("baijnath");
            c.setPort("8010");
            c.setIP("180.149.52.3");
            c.setStoragePath(Environment.getExternalStorageDirectory().toString() + "/framework");
            Log.d("FRAMEWORK","STARTING SERVICES NOW!!!");
                Intent framework = new Intent(this, com.example.framework.FrameworkService.class);
                //framework.putExtra("com.androidbook.FrameworkService.UID","1525");
                startService(framework);
//                try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        }
//         Intent stubser=new Intent(this, ClientStubService.class);
//         stubser.putExtra("com.androidbook.ClientStubService.UID","1525");
//         startService(stubser);
//             
//         Intent pullser=new Intent(this, ClientPullService.class);
//         pullser.putExtra("com.androidbook.ClientPullService.UID","1525");
//         startService(pullser);
                           
    Intent i = new Intent(this, Goonj.class);                    
    startActivity(i);                                                   
}
// Function checks if the monitor service is currently running
private boolean isMyServiceRunning(String serv)
{
    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
    {
        if (serv.equals(service.service.getClassName()))
        {
                System.out.println("Hello:  "+service.service.getClassName());
            return true;
        }
    }
    return false;
}
    
}