//package com.goonj;
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
//import java.lang.reflect.Field;
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
//public class NamespaceService extends Service {
//	private static final String TAG = "ClientStubService";
//	private ClientSideThread thread;
//	ConnectivityManager connManager;
//	Helper dh;
//	String deviceId;
//
//	/* These three - onBind, onCreate, onStart are required in every service */
//	@Override
//	public IBinder onBind(Intent arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void onCreate() {
//		// dh = new Helper((Context)this,"example.db","datastore");
//		// thread= new ClientSideThread(dh);
//		Log.d(TAG, "Service Created");
//	}
//
//	@Override
//	public void onStart(Intent intent, int startid) {
//		Log.d(TAG, "onStart");
//		connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//		/* Getting the user-id of the process who creates this service */
//		int id = Integer.parseInt(intent
//				.getStringExtra("com.androidbook.ClientStubService.UID"));
//		dh = new Helper((Context) this, "example.db", "datastore", 13241);
//		deviceId = Secure.getString(((Context) this).getContentResolver(),
//				Secure.ANDROID_ID);
//		// dh.deleteAll();
//
//		/* currently, connManager not used inside ClientSideThread */
//		thread = new ClientSideThread(dh, connManager, this, deviceId);
//		thread.start();
//	}
//}
//
//class NamespaceThread extends Thread {
//	Helper dh;
//	InetAddress server; /* The IP address of Server */
//
//	public NamespaceThread(Helper dh) throws UnknownHostException {
//		server = InetAddress.getByName(GlobalData.IPAdd);
//		this.dh = dh;
//	}
//
//	public void run() {
//		ObjectOutputStream oos;
//		ObjectInputStream ois;
//		Log.d("ClientSideService", "Thread is running");
//		Socket theSocket = null;
//		while (true) {			
//			try {				
//				ArrayList<String[]> records = dh.getNamespaces();
//				int l = records.size();
//				if (l > 0) {
//					theSocket = new Socket(server, 8011);
//					theSocket.setSoTimeout(20000);
//					oos = new ObjectOutputStream(theSocket.getOutputStream());
//					ois = new ObjectInputStream(theSocket.getInputStream());
//					byte[] startPacket = ("NAMESPACE").getBytes();
//					oos.writeObject(startPacket);
//					System.out.println("Start packet sent!!!");
//					ACKThread ack = new ACKThread(-1,l,ois,0);
//					for (int i = 0; i < l; i++) {
//						String method = records.get(i)[0];
//						String userid = records.get(i)[1];
//						String namespace = records.get(i)[2];
//						String permission = records.get(i)[3];
//						String data = method + "\n" + userid + "\n" + namespace
//								+ "\n" + permission;
//						Bundle b = new Bundle();
//						b.data = data.getBytes();
//						b.transactionId = -1;
//						b.bundleType = 2;
//						byte[] bundle = b.getBytes();
//						oos.writeObject(bundle);
//						System.out.println("Sent bundle -> Transaction id : "
//								+ "-1" + " Bundle No. : " + i);
//						Thread.sleep(1000);
//					}
//					ack.join();
//				}
//				Thread.sleep(10000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//}
//
///* The class which actually does the talking to the server. */
//
//class ClientSideThread extends Thread {
//	String extStorageDirectory = Environment.getExternalStorageDirectory()
//			.toString();
//	Socket theSocket; /* The socket used for communication */
//	InetAddress server; /* The IP address of Server */
//	Helper dh; /* Database helper class */
//	// static ConnectivityManager cm; /* Not used */
//	ConnectivityManager cm; /* Not used */
//	ClientStubService srv; /* Not used */
//	NetworkInfo mMobile; /* Not used */
//	String statusKeyValue = "";
//	long statusTimestamp = 0;
//	int bundleNo = -1;
//	String deviceId;
//	List<String[]> list;
//
//	public ClientSideThread(Helper h, ConnectivityManager cm,
//			ClientStubService serv, String deviceId) {
//		try {
//			server = InetAddress.getByName(GlobalData.IPAdd);
//			dh = h;
//			this.cm = cm;
//			srv = serv;
//			this.deviceId = deviceId;
//		} catch (Exception e) {
//			System.out.println(e.toString());
//		}
//	}
//
//	/* Convert an integer into a byte array */
//	public static final byte[] intToByteArray(int value) {
//		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
//				(byte) (value >>> 8), (byte) value };
//	}
//
//	/* Convert a short integer to a byte array */
//	public static final byte[] shortToByteArray(short value) {
//		return new byte[] { (byte) (value >>> 8), (byte) value };
//	}
//
//	/* Convert a byte array to an integer */
//	public static final int byteArrayToInt(byte[] b) {
//		int value = 0;
//		for (int i = 0; i < 4; i++) {
//			int shift = (4 - 1 - i) * 8;
//			value += (b[i] & 0x000000FF) << shift;
//		}
//		return value;
//	}
//
//	/* Convert a byte array to a short */
//	public static final short byteArrayToShort(byte[] b) {
//		short value = 0;
//		for (int i = 0; i < 2; i++) {
//			int shift = (2 - 1 - i) * 8;
//			value += (b[i] & 0x000000FF) << shift;
//		}
//		return value;
//	}
//
//	/* Check if internet is available */
//	public boolean haveInternet() {
//		try {
//			HttpGet request = new HttpGet();
//			HttpParams httpParameters = new BasicHttpParams();
//
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
//			HttpConnectionParams.setSoTimeout(httpParameters, 5000);
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
//	/* Creating the termination packet */
//	byte[] endMessage(int packetidstart, int userident) {
//		byte[] pckt = new byte[12];
//		int pcktid = packetidstart;
//		/* Insert the packet id into termination packet */
//		byte a[] = intToByteArray(pcktid);
//		for (int k = 0; k < 4; k++)
//			pckt[k] = a[k];
//		Log.d("PacketMake:", "pcktid inserted");
//		/* Insert the user id into termination packet */
//		byte useri[] = intToByteArray(userident);
//		// int pcktid=i+pcktidstart;
//		for (int k = 0; k < 4; k++)
//			pckt[k + 4] = useri[k];
//		Log.d("PacketMake:", "userid inserted");
//		/* Insert the packet type into termination packet */
//		byte type[] = "TERM".getBytes();
//		Log.d("PacketMake:", "msg size is:" + type.length);
//		// int pcktid=i+pcktidstart;
//		for (int k = 0; k < 4; k++)
//			pckt[k + 8] = type[k];
//		Log.d("PacketMake:", "msgtype inserted");
//		return pckt;
//	}
//
//	public byte[] createPacket(int packetId, int userId, String packetType) {
//		byte[] pckt = new byte[12];
//		byte packetIdarr[] = intToByteArray(packetId);
//		for (int k = 0; k < 4; k++)
//			pckt[k] = packetIdarr[k];
//		byte userIdarr[] = intToByteArray(userId);
//		for (int k = 0; k < 4; k++)
//			pckt[k + 4] = userIdarr[k];
//		byte[] packetTypearr = packetType.getBytes();
//		for (int k = 0; k < 4; k++)
//			pckt[k + 8] = packetTypearr[k];
//		return pckt;
//	}
//
//	class S {
//		public void putToRepository(Helper dh) {
//
//		}
//	}
//
//	class Temp {
//		int a;
//		double b;
//		long t;
//		char c;
//		String d;
//	}
//
//	/* This contains the code for the main thread that keeps running repeatedly */
//	public void run() {
//		ObjectOutputStream oos;
//		ObjectInputStream ois;
//		Log.d("ClientSideService", "Thread is running");
//		Socket theSocket = null;
//		Transaction[] transactions = null;
//		File f = new File(extStorageDirectory + "/framework/status.txt");
//		Temp obj = new Temp();
//		obj.a = 1;
//		obj.b = 3.14;
//		obj.t = System.currentTimeMillis();
//		obj.c = 'S';
//		obj.d = "Hi";
//		while (true) {
//			try {
//				sleep(60000);
//				// dh.putObject(obj);
//				// obj.a++;
//				// obj.b++;
//				// obj.t = System.currentTimeMillis();
//				// obj.c++;
//				// obj.d.concat("!");
//				theSocket = new Socket(server, 8011);
//				theSocket.setSoTimeout(20000);
//				oos = new ObjectOutputStream(theSocket.getOutputStream());
//				ois = new ObjectInputStream(theSocket.getInputStream());
//				byte[] startPacket = ("PUSH " + deviceId).getBytes();
//				oos.writeObject(startPacket);
//				System.out.println("Start packet sent!!!");
//				byte[] buffer = (byte[]) ois.readObject();
//				String[] info = (new String(buffer)).split("\n");
//				int tid = Integer.parseInt(info[0]);
//				int bno = Integer.parseInt(info[1]);
//				if (tid != -1) {
//					Transaction t = new Transaction();
//					t.transactionId = tid;
//					List<String[]> list = dh.selectRecords(tid);
//					int l = list.size();
//					Record[] records = new Record[l];
//					for (int i = 0; i < l; i++) {
//						records[i] = new Record();
//						records[i].groupid = Integer.parseInt(list.get(i)[0]);
//						records[i].key = list.get(i)[1];
//						records[i].value = list.get(i)[2];
//						records[i].user = list.get(i)[3];
//						records[i].datatype = list.get(i)[4];
//						records[i].timestamp = Long.parseLong(list.get(i)[5]);
//						records[i].synched = "N";
//						t.addRecord(records[i]);
//					}
//					t.organizeBundles();
//					ACKThread ack = new ACKThread(tid, t.noOfBundles, ois, bno);
//					ack.start();
//					for (int b = bno; b < t.noOfBundles; b++) {
//						byte[] bundle = t.bundles[b].getBytes();
//						oos.writeObject(bundle);
//						System.out.println("Sent bundle -> Transaction id : "
//								+ tid + " Bundle No. : " + b);
//						Thread.sleep(1000);
//					}
//					ack.join();
//					dh.setSynched(t.records);
//					// ObjectInputStream fin = new ObjectInputStream(new
//					// FileInputStream(f));
//					// transactions = (Transaction[])fin.readObject();
//					// fin.close();
//					// int n = fin.readInt();
//					// transactions = new Transaction[n];
//					// for(int i=0;i<n;i++)
//					// {
//					// transactions[i] = new Transaction();
//					// transactions[i] = (Transaction) fin.readObject();
//					// }
//					// System.out.println("n is : "+n);
//				}
//				// else
//				// {
//				tid = 0;
//				bno = 0;
//				List<String[]> list = dh.selectNewRecords();
//				int l = list.size();
//				if (l > 0) {
//					Record[] records = new Record[l];
//					int n = 0, gid = -1;
//					for (int i = 0; i < l; i++) {
//						records[i] = new Record();
//						records[i].groupid = Integer.parseInt(list.get(i)[0]);
//						records[i].key = list.get(i)[1];
//						records[i].value = list.get(i)[2];
//						records[i].user = list.get(i)[3];
//						records[i].datatype = list.get(i)[4];
//						records[i].timestamp = Long.parseLong(list.get(i)[5]);
//						records[i].synched = "N";
//						if (gid != records[i].groupid) {
//							n++;
//							gid = records[i].groupid;
//						}
//					}
//					transactions = new Transaction[n];
//					int t = 0, r = 0;
//					transactions[t] = new Transaction();
//					transactions[t].transactionId = records[r].groupid;
//					transactions[t].addRecord(records[r]);
//					r++;
//					while (r < records.length) {
//						while (r < records.length
//								&& records[r].groupid == records[r - 1].groupid) {
//							transactions[t].addRecord(records[r]);
//							r++;
//						}
//						if (r < records.length) {
//							t++;
//							transactions[t] = new Transaction();
//							transactions[t].transactionId = records[r].groupid;
//							transactions[t].addRecord(records[r]);
//							r++;
//						}
//					}
//					// Fetch all distinct group ids that are not synched
//					// Get list of all records for each group id and form
//					// transactions with increasing tid from 0 onwards
//					// }
//					for (t = 0; t < transactions.length; t++) {
//						transactions[t].organizeBundles();
//						ACKThread ack = new ACKThread(
//								transactions[t].transactionId,
//								transactions[t].noOfBundles, ois, bno);
//						ack.start();
//						for (int b = 0; b < transactions[t].noOfBundles; b++) {
//							byte[] bundle = transactions[t].bundles[b]
//									.getBytes();
//							oos.writeObject(bundle);
//							System.out
//									.println("Sent bundle -> Transaction id : "
//											+ t + " Bundle No. : " + b);
//							Thread.sleep(1000);
//						}
//						ack.join();
//						dh.setSynched(transactions[t].records);
//					}
//				}
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
//				theSocket.close();
//				if (f.exists())
//					f.delete();
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Caught Outside");
//				// try
//				// {
//				// ObjectOutputStream fout = new ObjectOutputStream(new
//				// FileOutputStream(new File(extStorageDirectory +
//				// "/framework/status.txt")));
//				// fout.writeObject(transactions);
//				// /*fout.writeInt(transactions.length);
//				// System.out.println("Writing : "+transactions.length);
//				// for(int i=0;i<transactions.length;i++)
//				// fout.writeObject(transactions[i]);*/
//				// fout.close();
//				// }
//				// catch(Exception e1){}
//			}
//		}
//	}
//}
//class ACKThread extends Thread {
//	int tid;
//	int noOfBundles;
//	ObjectInputStream ois;
//	int seq;
//
//	public ACKThread(int tid, int noOfBundles, ObjectInputStream ois,
//			int seq) {
//		this.tid = tid;
//		this.noOfBundles = noOfBundles;
//		this.ois = ois;
//		this.seq = seq;
//	}
//
//	public void run() {
//		System.out.println("ACK STARTED");
//		int j = seq;
//		Bundle b;
//		try {
//			for (j = seq; j < noOfBundles; j++) {
//				do {
//					System.out.println("SEQ ACK : " + noOfBundles);
//					byte[] buffer = (byte[]) ois.readObject();
//					b = new Bundle();
//					b.parse(buffer);
//					System.out.println("ACK WAITING : " + j);
//					System.out.println("ACK RECEIVED : " + b.bundleNumber);
//					System.out.println(tid + " " + noOfBundles + " " + j);
//				} while (!b.isAcknowledgement(tid, noOfBundles, j));
//				// String key = list.get(j)[0];
//				// long timestamp = Long.parseLong(list.get(j)[4]);
//				// dh.setSynched(key, timestamp);					
//			}
//		} catch (Exception e) {
//			e.printStackTrace();						
//		}
//		System.out.println("ACK ENDED");
//	}
//}