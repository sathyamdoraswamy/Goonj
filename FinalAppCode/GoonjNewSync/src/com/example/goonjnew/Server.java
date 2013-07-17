//package com.goonj;
//import java.io.*;
//import java.util.*;
//import java.net.*;
//
//public class Server
//{
//  static int port = 8011;
//  static ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();
//  public static void main(String args[])throws Exception
//  {
//    ServerSocket server = new ServerSocket(port);
//    int index = 0;
//    while(true)
//    {
//      Socket connection = server.accept();
//      System.out.println("Accepted");
//      ServerThread t = new ServerThread(index++,connection);
//      threadList.add(t);
//      t.start();            
//    }
//  }
//}
//
//class ServerThread extends Thread
//{
//  Socket connection;
//  ObjectInputStream ois;
//  ObjectOutputStream oos;
//  int index;
//  //Helper dh;
//  public ServerThread(int i, Socket con)
//  {
//    index = i;
//    connection = con;
//    //dh = new Helper((Context)this, "example.db", "datastore", 13241);
//  }
//  public void run()
//  {
//    try 
//    {
//      ois = new ObjectInputStream(connection.getInputStream());
//      oos= new ObjectOutputStream(connection.getOutputStream());
//      byte[] buffer = (byte[]) ois.readObject();
//      String msg = new String(buffer);
//      //       byte[] buffer=(byte[])oip.readObject();
//      //       byte pcktidarr[]=new byte[4];
//      //       byte useridarr[]=new byte[4];
//      //       byte pckttypearr[]=new byte[4];
//      //       for(int lk=0;lk<4;lk++)
//      //       {
//	// 	pcktidarr[lk]=buffer[lk];
//      // 	useridarr[lk]=buffer[lk+4];
//      // 	pckttypearr[lk]=buffer[lk+8];
//      //       }
//      //       int packetid=byteArrayToInt(pcktidarr);
//      //       int userid=byteArrayToInt(useridarr);
//      //       String packettype=new String(pckttypearr);
//      buffer = null;
//      if(msg.equals("PULL"))
//      {	
//	/*File f = new File(extStorageDirectory + "/framework/timestamp.txt");
//	if (!f.exists()) {
//	  f.createNewFile();
//	  FileWriter fw = new FileWriter(f);
//	  fw.write("0");
//	  System.out.println("New file \"timestamp.txt\" has been created to the framework directory");
//	  fw.close();
//	}
//	BufferedReader ftimest = new BufferedReader(new FileReader(f));
//	oldtimes = Long.parseLong(ftimest.readLine().trim());
//	ftimest.close();
//	times = (new Date()).getTime();
//	System.out.println("times:" + times);
//	System.out.println("oldtimes:" + oldtimes);
//	list = dh.selectnewAfterTimestamp(oldtimes);
//	tlist = dh.getnewTimestamp(oldtimes);
//	String[] tliststr = new String[tlist.size()];
//	int tlistl = 0;
//	 Copying the timestamp list into a string array 
//	for (String tlloop : tlist) {
//	  tliststr[tlistl] = tlloop;
//	  tlistl++;
//	}
//	if (list.size() == 0)
//	  System.out.println("No new record!!!");
//	else
//	  System.out.println("New record!!!");
//	 Check if there are any new records in the db 
//	if (list.size() != 0) {
//	  arr = new String[list.size()];
//	  fnames = new String[list.size()];
//	  imgnames = new String[list.size()];
//	  int i = 0;
//	  for (String[] name : list) {
//	    arr[i] = name[0];
//	    imgnames[i] = name[0].replace('@', '_');
//	    System.out.println("image name new one is: " + imgnames[i]);
//	    arr[i] = arr[i] + "," + name[1];
//	    fnames[i] = name[1];
//	    i++;
//	    // System.out.println("Hu::"+name+"::");
//	  }
//	  theSocket = new Socket(server, 8011);
//	  theSocket.setSoTimeout(120000);
//	  oos = new ObjectOutputStream(theSocket.getOutputStream());
//	  ois = new ObjectInputStream(theSocket.getInputStream());
//	  System.out.println("i is: " + i);
//	   i is the number of records in the database 
//	  for (int lp = 0; lp < i; lp++) {
//	    System.out.println("Image file is: " + fnames[lp]);
//	    FileInputStream ft = new FileInputStream(fnames[lp]);
//	    int typeofval = 1;
//	    if (fnames[lp].indexOf(".3gpp") > 0) {
//	      typeofval = 2;
//	    } 
//	    else if (fnames[lp].indexOf(".png") > 0)
//	      typeofval = 1;
//	    else if (fnames[lp].indexOf(".jpg") > 0)
//	      typeofval = 3;
//	    else if (fnames[lp].indexOf(".mp4") > 0)
//	      typeofval = 4;
//	    byte bb[];
//	    bb = new byte[ft.available()];
//	    ft.read(bb);
//	    int tid = (int) System.currentTimeMillis() % 1000000000;
//	    Transaction t = new Transaction(arr[lp], tid);
//	    t.organizeBundles(bb, arr[lp], 13452, 13241, imgnames[lp], typeofval);
//	    byte[][] packContent = t.getBundleBytes();// (bb,arr[lp],13452,13241,imgnames[lp],typeofval);
//	    System.out.println("Packet has been partitioned\n");
//	    for (int i = 0; i < t.noOfBundles; i++) 
//	    {
//	      try 
//	      {
//		oos.writeObject(packContent[i]);
//		//Log.d("ClientSideService", "Packet Sent");
//		byte[] buffer = (byte[]) ois.readObject();
//		Bundle b = new Bundle();
//		b.parse(buffer);
//		while (!b.isAcknowledgement(tid, t.noOfBundles,(i + 1)));
//		//Log.d("ClientSideService", "Acknowledgement Received");
//	      } 
//	      catch (SocketTimeoutException e) 
//	      {
//		while (!haveInternet())
//		  sleep(20000);
//		theSocket = new Socket(server, 8011);
//		theSocket.setSoTimeout(120000);
//		oos = new ObjectOutputStream(theSocket.getOutputStream());
//		ois = new ObjectInputStream(theSocket.getInputStream());
//	      }
//	    }
//	  }
//	}*/
//      }
//      else if(msg.equals("PUSH"))
//      {
//	while(true)
//	{
//	  String record = "";
//	  int dataSize = 0;
//	  int tid = (int) System.currentTimeMillis() % 1000000000;
//	  Transaction t = new Transaction(tid, 0);  
//	  buffer = (byte[]) ois.readObject();
//	  Bundle b = new Bundle();
//	  b.parse(buffer);
//	  System.out.println("Pull: This is the first bundle containing the record");
//	  byte[] recd = b.data;// check whether to copy or	
//	  record = new String(recd);
//	  System.out.println("Pull: The record is: " + record);
//	  t.addBundle(b);	
//	  int n = b.noOfBundles;
//	  for(int i=1;i<n;i++)
//	  {
//	    buffer = (byte[]) ois.readObject();
//	    b = new Bundle();
//	    b.parse(buffer);
//	    dataSize += b.bundleSize;
//	    t.addBundle(b);
//	    Bundle ack = new Bundle();
//	    ack.createACK(b);
//	    oos.writeObject(ack.getBytes());
//	  }	 
//	  byte[] data = new byte[dataSize];
//	  int count = 0;
//	  for (int i = 1; i < t.noOfBundles; i++) 
//	  {
//	    for (int j = 0; j < t.bundles[i].bundleSize; j++) 
//	    {
//	      data[count++] = t.bundles[i].data[j];
//	    }
//	    System.out.println("Pull: Added data in bundle " + i);
//	  }	
//	  String entry[] = record.split(",");
//	  int index1 = entry[1].lastIndexOf("/");	    
//	  String loc = "/home/sathyam/Desktop/Data/";
//	  String filename = entry[1].substring(index1+1);
//	  String dbrecord = loc + filename;
//	  System.out.println("File name : " + filename + "\nLocation : " + dbrecord);
//	  FileOutputStream fos = new FileOutputStream(dbrecord);
//	  fos.write(data);
//	  fos.close();
//	  //dh.puttoRepositoryYes(entry[0],dbrecord);
//	  //System.out.println("INSERTED THE RECORD INTO TABLE");
//	  buffer = (byte[]) ois.readObject();
//	  msg = new String(buffer);
//	  if(msg.equals("TERM"))
//	  {
//	    break;
//	  }	  
//	}
//      }
//    }
//    catch(Exception e){e.printStackTrace();}
//  }
//}