//package com.example.goonjnew;
//
//import java.io.*;
//
//class Bundle implements java.io.Serializable{
//	int userId;
//	int transactionId;
//	int bundleType;
//	int noOfBundles;
//	int bundleNumber;
//	int bundleSize;
//	byte[] data;
//
//	public void parse(byte[] buffer) {
//		byte userIdarr[] = new byte[4];
//		byte transactionIdarr[] = new byte[4];
//		byte bundleTypearr[] = new byte[4];
//		byte noOfBundlesarr[] = new byte[4];
//		byte bundleNumberarr[] = new byte[4];
//		byte bundleSizearr[] = new byte[4];
//		for (int lk = 0; lk < 4; lk++) {
//			userIdarr[lk] = buffer[lk];
//			transactionIdarr[lk] = buffer[lk + 4];
//			bundleTypearr[lk] = buffer[lk + 8];
//			noOfBundlesarr[lk] = buffer[lk + 12];
//			bundleNumberarr[lk] = buffer[lk + 16];
//			bundleSizearr[lk] = buffer[lk + 20];
//		}
//		userId = byteArrayToInt(userIdarr);
//		transactionId = byteArrayToInt(transactionIdarr);
//		bundleType = byteArrayToInt(bundleTypearr);
//		noOfBundles = byteArrayToInt(noOfBundlesarr);
//		bundleNumber = byteArrayToInt(bundleNumberarr);
//		bundleSize = byteArrayToInt(bundleSizearr);
//		data = new byte[bundleSize];
//		for (int j = 0; j < bundleSize; j++) {
//			data[j] = buffer[j + 24];
//		}
//	}
//
//	public boolean isAcknowledgement(int transactionid, int noofbundles,
//			int bundleno) {
//		if (transactionid == transactionId && noofbundles == noOfBundles
//				&& bundleno == bundleNumber && bundleType == 2)
//			return true;
//		else
//			return false;
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
//	public byte[] getBytes() {
//		byte userIdarr[] = new byte[4];
//		byte transactionIdarr[] = new byte[4];
//		byte bundleTypearr[] = new byte[4];
//		byte noOfBundlesarr[] = new byte[4];
//		byte bundleNumberarr[] = new byte[4];
//		byte bundleSizearr[] = new byte[4];
////		byte[] buffer = new byte[data.length + 24];
//		byte[] buffer = new byte[bundleSize + 24];
//		userIdarr = intToByteArray(userId);
//		transactionIdarr = intToByteArray(transactionId);
//		bundleTypearr = intToByteArray(bundleType);
//		noOfBundlesarr = intToByteArray(noOfBundles);
//		bundleNumberarr = intToByteArray(bundleNumber);
//		bundleSizearr = intToByteArray(bundleSize);		
//		for (int lk = 0; lk < 4; lk++) {
//			buffer[lk] = userIdarr[lk];
//			buffer[lk + 4] = transactionIdarr[lk];
//			buffer[lk + 8] = bundleTypearr[lk];
//			buffer[lk + 12] = noOfBundlesarr[lk];
//			buffer[lk + 16] = bundleNumberarr[lk];
//			buffer[lk + 20] = bundleSizearr[lk];
//		}
//		for (int j = 0; j < bundleSize; j++) {
//			buffer[j + 24] = data[j];
//		}
//		return buffer;
//	}
//   
//	public void createACK(Bundle b) {
//		userId = b.userId;
//		transactionId = b.transactionId;
//		bundleType = 2;// ACK
//		noOfBundles = b.noOfBundles;
//		bundleNumber = b.bundleNumber;
//		bundleSize = 0;
//		data = null;
//	}
//}