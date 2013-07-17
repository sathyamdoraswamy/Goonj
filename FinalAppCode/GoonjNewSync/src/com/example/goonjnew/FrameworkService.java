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
//import serversync.Temp;
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
//public class FrameworkService extends Service {
//	private static final String TAG = "FrameworkService";
//	private FrameworkThread thread;
//	
//	
//	
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
////		Config c = new Config((Context)this);
////		dh = new Helper((Context) this);
////		user = c.getUser();		
////		deviceId = Secure.getString(((Context)this).getContentResolver(), Secure.ANDROID_ID);
////		storagePath = c.getStoragePath();//Environment.getExternalStorageDirectory().toString() + "/framework";
////		ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);						
//		thread = new FrameworkThread((Context)this);
//		thread.start();
//	}
//	
//	@Override
//	public void onDestroy()
//	{
//		if(thread!=null)
//		{
//			try {
//				thread.flag = false;
//				thread.connection.close();
//				Log.d("DEBUGGING","THREAD WAS NOT NULL");
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else
//			Log.d("DEBUGGING","THREAD WAS NULL");
//	}
//}
//
//class FrameworkThread extends Thread {
//	
//	class Temp {
//	       int a;
//	       int a1,a2,a3,a4,a5,a6,a7,a8,a9,a10;
//	       
//	       int a11,a12,a13,a14,a15,a16,a17,a18,a19,a20;
//	       double b;
//	       long t;
//	       char c;
//	       String d;
//	       }
//	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();			
//	boolean flag = true;
//	
//	Socket connection;
//	ObjectOutputStream oos;
//	ObjectInputStream ois;
//	
//	Helper dh;	
//	ConnectivityManager cm;
//	String deviceId;
//	String user;	
//	InetAddress server;
//	int port;
//	String storagePath;			
//	
//	public FrameworkThread(Context c)
//	{		
//		dh = new Helper(c);
//		dh.open();
//		ConnectivityManager connMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
//		deviceId = Secure.getString(c.getContentResolver(), Secure.ANDROID_ID);
//		Config con = new Config(c);
//		user = con.getUser();
//		String IP = con.getIP();
//		try {
//		server = InetAddress.getByName(IP);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		port = Integer.parseInt(con.getPort());
//		storagePath = con.getStoragePath();//Environment.getExternalStorageDirectory().toString() + "/framework";					
//	}
////	public FrameworkThread(ConnectivityManager cm, Helper h, String user, String deviceId, String storagePath) {
////		try {
////			server = InetAddress.getByName(GlobalData.IPAdd);			
////			dh = h;
////			this.cm = cm;
////			this.storagePath = storagePath;
////			this.deviceId = deviceId;
////			this.user = user;	
////		} catch (Exception e) {
////			System.out.println(e.toString());
////		}
////	}
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
//			ArrayList<Record> records = new ArrayList<Record>();
//			for (int i = 0; i < t.noOfBundles; i++) {
//				String record = "";
//				String query = "";            
//				Bundle b = t.bundles[i];
//				byte[] recd = b.data;	
//				record = new String(recd);
//				System.out.println("Record :\n" + record);
//				String rec[] = record.split("\n");  
//				Record r = new Record();
//				r.rowid = Long.parseLong(rec[0]);
//				r.groupid = rec[1];
//				r.key = rec[2];            
//				r.value = rec[3];            
//				r.user = rec[4];            
//				r.datatype = rec[5];            
//				r.timestamp = Long.parseLong(rec[6]); 
//				r.synched = "Y";
//				if (r.datatype.equals("file")) {
//					int l = Integer.parseInt(r.value.substring(0,r.value.indexOf(' ')));
//					String fileName = r.value.substring(r.value.indexOf(' ')+1);
//					fileName = deviceId+"_"+t.transactionId+"_"+i+"_"+fileName;
//					String path = storagePath + "/" + fileName;
//					File f = new File(path);
//					FileOutputStream fos = new FileOutputStream(f);               
//					for (int j = 1; j <= l; j++) {
//						b = t.bundles[i+j];
//						fos.write(b.data);
//					}
//					fos.close();
//					i += l;
//					r.value = path;
////					query = "insert into serverdb values("+recordid+","+groupid+",'"+key+"','"+value+"','"+user+"','"+datatype+"',"+timestamp+",'Y')";
////					System.out.println("Query :"+query);					
//					//dh.putToRepository(recordid,groupid,key,value,user,datatype,timestamp,"Y");
//					//Framework.executeQuery(query);
//				} 
////					else {
//////					query = "insert into serverdb values("+recordid+","+groupid+",'"+key+"','"+value+"','"+user+"','"+datatype+"',"+timestamp+",'Y')";
//////					System.out.println("Query :"+query);					
////					//dh.putToRepository(recordid,groupid,key,value,user,datatype,timestamp,"Y");
////					//Framework.executeQuery(query);
////				}	
//				records.add(r);
//			}
//			dh.putToRepository(records);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	public boolean update()
//	{
//		try {		
//			byte[] buffer = "UPDATE".getBytes();
//			oos.writeObject(buffer);
//			buffer = (deviceId + " " + user).getBytes();
//			oos.writeObject(buffer);
//			buffer = (byte[]) ois.readObject();
//			String reply = (new String(buffer));
//			if(reply.equals("NULL"))
//				return true;
//			String[] namespaces = reply.split("\n");
//			Long[] rowids = dh.getRowIds(namespaces);
//			int l = rowids.length;
//			String ids;
//			if(l==1)
//				ids = Long.toString(rowids[0]);
//			else
//			{
//				ids = Long.toString(rowids[0]);
//				int i;
//				for(i=1;i<l;i++)
//				{
//					ids = ids + "\n" + Long.toString(rowids[i]);
//				}
//			}			
//			System.out.println("IDs : "+ids);
//			buffer = ids.getBytes();
//			oos.writeObject(buffer);
//		} catch(Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//	public boolean pull()
//	{			
//		try { 											
//			Transaction t = null;			
//			String fileName = storagePath + "/pull";			
//			File f = new File(fileName);		
//			try {
//				boolean flag = true;
//				if (f.exists()) {
//					byte[] buffer = "PULL".getBytes();
//					oos.writeObject(buffer);
//					System.out.println("PULL packet sent!!!");						
//					ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
//					t = (Transaction) fin.readObject();
//					fin.close();					
//					boolean result = f.delete();
//					System.out.println("Delete event : "+result);
//                    Bundle b = new Bundle();
//                    b.userId = 1;
//                    b.transactionId = -1;
//                    b.bundleType = 5;// RETRANS
//                    b.noOfBundles = 1;
//                    b.bundleNumber = -1;      
//                    String s = Integer.toString(t.transactionId)+"\n"+Integer.toString((t.bundleNo+1));
//                    byte[] bb = s.getBytes();
//                    int size = bb.length;
//                    b.bundleSize = bb.length;
//                    b.data = new byte[size];
//                    for(int j=0;j<size;j++)
//                    {
//                        b.data[j] = bb[j];
//                    }
//                    System.out.println("Sent retrans : " + b.transactionId + " " + b.noOfBundles + " " + b.bundleNumber + " " + b.bundleType + " " + new String(b.data));
//                    oos.writeObject(b.getBytes());
//                    oos.flush();	
//                    System.out.println("RETRANS bundle sent");
//					flag = true;
//					//depending upon response receive broken  transaction or start receiving fresh transactions
//					while(flag)
//					{
//						System.out.print("Going to read");
//						//Thread.sleep(1000);
//						buffer = (byte[]) ois.readObject();
//						System.out.println("Read");
//						b = new Bundle();
//						b.parse(buffer);
//						System.out.println("Parsed");
//						if(b.bundleType==3)
//							break;
//						t.addBundle(b);                                                
//						if(b.bundleNumber==(b.noOfBundles-1))
//						{                                                      
//							commit(t);
//							//t = new Transaction();
//							flag = false;
//						}
//						Bundle ackb = new Bundle();
//						ackb.createACK(b);
//						System.out.println(ackb.transactionId + " " + ackb.noOfBundles + " " + ackb.bundleNumber + " " + ackb.bundleType);
//						oos.writeObject(ackb.getBytes());
//						oos.flush();
//						System.out.println("Written!!!");
//					}
//					update();
//				} 				
//				byte[] buffer = "PULL".getBytes();
//				oos.writeObject(buffer);	
//				System.out.println("PULL packet sent!!!");		
//				Bundle b = new Bundle();
//                b.userId = 1;
//                b.transactionId = -1;
//                b.bundleType = 4;//START
//                b.noOfBundles = 1;
//                b.bundleNumber = -1;
//                b.bundleSize = 0;
//                b.data = null;
//                oos.writeObject(b.getBytes());
//                oos.flush();
//            	System.out.println("START bundle sent");
//					//t = new Transaction();						
////					String b = Integer.toString(-1)+"\n"+Integer.toString(-1);
////					oos.writeObject(b.getBytes());
////					System.out.println("Sent starting bundle no. : -1 -1 ");				
//				flag = true;
//				System.out.println("Waiting to receive");                
//				do {
//					System.out.print("Going to read");
//					//Thread.sleep(1000);
//					buffer = (byte[]) ois.readObject();
//					System.out.println("Read");
//					b = new Bundle();
//					b.parse(buffer);
//					System.out.println("Parsed");
//					if(b.bundleType==3)
//						break;
//					if(b.bundleNumber==0)
//					{
//						t = new Transaction(b.transactionId,b.noOfBundles);
//					}
//					t.addBundle(b);                                              
//					if(b.bundleNumber==(b.noOfBundles-1))
//					{                                                      
//						commit(t);                                                        
//					}        
//					Bundle ackb = new Bundle(); 
//					ackb.createACK(b);
//					System.out.println("Sent ack : " + ackb.transactionId + " " + ackb.noOfBundles + " " + ackb.bundleNumber + " " + ackb.bundleType);
//					oos.writeObject(ackb.getBytes());
//					oos.flush();
//				} while(flag);                                                            
//	
//				//                    buffer = (byte[]) ois.readObject();
//				//                    msg = new String(buffer);
//				//                    System.out.println("Message is :" + msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//				//                    if(t!=null)
//				//                    System.out.println(t.transactionId+" "+t.bundleNo+" "+t.noOfBundles);
//				if(t!=null && t.bundleNo != (t.noOfBundles-1))
//				{
//					System.out.println("Writing broken transaction!!!");
//					f.createNewFile();
//					ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(f));
//					fout.writeObject(t);
//					fout.close();
//				}
//				return false;
//			} 
//		} catch(Exception e) {
//			e.printStackTrace();
//			try {
//				sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			return false;
//		}				
//			return true;		
//	}
////	class Temp {
////	int a;
////	double b;
////	long t;
////	char c;
////	String d;
////	}
//	
//	public boolean namespace()
//	{
//		try {				
//			ArrayList<String[]> records = dh.getNamespaces();			
//			if (records!=null) {
//				System.out.println("NAMESPACE RECORDS FOUND");
//				int l = records.size();
//				byte[] startPacket = ("NAMESPACE").getBytes();
//				oos.writeObject(startPacket);
//				System.out.println("Start packet sent!!!");
//				AckThread ack = new AckThread(dh,ois,records);
//				for (int i = 0; i < l; i++) {
//					String method = records.get(i)[0];
//					String user = records.get(i)[1];
//					String namespace = records.get(i)[2];
//					String permission = records.get(i)[3];
//					String data = method + "\n" + user + "\n" + namespace
//							+ "\n" + permission;
//					Bundle b = new Bundle();
//					b.data = data.getBytes();
//					b.userId = 1;
//					b.transactionId = i;
//					b.bundleType = 1;
//					b.noOfBundles = 1;
//					b.bundleNumber = 0;
//					b.bundleSize = b.data.length;
//					byte[] bundle = b.getBytes();
//					oos.writeObject(bundle);
//					oos.flush();
//					System.out.println("Sent bundle -> Transaction id : " + i + " Bundle No. : " + 0);
//					//Thread.sleep(1000);
//				}
//				ack.join();					
//			}
//			return true;				
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	public boolean push()
//	{		
//		Transaction[] transactions = null;
//		ACKThread ack = null;
//		//File f = new File(extStorageDirectory + "/framework/status.txt");
////		Temp obj = new Temp();
////		obj.a = 1;
////		obj.b = 3.14;
////		obj.t = System.currentTimeMillis();
////		obj.c = 'S';
////		obj.d = "Hi";		
//		try {				
//			byte[] buffer = "PUSH".getBytes();
//			oos.writeObject(buffer);
//			System.out.println("PUSH packet sent!!!");
//			buffer = (byte[]) ois.readObject();
//            Bundle b = new Bundle();
//            b.parse(buffer);
//            if (b.bundleType == 5)//RETRANS
//            {
//				File f = new File(extStorageDirectory + "/framework/push");
//				if (!f.exists()) {
//                    b = new Bundle();
//                    b.userId = 1;
//                    b.transactionId = -1;
//                    b.bundleType = 3;//STOP
//                    b.noOfBundles = 1;
//                    b.bundleNumber = -1;
//                    b.bundleSize = 0;
//                    b.data = null;
//                    oos.writeObject(b.getBytes());
//                    oos.flush();                    
//                }
//				else
//				{
//					String[] info = (new String(b.data)).split("\n");
//	                int tid = Integer.parseInt(info[0]);
//	                int bno = Integer.parseInt(info[1]);
//					ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
//					//Transaction[] t = new Transaction[1];
//					Transaction[] tt = (Transaction[])fin.readObject();		
//					fin.close();
//					for(int i=0;i<(info.length-2);i++) {
//						dh.setSynched(Long.parseLong(info[i+2]), tt[i].records);
//					}
//					Transaction[] t = new Transaction[1];
//					t[0] = tt[tid];
//					if(t[0].bundles==null)
//						t[0].organizeBundles();
//					boolean result = f.delete();
//					System.out.println("Delete event : "+result);						
//					if(!(t[0].noOfBundles == bno)) {
//						ack = new ACKThread(dh, t, ois, bno);
//						//ACKThread ack = new ACKThread(tid, t.noOfBundles, ois, bno);
//						ack.start();
//						for (int bi = bno; bi < t[0].noOfBundles; bi++) {
//							byte[] bundle = t[0].bundles[bi].getBytes();
//							oos.writeObject(bundle);
//							oos.flush();
//							System.out.println("Sent bundle -> Transaction id : " + tid + " Bundle No. : " + bi);
//							//Thread.sleep(5000);
//						}
//						ack.join();
//					}
//					
//					//Sending STOP message
//		            b = new Bundle();
//		            b.userId = 1;
//		            b.transactionId = -1;
//		            b.bundleType = 3;//STOP
//		            b.noOfBundles = 1;
//		            b.bundleNumber = -1;
//		            b.bundleSize = 0;
//		            b.data = null;
//		            oos.writeObject(b.getBytes());
//					//dh.setSynched(t[0].records);
//				}
//			}
//            if (b.bundleType == 4)//START
//            {
//                //Transaction[] transactions = null;
//				List<String[]> list = dh.selectNewRecords();			
//				if (list!=null) {
//					int l = list.size();
//					Record[] records = new Record[l];
//					int n = 0;
//					String gid = "";
//					for (int i = 0; i < l; i++) {
//						records[i] = new Record();
//						records[i].groupid = list.get(i)[0];
//						records[i].key = list.get(i)[1];
//						records[i].value = list.get(i)[2];
//						records[i].user = list.get(i)[3];
//						records[i].datatype = list.get(i)[4];
//						records[i].timestamp = Long.parseLong(list.get(i)[5]);
//						records[i].synched = "N";
//						if (!(gid.equals(records[i].groupid))) {
//							n++;
//							gid = records[i].groupid;
//						}
//					}
//					System.out.println("NO OF TRANSACTIONS:"+n);
//					transactions = new Transaction[n];
//					int t = 0, r = 0;
//					transactions[t] = new Transaction();
//					transactions[t].transactionId = t;//records[r].groupid;
//					transactions[t].addRecord(records[r]);
//					r++;
//					while (r < records.length) {
//						while (r < records.length && records[r].groupid.equals(records[r - 1].groupid)) {
//							transactions[t].addRecord(records[r]);
//							System.out.println(records[r].key);
//							r++;
//						}
//						System.out.println("NO OF RECORDS :"+records.length);
//						System.out.println("R VALUE :"+r);
//						if (r < records.length) {
//							t++;
//							transactions[t] = new Transaction();
//							transactions[t].transactionId = t;//records[r].groupid;
//							transactions[t].addRecord(records[r]);
//							r++;
//						}
//					}
//					// Fetch all distinct group ids that are not synched
//					// Get list of all records for each group id and form
//					// transactions with increasing tid from 0 onwards
//					// }
//					ack = new ACKThread(dh, transactions, ois, 0);
//					ack.start();
//					for (t = 0; t < transactions.length; t++) {
//						transactions[t].organizeBundles();
//	//						ACKThread ack = new ACKThread(
//	//								transactions[t].transactionId,
//	//								transactions[t].noOfBundles, ois, 0);
//	//						ack.start();
//						for (int bi = 0; bi < transactions[t].noOfBundles; bi++) {
//							byte[] bundle = transactions[t].bundles[bi].getBytes();
//							oos.writeObject(bundle);
//							oos.flush();
//							System.out.println("Sent bundle -> Transaction id : " + t + " Bundle No. : " + bi);
//							//Thread.sleep(5000);
//						}
//	//						ack.join();
//	//						dh.setSynched(transactions[t].records);
//					}
//					ack.join();
//				}
//				//Sending STOP message
//	            b = new Bundle();
//	            b.userId = 1;
//	            b.transactionId = -1;
//	            b.bundleType = 3;//STOP
//	            b.noOfBundles = 1;
//	            b.bundleNumber = -1;
//	            b.bundleSize = 0;
//	            b.data = null;
//	            oos.writeObject(b.getBytes());
//				// Fetch all distinct group ids that are not synched
//				// Get list of all records for each group id and form
//				// transactions with increasing tid from 0 onwards
//				// Start transmission and ackthread like above
//				// In case of disconnection save transaction objects to a file
//				// Transaction t = new Transaction(tid,list);
//				// String msg = t.transactionId+"\n"+t.noOfBundles;
//				// oos.writeObject(msg.getBytes());
//				// byte[] buffer = (byte[]) ois.readObject();
//				// int b = Integer.parseInt(new String(buffer));
//				// t.organizeBundles();
//				// ACKThread ack = new ACKThread(tid,t.noOfBundles,ois,b+1);
//				// ack.start();
//				// t.send(oos, b+1);
//				// ack.join();				
//				transactions = null;
//				ack = null;
//	//				if (f.exists())
//	//					f.delete();
//	//			}
//				sleep(100);
//            }
//		}catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Caught Outside");
//			if(ack!=null)
//			{
//				try {
//					ack.join();
//					sleep(100);
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				return false;
//			}
////				 try
////				 {
////				 ObjectOutputStream fout = new ObjectOutputStream(new
////				 FileOutputStream(new File(extStorageDirectory + "/framework/push.txt")));
////				 fout.writeObject(transactions);
////				 /*fout.writeInt(transactions.length);
////				 System.out.println("Writing : "+transactions.length);
////				 for(int i=0;i<transactions.length;i++)
////				 fout.writeObject(transactions[i]);*/
////				 fout.close();
////				 }
////				 catch(Exception e1){}
//		}			
//		return true;		
//	}
//	
//	public boolean end()
//	{
//		try {
//			byte[] buffer = "END".getBytes();
//			oos.writeObject(buffer);
//		} catch(Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//	
//	public void run() {
//
//	    Temp obj = new Temp();
//        obj.a = 123;
//        obj.a1=obj.a2=obj.a3=obj.a4=obj.a5=obj.a6=obj.a7=obj.a8=obj.a9=obj.a10=100;
//        obj.a11=obj.a12=obj.a13=obj.a14=obj.a15=obj.a16=obj.a17=obj.a18=obj.a19=obj.a20=100;
//        obj.b = 3.14;
//        obj.t = 0;//System.currentTimeMillis();
//        obj.c = 'A';
//        obj.d = "GOONJ";
////dh.putObject(obj,"Goonj");	
//		//dh.updateObject("f3f529cf-5c6f-40cf-97d9-13cc859b6223", obj, "Goonj");
//		//dh.updateObject("f3f529cf-5c6f-40cf-97d9-13cc859b6223", obj, "Goonj");
//		//dh.putObject(obj,"Goonj");
//		while(flag)  
//		{
//			try {
//				File folder = new File(extStorageDirectory + "/framework");
//			    if(!folder.exists()) 
//			    	folder.mkdirs(); 
//				connection = new Socket(server, port);
//				connection.setSoTimeout(180000);
//				ois = new ObjectInputStream(connection.getInputStream());
//				oos = new ObjectOutputStream(connection.getOutputStream());
//				boolean result = namespace();
//				if(result)
//					result = update();
//				System.out.println("UPDATE RESULT : "+result);
//				if(result)
//					result = pull();
//				System.out.println("PULL RESULT : "+result);
//				System.out.println("PULL OVER!!!");				
//				if(result)		
//				{		  	
//					obj.d = "GRAMVAANI";
//					//dh.putObject(obj,"Goonj");
//					push();
//					System.out.println("PUSH OVER!!!");
//				}
//				if(result)
//					end();
//				connection.close();									
////				String query = "UPDATE phone SET RECORDID=0 WHERE GROUPID='f3f529cf-5c6f-40cf-97d9-13cc859b6223'";
////				dh.runQuery(query);
////				break;				
//			} catch(Exception e) {
//				e.printStackTrace();
//			}  
//			try {  
//				sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//break;
//		}
//	}	
//
//	class AckThread extends Thread {
//		
//		Helper h;
//		ObjectInputStream ois;
//		ArrayList<String[]> records;
//		
//		public AckThread(Helper h, ObjectInputStream ois, ArrayList<String[]> records)
//		{
//			this.h = h;
//			this.ois = ois;
//			this.records = records;
//		}
//		
//		public void run()
//		{
//			int l = records.size();
//			try {
//				for(int i=0;i<l;i++)
//				{							
//					byte[] buffer = (byte[]) ois.readObject();
//					Bundle b = new Bundle();
//					b.parse(buffer);
//					if(b.isAcknowledgement(i, 1, 0))
//					{						
//						String user = records.get(i)[1];
//						String namespace = records.get(i)[2];
//						String permission = records.get(i)[3];
//						h.removeNamespace(user, namespace, permission);
//					}
//				}
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	class ACKThread extends Thread {
//		Transaction[] t;
//		int tid;
//		int noOfBundles;
//		ObjectInputStream ois;
//		int seq;
//		Helper dh;	
//		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//		public ACKThread(Helper dh, Transaction[] t, ObjectInputStream ois, int seq) {
//			this.dh = dh;
//			this.t = t;
//			this.ois = ois;
//			this.seq = seq;
//		}
////		public ACKThread(int tid, int noOfBundles, ObjectInputStream ois, int seq) {
////			this.tid = tid;
////			this.noOfBundles = noOfBundles;
////			this.ois = ois;
////			this.seq = seq;		
////		}
//
//		public void run() {
//			System.out.println("ACK STARTED");
//			System.out.println("No of transactions : "+t.length);
//			System.out.println("Seq : "+seq);
//			Bundle b = null;
//			int i =0, j;
//			try
//			{
//			for(i=0;i<t.length;i++)
//			{
//				String timestamp = "";
//				while(t[i].noOfBundles==0);
//				for(j=seq;j<t[i].noOfBundles;j++)	
//				{
//					do {
//						System.out.println("SEQ ACK : " + t[i].noOfBundles);
//						byte[] buffer = (byte[]) ois.readObject();
//						b = new Bundle();
//						b.parse(buffer);
//						if(b.bundleNumber==b.noOfBundles-1)
//							timestamp = new String(b.data);
//						System.out.println("ACK WAITING : " + j);
//						System.out.println("ACK RECEIVED : " + b.bundleNumber);
//						System.out.println(t[i].transactionId + " " + t[i].noOfBundles + " " + j);
//					} while (!b.isAcknowledgement(t[i].transactionId, t[i].noOfBundles, j));
//				}
//				System.out.println("TIMESTAMP RECEIVED IS :" + timestamp);
//				long ts = Long.parseLong(timestamp);
//				dh.setSynched(ts, t[i].records);
//				seq = 0;
//				//Thread.sleep(5000);
//			}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//				ObjectOutputStream fout;
//				try {
//					fout = new ObjectOutputStream(new FileOutputStream(new File(storagePath + "/push")));
//					fout.writeObject(t);
//					System.out.println("-----------------------------------------------------");
//					System.out.println("Written broken transaction!!!");
//					System.out.println("-----------------------------------------------------");
//					fout.close();
//				} 
//				catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}			 
//				 /*fout.writeInt(transactions.length);
//				 System.out.println("Writing : "+transactions.length);
//				 for(int i=0;i<transactions.length;i++)
//				 fout.writeObject(transactions[i]);*/
//				 
//			}
////			int j = seq;
////			
////			try {
////				for (j = seq; j < noOfBundles; j++) {
////					do {
////						System.out.println("SEQ ACK : " + noOfBundles);
////						byte[] buffer = (byte[]) ois.readObject();
////						b = new Bundle();
////						b.parse(buffer);
////						System.out.println("ACK WAITING : " + j);
////						System.out.println("ACK RECEIVED : " + b.bundleNumber);
////						System.out.println(tid + " " + noOfBundles + " " + j);
////					} while (!b.isAcknowledgement(tid, noOfBundles, j));			
////				}
////			} catch (Exception e) {
////				e.printStackTrace();						
////			}
//			System.out.println("ACK ENDED");
//		}
//	}
//}