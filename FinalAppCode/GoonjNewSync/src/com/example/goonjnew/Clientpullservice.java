//package com.example.goonjnew;
//
//import android.app.Service;
//import android.net.*;
//import android.content.Intent; //import android.os.Bundle;
//import android.os.Environment;
//import android.os.IBinder;
//import android.provider.Settings.Secure;
//import android.util.Log;
//import android.content.Context; //import android.view.View;
////import android.widget.EditText;
////import android.widget.Button;
////import android.widget.TextView;
//
//import java.util.*;
//import java.net.*;
//import java.io.*;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//
//// WE'LL HAVE A SEPARATE SERVICE FOR PULLING UPDATES.. WE'LL SEND A MESSAGE IN A CERTAIN FORMAT AND
//// READING THE MESSAGE, WE'LL BE ABLE TO INTERPRET WHAT IT SAYS, AND DO WHATEVER IS NEEDED..
//
//
///**  1. When we switch off and on, we should be able to start communicating from the exact point where it was left off. Atleast, abort what was happening and restart it.
// *  2. Every 20 seconds, check if GPRS available.. Only then, you should transmit.
// *  3. Let us try putting a signal strength bar (cant transmit if sig str <val)
// *  4. if server not there, keep waiting and keep trying till server becomes available..
// *  5. Maybe we'll fix the user id..*/
//
//public class ClientPullService extends Service {
//	private static final String TAG = "ClientPullService";
//	private ClientPullThread thread;
//	Helper dh;
//	String deviceId;
//	static String storagePath;
//	@Override
//	public IBinder onBind(Intent arg0) {
//		// TODO Auto-generated method stub,deviceId
//
//		return null;
//	}
//
//	@Override
//	public void onCreate() {
//		// Intent it=this.getIntent();
//		// long
//		// id=Long.parseLong(callingIntent.getStringExtra("com.androidbook.imgtwit.ID"));
//		Log.d(TAG, "Service Created");
//	}
//
//	@Override
//	public void onStart(Intent intent, int startid) {
//		Log.d(TAG, "onStart");
//		// Intent callingIntent=getIntent();
//		int id = Integer.parseInt(intent.getStringExtra("com.androidbook.ClientPullService.UID"));
//		dh = new Helper((Context) this, "example.db", "datastore", id);
//		ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//		deviceId = Secure.getString(((Context)this).getContentResolver(), Secure.ANDROID_ID);
//		storagePath = Environment.getExternalStorageDirectory().toString() + "/framework";
//		thread = new ClientPullThread(dh, 13241, connMgr, deviceId);
//		thread.start();
//	}
//}
//
//class ClientPullThread extends Thread {
//	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//	DatagramSocket dg;
//	DatagramPacket sendPacket;
//	Socket connection;	
//	InetAddress server;
//	Helper dh;
//	int client_userid;
//	ConnectivityManager cm;
//	String deviceId;
//	public ClientPullThread(Helper h, int id, ConnectivityManager cm, String deviceId) {
//		try {
//			server = InetAddress.getByName(GlobalData.IPAdd);
//			client_userid = id;
//			dh = h;
//			this.cm = cm;
//			this.deviceId = deviceId;
//			// byte[] data = "MSG SENT BY SERVICE".getBytes();
//			// sendPacket = new DatagramPacket(data, data.length,server,8011);
//			//
//			// dg=new DatagramSocket();		
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//	}
//
//	public boolean checkNetworkStatus() {
//
//		final android.net.NetworkInfo wifi = cm
//		.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//		final android.net.NetworkInfo mobile = cm
//		.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//
//		if (wifi.isAvailable()) { return true;
//
//		} else
//
//			if (mobile.isAvailable()) {
//				return true;
//
//			} else {
//				return false;
//			}
//
//	}
//
////	public static final byte[] intToByteArray(int value) {
////		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
////				(byte) (value >>> 8), (byte) value };
////	}
////
////	public static final byte[] shortToByteArray(short value) {
////		return new byte[] { (byte) (value >>> 8), (byte) value };
////	}
////
////	public static final int byteArrayToInt(byte[] b) {
////		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
////		+ (b[3] & 0xFF);
////	}
//
//	public boolean haveInternet() {
//		try {
//			HttpGet request = new HttpGet();
//			HttpParams httpParameters = new BasicHttpParams();
//
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 500);
//			HttpConnectionParams.setSoTimeout(httpParameters, 500);
//
//			HttpClient httpClient = new DefaultHttpClient(httpParameters);
//			request.setURI(new URI("http://www.cse.iitd.ernet.in/~aseth/"));
//			HttpResponse response = httpClient.execute(request);
//
//			int status = response.getStatusLine().getStatusCode();
//			if (status == HttpStatus.SC_OK) {
//				return true;
//			} else
//				return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//
//	public void commit(Transaction t) {
//		try {
//			for (int i = 0; i < t.noOfBundles; i++) {
//				String record = "";
//				String query = "";            
//				Bundle b = t.bundles[i];
//				byte[] recd = b.data;	
//				record = new String(recd);
//				System.out.println("Record :\n" + record);
//				String rec[] = record.split("\n");    
//				long recordid = Long.parseLong(rec[0]);
//				String groupid = rec[1];
//				String key = rec[2];            
//				String value = rec[3];            
//				String user = rec[4];            
//				String datatype = rec[5];            
//				long timestamp = Long.parseLong(rec[6]);                        
//				if (datatype.equals("file")) {
//					int l = Integer.parseInt(value.substring(0,value.indexOf(' ')));
//					String fileName = value.substring(value.indexOf(' ')+1);
//					fileName = deviceId+"_"+t.transactionId+"_"+i+"_"+fileName;
//					String path = ClientPullService.storagePath + "/" + fileName;
//					File f = new File(path);
//					FileOutputStream fos = new FileOutputStream(f);               
//					for (int j = 1; j <= l; j++) {
//						b = t.bundles[i+j];
//						fos.write(b.data);
//					}
//					fos.close();
//					i += l;
//					value = path;
//					query = "insert into serverdb values("+recordid+","+groupid+",'"+key+"','"+value+"','"+user+"','"+datatype+"',"+timestamp+",'Y')";
//					System.out.println("Query :"+query);
//					dh.putToRepository(recordid,groupid,key,value,user,datatype,timestamp,"Y");
//					//Framework.executeQuery(query);
//				} else {
//					query = "insert into serverdb values("+recordid+","+groupid+",'"+key+"','"+value+"','"+user+"','"+datatype+"',"+timestamp+",'Y')";
//					System.out.println("Query :"+query);
//					dh.putToRepository(recordid,groupid,key,value,user,datatype,timestamp,"Y");
//					//Framework.executeQuery(query);
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public void run() {
//		ObjectOutputStream oos;
//		ObjectInputStream ois;
//		while(true)
//		{			
//			try
//			{				
//				connection = new Socket(server, 8011);
//				connection.setSoTimeout(20000);
//				ois = new ObjectInputStream(connection.getInputStream());
//				oos = new ObjectOutputStream(connection.getOutputStream());	            
//				byte[] startPacket = ("PULL " + deviceId + " SATHYAM").getBytes();
//				oos.writeObject(startPacket);	
//				System.out.println("Start packet sent!!!");	
//				int n = 0;
//				byte[] buffer;
//				Transaction t = null;
//				String fileName = extStorageDirectory + "/framework/" + deviceId;
//				File f = new File(fileName);
//				try { 
//					long recordid = dh.selectMaxRecordId();
//					if (f.exists()) {
//						ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
//						t = (Transaction) fin.readObject();
//						fin.close();
//						boolean result = f.delete();
//						System.out.println("Delete event : "+result);
//						String s = Integer.toString(t.transactionId)+"\n"+Integer.toString((t.bundleNo+1))+"\n"+Long.toString(recordid);
//						oos.writeObject(s.getBytes());
//						System.out.println("Sent starting bundle no. : "+t.transactionId + " " +(t.bundleNo+1) + " " + recordid);
//						boolean flag = true;
//						while(flag)
//						{
//							System.out.print("Going to read");
//							Thread.sleep(1000);
//							buffer = (byte[]) ois.readObject();
//							System.out.println("Read");
//							Bundle b = new Bundle();
//							b.parse(buffer);
//							System.out.println("Parsed");
//							t.addBundle(b);                                                
//							if(b.bundleNumber==(b.noOfBundles-1))
//							{                                                      
//								commit(t);
//								//t = new Transaction();
//								flag = false;
//							}
//							Bundle ack = new Bundle();
//							ack.createACK(b);
//							System.out.println(ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
//							oos.writeObject(ack.getBytes());
//							oos.flush();
//						}
//					} else {
//						//t = new Transaction();						
//						String b = Integer.toString(-1)+"\n"+Integer.toString(-1)+"\n"+Long.toString(recordid);
//						oos.writeObject(b.getBytes());
//						System.out.println("Sent starting bundle no. : -1 -1 "+recordid);
//					}
//					System.out.println("Waiting to receive");                
//					do {
//						System.out.print("Going to read");
//						Thread.sleep(1000);
//						buffer = (byte[]) ois.readObject();
//						System.out.println("Read");
//						Bundle b = new Bundle();
//						b.parse(buffer);
//						System.out.println("Parsed");
//						if(b.bundleNumber==0)
//						{
//							t = new Transaction(b.transactionId,b.noOfBundles);
//						}
//						t.addBundle(b);                                              
//						if(b.bundleNumber==(b.noOfBundles-1))
//						{                                                      
//							commit(t);                                                        
//						}        
//						Bundle ack = new Bundle(); 
//						ack.createACK(b);
//						System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
//						oos.writeObject(ack.getBytes());
//						oos.flush();
//					} while (n==0);                                                            
//
//					//                    buffer = (byte[]) ois.readObject();
//					//                    msg = new String(buffer);
//					//                    System.out.println("Message is :" + msg);
//				} catch (Exception e) {
//					e.printStackTrace();
//					//                    if(t!=null)
//					//                    System.out.println(t.transactionId+" "+t.bundleNo+" "+t.noOfBundles);
//					if(t!=null && t.bundleNo != (t.noOfBundles-1))
//					{
//						System.out.println("Writing broken transaction!!!");
//						f.createNewFile();
//						ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(f));
//						fout.writeObject(t);
//						fout.close();
//					}
//				}
//				sleep(10000);
//			} catch(Exception e) {
//				e.printStackTrace();
//				try {
//					sleep(10000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}
//	}
//}