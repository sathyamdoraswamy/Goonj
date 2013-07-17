///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package serversync;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.sql.Statement;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author sathyam
// */
//public class Framework extends Thread {
//
//    int port = 8010;
//    static String databasePath = "/home/mridu/moderationApp";
//    static String storagePath = "/home/mridu/Desktop/Data";
//    static boolean callback = false;
//    static Events e = null;
//    static ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();
//
//    public Framework(int port, String databasePath, String storagePath) {
//        this.port = port;
//        this.databasePath = databasePath;
//        this.storagePath = storagePath;
//    }
//
//    public void run() {
//        try {
//            System.out.println("FRAMEWORK STARTED!!!");
//            ServerSocket server = new ServerSocket(port);
//            int index = 0;
//            boolean flag = true;
//            while (flag) {
//                Socket connection = server.accept();
//                connection.setSoTimeout(60000);
//                System.out.println("Accepted");
//                ServerThread t = new ServerThread(index++, connection);
//                threadList.add(t);
//                t.start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setCallbackObject(Object obj) {
//        e = (Events) obj;
//        callback = true;
//    }
//
//    public void setDatabasePath(String value) {
//        databasePath = value;
//    }
//
//    public void setStoragePath(String value) {
//        storagePath = value;
//    }
//
//    public void setPort(int value) {
//        port = value;
//    }
//
//    public String getDatabasePath() {
//        return databasePath;
//    }
//
//    public String getStoragePath() {
//        return storagePath;
//    }
//
//    public int getPort() {
//        return port;
//    }
//}
//
//class ServerThread extends Thread {
//
//    DatabaseHelper dh;
//    Socket connection;
//    ObjectInputStream ois;
//    ObjectOutputStream oos;
//    int index;
//
//    public ServerThread(int i, Socket con) {
//        this.dh = new DatabaseHelper(Framework.databasePath);
//        index = i;
//        connection = con;
//    }
//
////    public void addNamespace(String user, String namespace, String permission) {
////        try {
////            String query = "insert into namespaces values('" + user + "','" + namespace + "','" + permission + "');";
////            //System.out.println("Query :" + query);
////            dh.executeQuery(query);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    public void removeNamespace(String user, String namespace, String permission) {
////        try {
////            String query = "delete from namespaces where user='" + user + "' and namespace='" + namespace + "' and permission='" + permission + "';";
////            //System.out.println("Query :" + query);
////            dh.executeQuery(query);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    public Record getRecordFromBundle(Bundle b) {
//        Record r = null;
//        try {
//            String record = "";
//            byte[] recd = b.data;
//            record = new String(recd);
//            System.out.println("Record :\n" + record);
//            String rec[] = record.split("\n");
//            r = new Record();
//            r.groupid = rec[0];
//            r.key = rec[1];
//            r.value = rec[2];
//            r.user = rec[3];
//            r.datatype = rec[4];
//            r.timestamp = Long.parseLong(rec[5]);
//            return r;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    boolean checkPermission(String key, String user) {
//        String namespace = key.substring(0, key.indexOf("."));
//        String query = "select * from namespaces where user='" + user + "' and namespace='" + namespace + "'";
//        ArrayList<String[]> result = dh.executeQueryForResult(query);
//        if (result != null && result.get(0)[2].indexOf("w") != -1) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public synchronized long commit(Transaction t, String user, String deviceId) {
//        boolean flag = true, result = true;
//        String className = "";
//        long timestamp = System.currentTimeMillis();
//        String groupid = "";
//        ArrayList<Record> records = new ArrayList<Record>();
//        Record r = new Record();
//        r = getRecordFromBundle(t.bundles[0]);
//        if (!checkPermission(r.key, user)) {
//            return -1;
//        }
//        groupid = r.groupid;
//        String query = "select timestamp from server where groupid='" + r.groupid + "'";
//        ArrayList<String[]> temp = dh.executeQueryForResult(query);
//        if (temp != null && temp.size() != 0) {
//            long current_timestamp = Long.parseLong(temp.get(0)[0]);
//            //System.out.println("CURRENT TIMESTAMP: " + current_timestamp);
//            //System.out.println("TIMESTAMP : " + r.timestamp);
//            if (r.timestamp != current_timestamp) {
//                result = false;
//                flag = false;
//            }
//        }
//        while (flag) {
//            try {
//                dh.con.setAutoCommit(false);
//                for (int i = 0; i < t.noOfBundles; i++) {
//                    query = "";
//                    Thread.sleep(1000);
//                    r = new Record();
//                    r = getRecordFromBundle(t.bundles[i]);
//                    if (r.datatype.equals("file")) {
//                        //System.out.println("CHECK");
//                        int l = Integer.parseInt(r.value.substring(0, r.value.indexOf(' ')));
//                        String fileName = r.value.substring(r.value.indexOf(' ') + 1);
//                        fileName = deviceId + "_" + t.transactionId + "_" + i + "_" + fileName;
//                        String path = Framework.storagePath + "/" + fileName;
//                        File f = new File(path);
//                        FileOutputStream fos = new FileOutputStream(f);
//                        for (int j = 1; j <= l; j++) {
//                            Bundle b = t.bundles[i + j];
//                            fos.write(b.data);
//                        }
//                        fos.close();
//                        i += l;
//                        r.value = path;
//                    }
//                    if (Framework.callback) {
//                        //System.out.println("CHECK CALLBACK");
//                        records.add(r);
//                    }
//                    query = "delete from server  where groupid='" + r.groupid + "' and key='" + r.key + "'";
//                    //System.out.println("Query :" + query);
//                    dh.executeQuery(query);
//                    query = "insert into server values('" + r.groupid
//                            + "','" + r.key + "','" + r.value + "','" + r.user
//                            + "','" + r.datatype + "'," + timestamp + ",'"
//                            + deviceId + "')";
//                    //System.out.println("Query :" + query);
//                    dh.executeQuery(query);
//                }
//                query = "update server set timestamp=" + timestamp + " where groupid='" + groupid + "'";
//                dh.executeQuery(query);
//                dh.con.commit();
//
//                if (Framework.callback) {
//                    String key = records.get(0).key;
//                    className = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
//                    Object obj = dh.getObject(className, groupid);
//                    if(obj!=null) {
//                        Framework.e.doJob(obj);
//                    }
//                }
//                flag = false;
//            } catch (Exception e) {
//                e.printStackTrace();
////            String ex = e.toString();
////            if(ex.indexOf("unique")!=-1)
////            {
////                commitToBackup(t, deviceId);
////            }
////            else //if(ex.indexOf("locked")!=-1)
////            if(ex.indexOf("unique")==-1)
////            {
//                if (dh.con != null) {
//                    try {
//                        System.err.print("Transaction is being rolled back");
//                        dh.con.rollback();
//                        records = null;
//                        records = new ArrayList<Record>();
//                        //Thread.sleep(2000);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
////                }
//                } else {
//                    result = false;
//                    flag = false;
//                }
//                //}
//            } finally {
//                try {
//                    dh.con.setAutoCommit(true);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        if (result) {
//            return timestamp;
//        } else {
//            return commitToBackup(t, deviceId);
//        }
//    }
//
//    public synchronized long commitToBackup(Transaction t, String deviceId) {
//        boolean flag = true;
//        String className = "";
//        String groupid = "";
//        long timestamp = System.currentTimeMillis();
//        ArrayList<Record> records = new ArrayList<Record>();
//        while (flag) {
//            try {
//                dh.con.setAutoCommit(false);
//                for (int i = 0; i < t.noOfBundles; i++) {
//                    String record = "";
//                    String query = "";
//                    Bundle b = t.bundles[i];
//                    byte[] recd = b.data;
//                    record = new String(recd);
//                    //System.out.println("Record :\n" + record);
//                    String rec[] = record.split("\n");
//                    Record r = new Record();
//                    r.groupid = rec[0];
//                    groupid = r.groupid;
//                    r.key = rec[1];
//                    r.value = rec[2];
//                    r.user = rec[3];
//                    r.datatype = rec[4];
//                    r.timestamp = Long.parseLong(rec[5]);
//                    if (r.datatype.equals("file")) {
//                        int l = Integer.parseInt(r.value.substring(0, r.value.indexOf(' ')));
//                        String fileName = r.value.substring(r.value.indexOf(' ') + 1);
//                        fileName = deviceId + "_" + t.transactionId + "_" + i + "_" + fileName;
//                        String path = Framework.storagePath + "/" + fileName;
//                        File f = new File(path);
//                        FileOutputStream fos = new FileOutputStream(f);
//                        for (int j = 1; j <= l; j++) {
//                            b = t.bundles[i + j];
//                            fos.write(b.data);
//                        }
//                        fos.close();
//                        i += l;
//                        r.value = path;
//                    }
//                    if (Framework.callback) {
//                        records.add(r);
//                    }
//                    query = "insert into serverbackup values('" + r.groupid
//                            + "','" + r.key + "','" + r.value + "','" + r.user
//                            + "','" + r.datatype + "'," + timestamp + ",'"
//                            + deviceId + "')";
//                    //System.out.println("Query :" + query);
//                    dh.executeQuery(query);
//                }
//                dh.con.commit();
//                if (Framework.callback) {
//                    String key = records.get(0).key;
//                    className = key.substring(key.indexOf(".") + 1, key.lastIndexOf("."));
//                    Object obj = dh.getObject(className, groupid);
//                    if(obj!=null) {
//                        Framework.e.doJob(obj);
//                    }
//                }
//                flag = false;
//            } catch (Exception e) {
//                if (dh.con != null) {
//                    try {
//                        System.err.print("Transaction is being rolled back");
//                        dh.con.rollback();
//                        records = null;
//                        records = new ArrayList<Record>();
//                        flag = true;
//                        // Thread.sleep(2000);
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                }
//            } finally {
//                try {
//                    dh.con.setAutoCommit(true);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return timestamp;
//    }
//
//    class ACKThread extends Thread {
//
//        Transaction[] t;
//        //int tid;
//        //int noOfBundles;
//        //ObjectInputStream ois;
//        int seq;
//        //DatabaseHelper dh;
//        String deviceId;
//
//        public ACKThread(DatabaseHelper dh, Transaction[] t, ObjectInputStream ois, String deviceId, int seq) {
//            //this.dh = dh;
//            this.t = t;
//            //this.ois = ois;
//            this.deviceId = deviceId;
//            this.seq = seq;
//        }
//
////        public ACKThread(int tid, int noOfBundles, ObjectInputStream ois, int seq) {
////            this.tid = tid;
////            this.noOfBundles = noOfBundles;
////            this.ois = ois;
////            this.seq = seq;
////        }
//        public void run() {
//            System.out.println("ACK STARTED");
//            Bundle b;
//            int i = 0, j;
//            try {
//                for (i = 0; i < t.length; i++) {
//                    for (j = seq; j < t[i].noOfBundles; j++) {
//                        do {
//                            //System.out.println("SEQ ACK : " + t[i].noOfBundles);
//                            byte[] buffer = (byte[]) ois.readObject();
//                            b = new Bundle();
//                            b.parse(buffer);
//                            System.out.println("ACK WAITING : " + j);
//                            System.out.println("ACK RECEIVED : " + b.bundleNumber);
//                            System.out.println(t[i].transactionId + " " + t[i].noOfBundles + " " + j);
//                        } while (!b.isAcknowledgement(t[i].transactionId, t[i].noOfBundles, j));
//                    }
//                    //dh.setSynched(t[i].records);
//                    seq = 0;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                ObjectOutputStream fout;
//                try {
//                    System.out.println("Writing broken transaction");
//                    fout = new ObjectOutputStream(new FileOutputStream(new File(deviceId + "_pull")));
//                    fout.writeObject(t);
//                    fout.close();
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//            }
////            int j = seq;
////            Bundle b;
////            try {
////                for (j = seq; j < noOfBundles; j++) {
////                    do {
////                        System.out.println("SEQ ACK : " + noOfBundles);
////                        byte[] buffer = (byte[]) ois.readObject();
////                        b = new Bundle();
////                        b.parse(buffer);
////                        System.out.println("ACK WAITING : " + j);
////                        System.out.println("ACK RECEIVED : " + b.bundleNumber);
////                        System.out.println(tid + " " + noOfBundles + " " + j);
////                    } while (!b.isAcknowledgement(tid, noOfBundles, j));
////                    bundleNo = j;
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////                System.out.println("ACK EXCEPTION CAUGHT, STATUS SEQ : " + bundleNo);
////            }
//            System.out.println("ACK ENDED");
//        }
//    }
//
//    public void run() {
//        try {
//            oos = new ObjectOutputStream(connection.getOutputStream());
//            ois = new ObjectInputStream(connection.getInputStream());
//            String deviceId = "";
//            String user = "";
//            ArrayList<String[]> namespaces = null;// = new ArrayList<String[]>();
//            Long[] ids = null;
//            while (true) {
//                byte[] buffer = (byte[]) ois.readObject();
//                String msg = new String(buffer);
//                System.out.println("MESSAGE : " + msg);
//                if (msg.equals("UPDATE")) {
//                    System.out.println("UPDATE START");
//                    buffer = (byte[]) ois.readObject();
//                    msg = new String(buffer);
//                    deviceId = msg.substring(0, msg.indexOf(' '));
//                    msg = msg.substring(msg.indexOf(' ') + 1);
//                    user = msg;
//                    namespaces = dh.getNamespaceRecords(user);
//                    String temp = "NULL";
//                    if (namespaces != null) {
//                        int l = namespaces.size();
//                        //System.out.println("Size of namespaces is : "+l);
//                        if (l == 1) {
//                            temp = namespaces.get(0)[1];
//                        } else {
//                            //System.out.println(l);
//                            temp = namespaces.get(0)[1];
//                            int i;
//                            for (i = 1; i < l; i++) {
//                                //System.out.println("Loop : "+namespaces.get(i)[1]);
//                                temp = temp + "\n" + namespaces.get(i)[1];
//                            }
//                        }
//                    }
//                    //System.out.println("NAMESPACES : " + temp);
//                    oos.writeObject(temp.getBytes());
//                    if (temp.equals("NULL")) {
//                        continue;
//                    }
//                    buffer = (byte[]) ois.readObject();
//                    //System.out.println("Received : "+new String(buffer));
//                    String[] id = (new String(buffer)).split("\n");
//                    int l = id.length;
//                    ids = new Long[l];
//                    for (int i = 0; i < l; i++) {
//                        ids[i] = Long.parseLong(id[i]);
//                    }
//                    System.out.println("UPDATE OVER");
//                } else if (msg.equals("PULL")) {
//                    System.out.println("PULL START");
//                    ACKThread ack = null;
//                    try {
//                        buffer = (byte[]) ois.readObject();
//                        Bundle b = new Bundle();
//                        b.parse(buffer);
//                        if (b.bundleType == 5)//RETRANS
//                        {
//                            File f = new File(deviceId + "_pull");
//                            if (!f.exists()) {
//                                b = new Bundle();
//                                b.userId = 1;
//                                b.transactionId = -1;
//                                b.bundleType = 3;//STOP
//                                b.noOfBundles = 1;
//                                b.bundleNumber = -1;
//                                b.bundleSize = 0;
//                                b.data = null;
//                                oos.writeObject(b.getBytes());
//                                oos.flush();
//                                System.out.println("SENT STOP BUNDLE");
//                                continue;
//                            }
//                            String[] info = (new String(b.data)).split("\n");
//                            int tid = Integer.parseInt(info[0]);
//                            int bno = Integer.parseInt(info[1]);
//                            ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
//                            Transaction[] tt = (Transaction[]) fin.readObject();
//                            fin.close();
//                            Transaction[] t = new Transaction[1];
//                            t[0] = tt[tid];
//                            if (t[0].bundles == null) {
//                                t[0].organizeBundles();
//                            }
//                            f.delete();
//                            ack = new ACKThread(dh, t, ois, deviceId, bno);
//                            //ACKThread ack = new ACKThread(tid, t.noOfBundles, ois, bno);
//                            ack.start();
//                            for (int bi = bno; bi < t[0].noOfBundles; bi++) {
//                                byte[] bundle = t[0].bundles[bi].getBytes();
//                                oos.writeObject(bundle);
//                                oos.flush();
//                                System.out.println("Sent bundle -> Transaction id : " + tid + " Bundle No. : " + bi);
//                               // Thread.sleep(1000);
//                            }
//                            ack.join();
//                            //dh.setSynched(t.records);
//                        }
//                        if (b.bundleType == 4)//START
//                        {
//                            System.out.println("Start bundle received");
//                            Transaction[] transactions = null;
//                            //ArrayList<String[]> list = dh.selectNewRecords(user, rowid, deviceId);
//                            ArrayList<String[]> list = dh.selectNewRecords(namespaces, ids, deviceId);
//                            //System.out.println("New records selected");
//                            if (list != null) {
//                                int l = list.size();
//                                Record[] records = new Record[l];
//                                int n = 0;
//                                String gid = "";
//                                for (int i = 0; i < l; i++) {
//                                    records[i] = new Record();
//                                    records[i].rowid = Long.parseLong(list.get(i)[0]);
//                                    records[i].groupid = list.get(i)[1];
//                                    records[i].key = list.get(i)[2];
//                                    records[i].value = list.get(i)[3];
//                                    records[i].user = list.get(i)[4];
//                                    records[i].datatype = list.get(i)[5];
//                                    records[i].timestamp = Long.parseLong(list.get(i)[6]);
//                                    if (!(gid.equals(records[i].groupid))) {
//                                        n++;
//                                        gid = records[i].groupid;
//                                    }
//                                }
//                                transactions = new Transaction[n];
//                                int t = 0, r = 0;
//                                transactions[t] = new Transaction();
//                                transactions[t].transactionId = t;//records[r].groupid;
//                                transactions[t].addRecord(records[r]);
//                                r++;
//                                while (r < records.length) {
//                                    while (r < records.length && records[r].groupid.equals(records[r - 1].groupid)) {
//                                        transactions[t].addRecord(records[r]);
//                                        r++;
//                                    }
//                                    if (r < records.length) {
//                                        t++;
//                                        transactions[t] = new Transaction();
//                                        transactions[t].transactionId = t;//records[r].groupid;
//                                        transactions[t].addRecord(records[r]);
//                                        r++;
//                                    }
//                                }
//                                //Fetch all distinct group ids that are not synched
//                                //Get list of all records for each group id and form transactions with increasing tid from 0 onwards
//                                //				}
//                                ack = new ACKThread(dh, transactions, ois, deviceId, 0);
//                                ack.start();
//                                for (t = 0; t < transactions.length; t++) {
//                                    transactions[t].organizeBundles();
//                                    //ACKThread ack = new ACKThread(transactions[t].transactionId, transactions[t].noOfBundles, ois, 0);
//                                    //                            ack.start();
//                                    for (int bi = 0; bi < transactions[t].noOfBundles; bi++) {
//                                        byte[] bundle = transactions[t].bundles[bi].getBytes();
//                                        oos.writeObject(bundle);
//                                        oos.flush();
//                                        System.out.println("Sent bundle -> Transaction id : " + t + " Bundle No. : " + bi);
//                                       // Thread.sleep(1000);
//                                    }
//                                    //ack.join();
//                                    //dh.setSynched(transactions[t].records);
//                                }
//                                ack.join();
//                            }
//                            //Sending STOP message
//                            b = new Bundle();
//                            b.userId = 1;
//                            b.transactionId = -1;
//                            b.bundleType = 3;//STOP
//                            b.noOfBundles = 1;
//                            b.bundleNumber = -1;
//                            b.bundleSize = 0;
//                            b.data = null;
//                            oos.writeObject(b.getBytes());
//                            oos.flush();
//                        }
//                        //connection.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        if (ack != null) {
//                            try {
//                                ack.join();
//                            } catch (InterruptedException e1) {
//                                // TODO Auto-generated catch block
//                                e1.printStackTrace();
//                            }
//                        }
//                        System.out.println("Caught Outside");
//                    }
//                    System.out.println("PULL OVER");
//                } else if (msg.equals("PUSH")) {
//                    Transaction t = null;
//                    ArrayList<Long> tlist = null;// = new ArrayList<Long>();
//                    String fileName = deviceId + "_push";
//                    File f = new File(fileName);
//                    try {
//                        boolean flag = true;
//                        if (f.exists()) {
//                            ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
//                            t = (Transaction) fin.readObject();
//                            tlist = (ArrayList<Long>) fin.readObject();
//                            fin.close();
//                            boolean result = f.delete();
//                            //System.out.println("Delete event : " + result);
//                            Bundle b = new Bundle();
//                            b.userId = 1;
//                            b.transactionId = -1;
//                            b.bundleType = 5;// RETRANS
//                            b.noOfBundles = 1;
//                            b.bundleNumber = -1;
//                            String s = Integer.toString(t.transactionId) + "\n" + Integer.toString((t.bundleNo + 1));
//                            for(int i=0;i<tlist.size();i++) {
//                                s = s + "\n" + tlist.get(i);
//                            }
//                            byte[] bb = s.getBytes();
//                            int size = bb.length;
//                            b.bundleSize = bb.length;
//                            b.data = new byte[size];
//                            for (int j = 0; j < size; j++) {
//                                b.data[j] = bb[j];
//                            }
//                            System.out.println("Sent retrans : " + b.transactionId + " " + b.noOfBundles + " " + b.bundleNumber + " " + b.bundleType + " " + new String(b.data));
//                            oos.writeObject(b.getBytes());
//                            oos.flush();
//                            flag = true;
//                            //depending upon response receive broken  transaction or start receiving fresh transactions
//                            while (flag) {
//                                //System.out.print("Going to read");
//                                //Thread.sleep(1000);
//                                buffer = (byte[]) ois.readObject();
//                                //System.out.println("Read");
//                                b = new Bundle();
//                                b.parse(buffer);
//                                //System.out.println("Parsed");
//                                if (b.bundleType == 3) {
//                                    t = null;
//                                    break;
//                                }
//                                t.addBundle(b);
//                                if (b.bundleNumber == (b.noOfBundles - 1)) {
//                                    long ts = commit(t, user, deviceId);//use update if want to delete and add
//                                    tlist.add(ts);
//                                    Bundle ack = new Bundle();
//                                    ack.userId = b.userId;
//                                    ack.transactionId = b.transactionId;
//                                    ack.bundleType = 2;// ACK
//                                    ack.noOfBundles = b.noOfBundles;
//                                    ack.bundleNumber = b.bundleNumber;
//                                    bb = (Long.toString(ts)).getBytes();
//                                    size = bb.length;
//                                    ack.bundleSize = bb.length;
//                                    ack.data = new byte[size];
//                                    for (int j = 0; j < size; j++) {
//                                        ack.data[j] = bb[j];
//                                    }
//                                    System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
//                                    oos.writeObject(ack.getBytes());
//                                    oos.flush();
//                                    //flag = false;
//                                    continue;
//                                }
//                                Bundle ack = new Bundle();
//                                ack.createACK(b);
//                                System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
//                                //Thread.sleep(30000);
//                                oos.writeObject(ack.getBytes());
//                                oos.flush();
//                                //System.out.println("Written!!!");
//                            }
//                        } else {
//                            tlist = new ArrayList<Long>();
//                            Bundle b = new Bundle();
//                            b.userId = 1;
//                            b.transactionId = -1;
//                            b.bundleType = 4;//START
//                            b.noOfBundles = 1;
//                            b.bundleNumber = -1;
//                            b.bundleSize = 0;
//                            b.data = null;
//                            oos.writeObject(b.getBytes());
//                            flag = true;
//                            //System.out.println("Waiting to receive");
//                            do {
//                                //System.out.print("Going to read");
//                                // Thread.sleep(1000);
//                                buffer = (byte[]) ois.readObject();
//                                //System.out.println("Read");
//                                b = new Bundle();
//                                b.parse(buffer);
//                                //System.out.println("Parsed");
//                                if (b.bundleType == 3) {
//                                    t = null;
//                                    break;
//                                }
//                                if (b.bundleNumber == 0) {
//                                    t = new Transaction(b.transactionId, b.noOfBundles);
//                                }
//                                t.addBundle(b);
//                                if (b.bundleNumber == (b.noOfBundles - 1)) {
//                                    long ts = commit(t, user, deviceId);//use update if want to delete and add
//                                    tlist.add(ts);
//                                    Bundle ack = new Bundle();
//                                    ack.userId = b.userId;
//                                    ack.transactionId = b.transactionId;
//                                    ack.bundleType = 2;// ACK
//                                    ack.noOfBundles = b.noOfBundles;
//                                    ack.bundleNumber = b.bundleNumber;
//                                    byte[] bb = (Long.toString(ts)).getBytes();
//                                    int size = bb.length;
//                                    ack.bundleSize = bb.length;
//                                    ack.data = new byte[size];
//                                    for (int j = 0; j < size; j++) {
//                                        ack.data[j] = bb[j];
//                                    }
//                                    System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType + " " + new String(ack.data));
//                                    oos.writeObject(ack.getBytes());
//                                    oos.flush();
//                                    continue;
//                                }
//                                Bundle ack = new Bundle();
//                                ack.createACK(b);
//                                System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
//                                //Thread.sleep(50000);
//                                oos.writeObject(ack.getBytes());
//                                oos.flush();
//                                //System.out.println("Written!!!");
//                            } while (flag);
//                        }
//
////                    buffer = (byte[]) ois.readObject();
////                    msg = new String(buffer);
////                    System.out.println("Message is :" + msg);
//                    } catch (Exception e) {
//                        e.printStackTrace();
////                    if(t!=null)
////                    System.out.println(t.transactionId+" "+t.bundleNo+" "+t.noOfBundles);
//                        if (t != null)/* && t.bundleNo != (t.noOfBundles - 1))*/ {
//                            System.out.println("Writing broken transaction!!!");
//                            f.createNewFile();
//                            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(f));
//                            fout.writeObject(t);
//                            fout.writeObject(tlist);
//                            fout.close();
//                        }
//                    }
//                }
////                DO NOT USE THIS NAMESPACE CODE
////                else if (msg.equals("NAMESPACE")) {
////                    int n = 0;
////                    try {
////                        do {
////                            System.out.print("Going to read");
////                            //Thread.sleep(1000);
////                            buffer = (byte[]) ois.readObject();
////                            System.out.println("Read");
////                            Bundle b = new Bundle();
////                            b.parse(buffer);
////                            System.out.println("Parsed");
////                            String data = new String(b.data);
////                            String[] fields = data.split("\n");
////                            String method = fields[0];
////                            user = fields[1];
////                            String namespace = fields[2];
////                            String permission = fields[3];
////                            if (method.equals("ADD")) {
////                                addNamespace(user, namespace, permission);
////                            } else if (method.equals("REMOVE")) {
////                                removeNamespace(user, namespace, permission);
////                            }
////                            Bundle ack = new Bundle();
////                            ack.createACK(b);
////                            System.out.println("Sent ack : " + ack.transactionId + " " + ack.noOfBundles + " " + ack.bundleNumber + " " + ack.bundleType);
////                            oos.writeObject(ack.getBytes());
////                            oos.flush();
////                        } while (n == 0);
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
//                else if (msg.equals("END")) {
//                    connection.close();
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}