package com.example.goonjnew;

//import com.dropbox.client2.DropboxAPI;
//import com.dropbox.client2.android.AndroidAuthSession;
//import com.dropbox.client2.session.AccessTokenPair;
//import com.dropbox.client2.session.AppKeyPair;
//import com.dropbox.client2.session.Session.AccessType;

import com.example.framework.Config;
import com.example.framework.FrameworkService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver 
{
	// For Dropbox

    @Override
    public void onReceive(Context context, Intent intent)
    {        
        //if(!isMyServiceRunning("com.example.goonjnew.FrameworkService"))
        {
        	Config c = new Config(context);                        
            c.setUser("baijnath");
            c.setIP("180.149.52.3");
            c.setPort("8010");
            c.setStoragePath(Environment.getExternalStorageDirectory().toString() + "/framework");
            Log.d("FRAMEWORK","STARTING SERVICES NOW!!!");
                Intent framework = new Intent(context, com.example.framework.FrameworkService.class);
                //framework.putExtra("com.androidbook.FrameworkService.UID","1525");
                context.startService(framework);
//                try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
        }
    }
    
//    private boolean isMyServiceRunning(String serv)
//    {
//        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
//        {
//            if (serv.equals(service.service.getClassName()))
//            {
//                    System.out.println("Hello:  "+service.service.getClassName());
//                return true;
//            }
//        }
//        return false;
//    }
}
    
