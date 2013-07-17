//package com.example.goonjnew;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//
//public class Config {
//	Context c;
//	public Config(Context c)
//	{
//		this.c = c;
//	}
//	
//    public void setUser(String value) 
//    {
//        // Save the access key for later use
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        Editor edit = prefs.edit();
//        edit.putString("USER", value);        
//        edit.commit();
//    }
//    
//    public void setIP(String value) 
//    {
//        // Save the access key for later use
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        Editor edit = prefs.edit();
//        edit.putString("IP", value);        
//        edit.commit();
//    }
//    
//    public void setPort(String value) 
//    {
//        // Save the access key for later use
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        Editor edit = prefs.edit();
//        edit.putString("PORT", value);        
//        edit.commit();
//    }
//    
//    public void setStoragePath(String value) 
//    {
//        // Save the access key for later use
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        Editor edit = prefs.edit();
//        edit.putString("PATH", value);        
//        edit.commit();
//    }
//    
//    public void setPhoneNo(String value) 
//    {
//        // Save the access key for later use
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        Editor edit = prefs.edit();
//        edit.putString("PHONENO", value);        
//        edit.commit();
//    }
//    
//	public String getUser() 
//    {
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        String value = prefs.getString("USER", null);                 
//    	return value;        
//    }
//	
//	public String getIP() 
//    {
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        String value = prefs.getString("IP", null);                 
//    	return value;        
//    }
//	
//	public String getPort() 
//    {
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        String value = prefs.getString("PORT", null);                 
//    	return value;        
//    }
//	
//	public String getStoragePath() 
//    {
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        String value = prefs.getString("PATH", null);                 
//    	return value;        
//    }
//	
//	public String getPhoneNo() 
//    {
//        SharedPreferences prefs = c.getSharedPreferences("DATA", 0);
//        String value = prefs.getString("PHONENO", null);                 
//    	return value;        
//    }
//
//}
