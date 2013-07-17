//package serversync;
//
//import java.io.FileInputStream;
//import java.util.ArrayList;
//
//class Transaction implements java.io.Serializable {
//
//    int transactionId;
//    ArrayList<Record> records = new ArrayList<Record>();
//    Bundle bundles[];
//    int noOfBundles;
//    int BUNDLE_SIZE = 50 * 1024;
//    int HEADER_SIZE = 24;
//    int DATA = 1, ACK = 2, RETRANS = 3, START = 4, STOP = 5;
//    int bundleNo = -1;
//
//    public Transaction() {
//    }
//
//    public Transaction(int tid, int noofbundles) {
//        transactionId = tid;
//        noOfBundles = noofbundles;
//        bundles = new Bundle[noofbundles];
//    }
//
//    void addBundle(Bundle b) {
//        bundleNo++;
//        bundles[bundleNo] = new Bundle();
//        bundles[bundleNo] = b;
//    }
//
//    public int getNoOfBundles() {
//        int n = 0;
//        for (int i = 0; i < records.size(); i++) {
//            String datatype = records.get(i).datatype;
//            if (datatype.equals("file")) {
//                try {
//                    String value = records.get(i).value;
//                    FileInputStream ft = new FileInputStream(value);
//                    int s = ft.available();
//                    int n1 = s / (50 * 1024);
//                    int v;
//                    if (n1 * 50 * 1024 < s) {
//                        v = n1 + 1;
//                    } else {
//                        v = n1;
//                    }
//                    n += v + 1;
//                } catch (Exception e) {
//                }
//            } else {
//                n += 1;
//            }
//        }
//        return n;
//    }
//
//    public void addRecord(Record r) {
//        records.add(r);
//    }
//
//    public void organizeBundles() {
//        String rowid = "", groupid = "", key = "", value = "", user = "", datatype = "", timestamp = "";
//        int b = 0;
//        int l = records.size();
//        noOfBundles = getNoOfBundles();
//        bundles = new Bundle[noOfBundles];
//        for (int i = 0; i < l; i++) {
//            try {
//                rowid = Long.toString(records.get(i).rowid);
//                groupid = records.get(i).groupid;
//                key = records.get(i).key;
//                value = records.get(i).value;
//                user = records.get(i).user;
//                datatype = records.get(i).datatype;
//                timestamp = Long.toString(records.get(i).timestamp);
//                String record = "";
//                byte bb[];
//                if (datatype.equals("file")) {
//                    FileInputStream ft = new FileInputStream(value);
//                    bb = new byte[ft.available()];
//                    int s = ft.available();
//                    int n = s / (50 * 1024);
//                    int v;
//                    if (n * 50 * 1024 < s) {
//                        v = n + 1;
//                    } else {
//                        v = n;
//                    }
//                    String fileName = value.substring(value.lastIndexOf('/') + 1);
//                    record = rowid + "\n" + groupid + "\n" + key + "\n" + Integer.toString(v) + " " + fileName + "\n" + user + "\n" + datatype + "\n" + timestamp;
//                    bb = record.getBytes();
//                    int size = bb.length;
//                    int count = 0;
//                    while (count <= v) {
//                        bundles[b] = new Bundle();
//                        bundles[b].userId = 1;
//                        bundles[b].transactionId = transactionId;
//                        bundles[b].bundleType = DATA;
//                        bundles[b].noOfBundles = noOfBundles;
//                        bundles[b].bundleNumber = b;
//                        bundles[b].bundleSize = size;
//                        bundles[b].data = new byte[size];
//                        for (int j = 0; j < size; j++) {
//                            bundles[b].data[j] = bb[j];
//                        }
//                        b++;
//                        bb = new byte[BUNDLE_SIZE];
//                        size = ft.read(bb);
//                        count++;
//                    }
//                    ft.close();
//                    //					Bundle b = new Bundle();
//                    //					b.userId = 1;
//                    //		            b.transactionId = transactionId;
//                    //		            b.bundleType = DATA;
//                    //		            b.noOfBundles = noOfBundles;
//                    //		            b.bundleNumber = i;
//                    //		            b.bundleSize = bb.length;
//                    //		            b.data = new byte[bb.length];
//
//                } else {
//                    record = rowid + "\n" + groupid + "\n" + key + "\n" + value + "\n" + user + "\n" + datatype + "\n" + timestamp;
//                    bb = record.getBytes();
//                    bundles[b] = new Bundle();
//                    bundles[b].userId = 1;
//                    bundles[b].transactionId = transactionId;
//                    bundles[b].bundleType = DATA;
//                    bundles[b].noOfBundles = noOfBundles;
//                    bundles[b].bundleNumber = b;
//                    bundles[b].bundleSize = bb.length;
//                    bundles[b].data = new byte[bb.length];
//                    for (int j = 0; j < bb.length; j++) {
//                        bundles[b].data[j] = bb[j];
//                    }
//                    b++;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
////     public byte[][] getBundleBytes() {
////         int packetSize = BUNDLE_SIZE + HEADER_SIZE; // header size is 24 bytes
////         byte[][] data = new byte[noOfBundles][packetSize];
////         for (int i = 0; i < bundles.length; i++) {
////             data[i] = bundles[i].getBytes();
////         }
////         return data;
////     }
//}
