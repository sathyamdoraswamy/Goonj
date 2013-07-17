///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package serversync;
//
//import java.io.ObjectInputStream;
//
///**
// *
// * @author sathyam
// */
////class ACKThread extends Thread {
////
////    int tid;
////    int noOfBundles;
////    ObjectInputStream ois;
////    int seq;
////
////    public ACKThread(int tid, int noOfBundles, ObjectInputStream ois, int seq) {
////        this.tid = tid;
////        this.noOfBundles = noOfBundles;
////        this.ois = ois;
////        this.seq = seq;
////    }
////
////    public void run() {
////        System.out.println("ACK STARTED");
////        int j = seq;
////        Bundle b;
////        try {
////            for (j = seq; j < noOfBundles; j++) {
////                do {
////                    System.out.println("SEQ ACK : " + noOfBundles);
////                    byte[] buffer = (byte[]) ois.readObject();
////                    b = new Bundle();
////                    b.parse(buffer);
////                    System.out.println("ACK WAITING : " + j);
////                    System.out.println("ACK RECEIVED : " + b.bundleNumber);
////                    System.out.println(tid + " " + noOfBundles + " " + j);
////                } while (!b.isAcknowledgement(tid, noOfBundles, j));
////                //					String key = list.get(j)[0];
////                //					long timestamp = Long.parseLong(list.get(j)[4]);
////                //					dh.setSynched(key, timestamp);
////                bundleNo = j;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            System.out.println("ACK EXCEPTION CAUGHT, STATUS SEQ : " + bundleNo);
////        }
////        System.out.println("ACK ENDED");
////    }
////}