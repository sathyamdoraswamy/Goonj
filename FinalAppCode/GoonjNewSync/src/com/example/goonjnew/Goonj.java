package com.example.goonjnew;

import java.io.File;
import java.lang.reflect.Method;

import com.example.framework.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import serversync.*;

public class Goonj extends Activity implements OnClickListener 
{	
	// For Application
    private static final int AUDIO_REQUEST = 1001;
	private static final int CAMERA_VIDEO_REQUEST = 1002;
	private static final int CAMERA_PIC_REQUEST = 1003;
	private static int i = 0;
	double gps[] = new double[2]; 
	String appFolder = "Goonj";
	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	public static String currentFile = "";
	MyFileObserver fo;
	LocationManager locationManager;
	LocationListener locationListener;
	private Helper dh;
	Story s1 = new Story();
	Story s2 = new Story();
	Story s3 = new Story();
	
	TopicChannel t1 = new TopicChannel();
	TopicChannel t2 = new TopicChannel();
	TopicChannel t3 = new TopicChannel();
	
	Issue i1 = new Issue();
	Issue i2 = new Issue();
	Issue i3 = new Issue();
	
	Loc_district l1 = new Loc_district();
	Loc_district l2 = new Loc_district();
	Loc_district l3 = new Loc_district();
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		GlobalData.dh.close();
	}
	
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


	
	
	public void onCreate(Bundle savedInstanceState)
	{		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
     
	    GlobalData.dh = new Helper(this);
	    GlobalData.dh.open();	    
	    s1.story_id = 12;
	    s1.detail_id = 10;
	    s1.ai_id =12345;
	    s1.status_tag2 = "Assigned";
	    s1.time_assigned = "23";
	    s1.time_fetched = "123";
	    s1.audioPath = extStorageDirectory + "/Goonj/song.mp3";
	    
	    
//	    s2.story_id = 15;
//	    s2.detail_id = 10;
//	    s2.ai_id =54321;
//	    s2.status_tag2 = "Assigned";
//	    s2.time_assigned = "0";
//	    s2.audioPath = extStorageDirectory + "/Goonj/Check/song.mp3";
//	    
//	    s3.story_id = 25;
//	    s3.detail_id = 10;
//	    s3.ai_id =54312;
//	    s3.status_tag2 = "Assigned";
//	    s3.time_assigned = "25";
//	    s3.audioPath = extStorageDirectory + "/Goonj/Check/songCopy.mp3";
//	    
//	    t1.topic_channel_id = 1;
//	    t1.topic = "Health";
//	    
//	    t2.topic_channel_id = 2;
//	    t2.topic = "Education";
//	    
//	    t3.topic_channel_id = 3;
//	    t3.topic = "Agriculture";
//	    
//	    i1.issue_id = 1;
//	    i1.issue_name = "NREGA";
//	    
//	    i2.issue_id = 2;
//	    i2.issue_name = "Water Conservation Campaign";
//	    
//	    i3.issue_id = 3;
//	    i3.issue_name = "Rural Urban Migration Campaign"; 
//	    
//	    l1.id = 1;
//	    l1.name = "Bokaro";
//	    
//	    l2.id = 2;
//	    l2.name = "Ranchi";
//	    
//	    l3.id = 3;
//	    l3.name = "East Singhbhum";
	    
	    System.out.println("CALLING PUT FUNCTION...");
	   // GlobalData.dh.putObject(s1,"mridu");
//	    GlobalData.dh.putObject(s2,"mridu");
//	    GlobalData.dh.putObject(s3,"mridu");
//	    
//	    GlobalData.dh.putObject(t1,"mridu");
//	    GlobalData.dh.putObject(t2,"mridu");
//	    GlobalData.dh.putObject(t3,"mridu");
////	    
//	    GlobalData.dh.putObject(i1,"mridu");
//	    GlobalData.dh.putObject(i2,"mridu");
//	    GlobalData.dh.putObject(i3,"mridu");
//	    
//	    GlobalData.dh.putObject(l1,"mridu");
//	    GlobalData.dh.putObject(l2,"mridu");
//	    GlobalData.dh.putObject(l3,"mridu");
//	    
	    File file = new File(extStorageDirectory + "/" + appFolder);
	    if(!file.exists()) 
	    	file.mkdirs();  
	    // Set up click listeners for all the buttons
	    View audioButton = findViewById(R.id.record_audio);
	    audioButton.setOnClickListener(this);
	    View videoButton = findViewById(R.id.capture_video);
	    videoButton.setOnClickListener(this);
	    View photoButton = findViewById(R.id.capture_photo);
	    photoButton.setOnClickListener(this);
	    View closeButton = findViewById(R.id.close);
	    closeButton.setOnClickListener(this);
	    View moderateItems = findViewById(R.id.moderate);
	    moderateItems.setOnClickListener(this);
	    View guidanceCall = findViewById(R.id.guidance);
	    guidanceCall.setOnClickListener(this);
	    //enableGPRS();
	    //enableGPS();    	        	 	      
    }
	
	public void enableGPS()
	{
		// Check GPS status and prompt an alert dialog
	    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
	    {  
	    	createGpsDisabledAlert();  
	    }   
	    // Initialize GPS parameters
	    Criteria crit = new Criteria();
	    crit.setAccuracy(Criteria.ACCURACY_FINE);
	    String provider = locationManager.getBestProvider(crit, true);
	    Location loc = locationManager.getLastKnownLocation(provider);
	    if(loc != null)
	    {
		    gps[0] = loc.getLatitude();
		    gps[1] = loc.getLongitude();
	    }
    	// Define a listener that responds to location updates
    	locationListener = new LocationListener() 
    	{
    		// Called when a new location is found by the network location provider.
    		public void onLocationChanged(Location location) 
    		{					
    			gps[0] = location.getLatitude();
    			gps[1] = location.getLongitude();   	    	
    		}
			public void onStatusChanged(String provider, int status, Bundle extras) {}
	        public void onProviderEnabled(String provider) {}
	        public void onProviderDisabled(String provider) {}
    	};
    	// Register the listener with the Location Manager to receive location updates
    	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
	}
	
	public void enableGPRS()
	{
		 // Enable packet data
	    try 
	    {
			setMobileDataEnabled(getApplicationContext(),true);
		} 
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	}
		
	// Function shows alert dialog giving user an option to switch on GPS
	private void createGpsDisabledAlert()
	{  
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage("Your GPS is disabled! Would you like to enable it?")  
		     .setCancelable(false)  
		     .setPositiveButton("Enable GPS",  
	          new DialogInterface.OnClickListener()
		     {  
		          public void onClick(DialogInterface dialog, int id)
		          {  
		               showGpsOptions();  
		          }  
		     });  
		     builder.setNegativeButton("Do nothing",  
	          new DialogInterface.OnClickListener()
		     {  
		          public void onClick(DialogInterface dialog, int id)
		          {  
		               dialog.cancel();  
		          }  
		     });  
		AlertDialog alert = builder.create();  
		alert.show();  
	}  
	// Function shows options for the GPS enable dialog
	private void showGpsOptions()
	{  
	        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
	        startActivity(gpsOptionsIntent);  
	}  	
	// Function enables/disables packet data according to value of parameter enabled
	private void setMobileDataEnabled(Context context, boolean enabled)
	{
		try 
		{
		    final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    final Class conmanClass = Class.forName(conman.getClass().getName());
		    final java.lang.reflect.Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
		    iConnectivityManagerField.setAccessible(true);
		    final Object iConnectivityManager = iConnectivityManagerField.get(conman);
		    final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
		    final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		    setMobileDataEnabledMethod.setAccessible(true);
		    setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		}
		catch(Exception e){}
	}			
	// Function checks for button clicked and takes suitable actions
	public void onClick(View v) 
	{
		
		switch (v.getId()) 
    	{
    		case R.id.record_audio:
    			fo = new MyFileObserver(extStorageDirectory+"/Sounds/");
	    		fo.startWatching();
	    		Intent audioIntent =new Intent(android.provider.MediaStore.Audio.Media.RECORD_SOUND_ACTION);
	    		audioIntent.putExtra(android.provider.MediaStore.EXTRA_FINISH_ON_COMPLETION, "true");
	    		startActivityForResult(audioIntent, AUDIO_REQUEST);
	    		break;
	    	case R.id.capture_video:
	    		Intent videoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);    	    			    		
	    		videoIntent.putExtra(android.provider.MediaStore.EXTRA_FINISH_ON_COMPLETION, "true");
	    		startActivityForResult(videoIntent, CAMERA_VIDEO_REQUEST);
	    		break;
	    	case R.id.capture_photo:
	    		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	    		currentFile = extStorageDirectory + "/" + appFolder +  "/" + "photo" + Integer.toString(i) + ".png";
	    		Uri photoUri = Uri.fromFile(new File(currentFile));
	    		i++;
	    		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
	    		cameraIntent.putExtra(android.provider.MediaStore.EXTRA_FINISH_ON_COMPLETION, "true");
	    		startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	    		break;
	    	case R.id.close:
	    		//locationManager.removeUpdates(locationListener);
	    		GlobalData.dh.close();
	    		Intent intent = new Intent(Intent.ACTION_MAIN);
	    		intent.addCategory(Intent.CATEGORY_HOME);
	    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		startActivity(intent);
	    		break;
	    	case R.id.moderate:
	    		Intent moderateIntent = new Intent(this, DisplayItems.class);
	    		startActivity(moderateIntent);
	    		break;
	    	case R.id.guidance:
	    		Intent guidanceIntent = new Intent(this, DisplayGuidanceItems.class);
	    		startActivity(guidanceIntent);
	    		break;
    	}
    }
	    
    public String getVideoPath(Uri uri) 
    {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // Function gets invoked when the home screen returns after media recording
    public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {  
    	super.onActivityResult(requestCode, resultCode, data); 
    	GlobalData.dh.open();
    	if (requestCode == AUDIO_REQUEST)
        {          	   
    		fo.stopWatching(); 
    		if(currentFile != "")
    		{
				String sourcePath = extStorageDirectory + "/Sounds/" + currentFile;						
				String destPath = extStorageDirectory + "/" + appFolder + "/" + "audio" + Integer.toString(i++) + "_" + gps[0] + "_" + gps[1] + ".3ga";
				File sourceF = new File(sourcePath);
				try 
				{
				    sourceF.renameTo(new File(destPath));
				}
				catch (Exception e) 
				{				
				    Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}		
				GlobalData.dh.putToRepository("listValues.User.Audio", destPath,"file");
				Toast.makeText(this, "Recorded Audio", Toast.LENGTH_SHORT).show();
    		}
        }
    	if(resultCode == RESULT_OK)    
    	{	    	
	    	if (requestCode == CAMERA_PIC_REQUEST)
	        {  
	    		GlobalData.dh.putToRepository("listValues.User.Photo", currentFile,"file");
				Toast.makeText(this, "Captured photo", Toast.LENGTH_SHORT).show();				
	        }
    		if (requestCode == CAMERA_VIDEO_REQUEST)
	        {  
	        	String sourcePath = getVideoPath(data.getData());			
				String destPath = extStorageDirectory + "/" + appFolder + "/" + "video" + Integer.toString(i++) + "_" + gps[0] + "_" + gps[1] + ".mp4";
				File sourceF = new File(sourcePath);
				try 
				{ 
				    sourceF.renameTo(new File(destPath));
				}
				catch (Exception e) 
				{				 
				    Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
				}	  	
				GlobalData.dh.putToRepository("listValues.User.Video", destPath, "file");
				Toast.makeText(this, "Captured Video", Toast.LENGTH_SHORT).show();  	    
	        }
    	} 
    	currentFile = "";
    }  
}
// Class listens for a new audio file getting created
class MyFileObserver extends FileObserver
{
	public MyFileObserver(String path,int mask)
	{
		super(path,mask);
	}
	public MyFileObserver(String path)
	{
		super(path);
	}
	@Override
	public void onEvent(int event,String path)
	{		
		if(path!=null)
			Goonj.currentFile = path;		
	}
	
	
}