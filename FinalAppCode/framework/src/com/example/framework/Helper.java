package com.example.framework;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.Settings.Secure;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
//import android.util.Pair;
public class Helper {

	

	/* The database name, Version Info and the table name are given*/


	/*
	 * We need functions for the following:
	 * 1. Create repository , at first. - check if db already available.
	 * 2. * Define the record formatdb.execSQL("UPDATE "+CLIENT_TABLE+" SET synched='Y' WHERE key ='"+key+"' AND timestamp = "+timestamp+" AND synched ='N' ");
	 * 3. * get every possible thing, given wildcards.
	 * 4. * put record into table. - a insert function and a separate put which converts i/p into record format.
	 * 5. remove unnecessary arguments and variables.
	 * 6. 
	 * 7. How to include the services in our application - we need to write an instruction manual.
	 * */
	

	private static final String DATABASE_NAME = "frameworkdb_client";
	private static final int DATABASE_VERSION = 1;
	private static final String CLIENT_TABLE = "phone" ;
	private static final String NAMESPACES_TABLE = "namespaces";
	private String user = "";
	private String phoneNo = "";
	private String number = "";
	private String deviceId = "";
	private Context context; /* The current context in which the function is called*/
	private SQLiteDatabase db;

	private SQLiteStatement insertStmt, insertStmt1, insertStmt_namespaces, insertUname;

	/* The INSERT string - the tsring form f the insert statement is given*/
	private static final String INSERT = "insert into "+ CLIENT_TABLE + " values (?,?,?,?,?,?,?,?)";	
	private static final String NAMESPACES_INSERT = "insert into "+ NAMESPACES_TABLE + " values (?,?,?,?,?)";	
	public OpenHelper openHelper;
	public Helper(Context context) {

		this.context = context;		

		/*We create a helper class to manage database creation and version management.*/
		openHelper = new OpenHelper(this.context);

		/* db is like a database handler*/
		//this.db = openHelper.getWritableDatabase();

		//this.insertStmt = this.db.compileStatement(INSERT);
		//this.insertStmt1 = this.db.compileStatement(INSERT1);
		//this.insertStmt_namespaces = this.db.compileStatement(NAMESPACES_INSERT);
		deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		Config c = new Config(context);
		user = c.getUser();
		phoneNo = c.getPhoneNo();
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
		number = tm.getLine1Number();
	}
	
	public void open()
	{
		boolean flag = true;
		while(flag)
		{
			try {
				this.db = openHelper.getWritableDatabase();
				this.insertStmt = this.db.compileStatement(INSERT);
				//this.insertStmt1 = this.db.compileStatement(INSERT1);
				this.insertStmt_namespaces = this.db.compileStatement(NAMESPACES_INSERT);
				flag = false;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}		
	} 
	  
	private String getLockStatus(String namespace) 
    {
        SharedPreferences prefs = context.getSharedPreferences("LOCKS", 0);
        String value = prefs.getString(namespace, null);                 
    	return value;        
    }
	
	public void lockNamespace(String namespace)
	{
		String msg = "LOCK" + "\n" + namespace + "\n" + deviceId + "\n" + number;
		SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, msg, null, null);  
	}
	
	public void unlockNamespace(String namespace)
	{
		String msg = "UNLOCK" + "\n" + namespace + "\n" + deviceId + "\n" + number;
		SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, msg, null, null);  
	}
	
	public void setSynched(long timestamp, ArrayList<Record> r)
    {
		boolean flag = true;
		try {
			do {
				try {
					db.beginTransaction();
		            String groupid = r.get(0).groupid;
		            for(int i=0;i<r.size();i++)
		            {
		            	db.execSQL("UPDATE "+CLIENT_TABLE+" SET synched='Y' WHERE groupid = '"+r.get(i).groupid+"' AND key = '"+r.get(i).key+"' AND value = '"+r.get(i).value+"' AND synched ='N' ");
		            }
		            db.execSQL("UPDATE "+CLIENT_TABLE+" SET timestamp="+timestamp+" WHERE groupid = '"+groupid+"'");
		            db.setTransactionSuccessful();
				    flag = false;
				} catch(Exception e) {
					System.out.println("HELPER");
					e.printStackTrace();
				} 
			} while(flag);
		} catch(Exception e) {}
		finally {
		     db.endTransaction();
		}
    }

	public void close()
	{
		openHelper.close();
	}

//	public void setIP(String add)
//	{
//		GlobalData.IPAdd=add;
//	} 
	//ASK WHAT TO DO
	public long subscribeToNamespace(String userid, String namespace, String permission)
	{
		this.insertStmt_namespaces.bindString(1,"ADD");
		this.insertStmt_namespaces.bindString(2,userid);
		this.insertStmt_namespaces.bindString(3,namespace);
		this.insertStmt_namespaces.bindString(4,permission);
		this.insertStmt_namespaces.bindLong(5,System.currentTimeMillis());
		return this.insertStmt_namespaces.executeInsert();
	}
	//ASK WHAT TO DO
	public long unsubscribeFromNamespace(String userid, String namespace, String permission)
	{
		this.insertStmt_namespaces.bindString(1,"REMOVE");
		this.insertStmt_namespaces.bindString(2,userid);
		this.insertStmt_namespaces.bindString(3,namespace);
		this.insertStmt_namespaces.bindString(4,permission);
		this.insertStmt_namespaces.bindLong(5,System.currentTimeMillis());
		return this.insertStmt_namespaces.executeInsert();
		//db.execSQL("delete from "+NAMESPACES_TABLE+" where userid='"+userid+"' and namespace='"+namespace+"' and permission='"+permission+"'");
	}
	
	public ArrayList<String[]> getNamespaceRecords() {
		ArrayList<String[]> records = null;
		Cursor cursor=db.query(NAMESPACES_TABLE, new String[] {"method","userid","namespace","permission"},null, null, null, null, "timestamp");
		//this.db.execSQL("SELECT * FROM "+CLIENT_TABLE+" WHERE KEY LIKE '%@%@%@%");		
		if (cursor.moveToFirst()) {
			records = new ArrayList<String[]>();
			do {				
				records.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)});
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return records;
	}
	
	public ArrayList<String[]> getNamespaces() {
		ArrayList<String[]> records = null;
		Cursor cursor=db.query(NAMESPACES_TABLE, new String[] {"method","userid","namespace","permission"},null, null, null, null, "timestamp");
		//this.db.execSQL("SELECT * FROM "+CLIENT_TABLE+" WHERE KEY LIKE '%@%@%@%");		
		if (cursor.moveToFirst()) {
			records = new ArrayList<String[]>();
			do {
				records.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)});
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return records;
	}
	
	public void removeNamespace(String userid, String namespace, String permission)
	{
		boolean flag = true;
		do {
			try {
				db.execSQL("DELETE FROM "+NAMESPACES_TABLE+" WHERE userid='"+userid+"' AND namespace='"+namespace+"' AND permission='"+permission+"';");
				flag = false;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while(flag);		
	}
	
//	public int getID()
//	{
//		String s = Long.toString(System.currentTimeMillis());
//		//System.out.println(s.substring(4));
//		int r = Integer.parseInt(s.substring(4));
//		//System.out.println(r);
//		return r;
//	}
	//ASK WHAT TO DO
	public long putToRepository(String key,String value,String datatype)
	{		
		long result = 0;
		boolean flag = true;
		while(flag) {
			try {
				String groupid = UUID.randomUUID().toString();//getID();		
				long timestamp = System.currentTimeMillis();
				this.insertStmt.bindLong(1, 0);
				this.insertStmt.bindString(2, groupid);
				this.insertStmt.bindString(3, key);
				this.insertStmt.bindString(4, value);
				this.insertStmt.bindString(5, user);
				this.insertStmt.bindString(6, datatype);
				this.insertStmt.bindLong(7, timestamp);
				this.insertStmt.bindString(8, "N");
				Log.d("CHECK INSERT",Long.toString(System.currentTimeMillis()));
				/* The actual insert to the database takes place here*/
				this.insertStmt.executeInsert();
				flag = false;
			} catch(Exception e) {
				e.printStackTrace();
				flag = true;
			}
		}
		return result;
		//insert(0,groupid,key, value, user, datatype, timestamp, "N");		
	}

	public void putObject(Object obj, String namespace)
	{
		try
		{
			Class c = obj.getClass();
			String className = c.getName();
			Field[] fields = c.getDeclaredFields(); 
			Record[] r = new Record[fields.length];
			int i = 0;		
			System.out.println("ENTERING LOOP NOW");
			for(Field f:fields)
			{		
				r[i] = new Record();
				String fieldName = f.getName();
				r[i].key = namespace + "." + className + "." + fieldName;            
				r[i].value = "";
				if(f.get(obj)!=null)
				{
					r[i].value = f.get(obj).toString();								
					if(fieldName.contains("Path"))
						r[i].datatype = "file";
					else
						r[i].datatype = "data";
				}
				else
				{
					r[i].datatype = "";
					r[i].value = "";					
				}
				i++;					
			}
			System.out.println("EXXITING LOOP NOW");
			putToRepository(r);		       
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void updateObject(String groupid, Object obj, String namespace)
	{
		try
		{
			Class c = obj.getClass();
			String className = c.getName();
			Field[] fields = c.getDeclaredFields(); 
			Record[] r = new Record[fields.length];
			int i = 0;			   
			for(Field f:fields)
			{		
				r[i] = new Record();
				r[i].groupid = groupid;
				String fieldName = f.getName();
				r[i].key = namespace + "." + className + "." + fieldName;            
				r[i].value = "";
				if(f.get(obj)!=null)
				{
					r[i].value = f.get(obj).toString();	
					if(fieldName.contains("Path"))
						r[i].datatype = "file";
					else
						r[i].datatype = "data";    
				}
				else
				{					
					r[i].datatype = "";
					r[i].value = "";
				}										
				i++;					
			}
			updateRepository(groupid,r);		       
		}
		catch(Exception e){e.printStackTrace();}
	}
	//ASK WHAT IS NEEDED
	public void updateRepository(String groupid, Record[] r)
	{
		System.out.println("INSIDE UPDATE OBJECT");
		boolean flag = true;
		try {
			do {
				try {
					db.beginTransaction();
					for(int i=0;i<r.length;i++)
					{
						System.out.println("UPDATE "+CLIENT_TABLE+" SET value='"+r[i].value+"', datatype='"+r[i].datatype+"', synched='N' WHERE groupid = '"+r[i].groupid+"' AND key = '"+r[i].key+"' AND VALUE <> '"+r[i].value+"'");
						db.execSQL("UPDATE "+CLIENT_TABLE+" SET value='"+r[i].value+"', datatype='"+r[i].datatype+"', synched='N' WHERE groupid = '"+r[i].groupid+"' AND key = '"+r[i].key+"' AND VALUE <> '"+r[i].value+"'");
					}
					db.setTransactionSuccessful();
				    flag = false;
				} catch(Exception e) {
					System.out.println("HELPER");
					e.printStackTrace();
				} 
			} while(flag);
		} catch(Exception e) {}
		finally {
		     db.endTransaction();
		}
	}
	
//	public long selectMaxRecordId()
//	{
//		int r = 0;
//		Cursor cursor = db.rawQuery("SELECT MAX(RECORDID) FROM "+CLIENT_TABLE, null);
//		if(cursor.moveToFirst())
//			r = cursor.getInt(0);
//		if (cursor != null && !cursor.isClosed()) {
//			cursor.close();
//		}
//		return r;
//	}	
	
	public ArrayList<Record> getRecords(String className) {
		ArrayList<Record> records = null;
//		if(!db.isOpen())
//		{
//			System.out.println("DATABASE IS NOT OPEN");
//			if(context==null)
//				System.out.println("CONTEXT IS NULL");
//			else
//				System.out.println("CONTEXT IS NOT NULL");
//			if(openHelper==null)
//				System.out.println("OH IS NULL");
//			else
//				System.out.println("OH IS NOT NULL");
//			db = openHelper.getWritableDatabase();
//		}
		Cursor cursor=db.query(CLIENT_TABLE, new String[] {"groupid","key","value","user", "datatype", "timestamp","synched"},"key like '%"+className+"%'", null, null, null, "groupid");
		//this.db.execSQL("SELECT * FROM "+CLIENT_TABLE+" WHERE KEY LIKE '%@%@%@%");		
		if (cursor.moveToFirst()) {
			records = new ArrayList<Record>();
			System.out.println("ASSIGNED NEW RECORDS");
			do {
				Record r = new Record();
				r.groupid = cursor.getString(0);
                r.key = cursor.getString(1);
                r.value = cursor.getString(2);
                r.user = cursor.getString(3);
                r.datatype = cursor.getString(4);
                r.timestamp = Long.parseLong(cursor.getString(5));
                r.synched =cursor.getString(6);
                records.add(r);				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return records;
	}
	
    private String getValue(ArrayList<Record> ans,String name)
    {
    	System.out.println("Name is:"+name);
        for(int i=0;i<ans.size();i++)
        {
            String key = ans.get(i).key;
            System.out.println("CHECK : " + key.substring(key.lastIndexOf(".")+1) + " " + ans.get(i).value);
            if(name.equals(key.substring(key.lastIndexOf(".")+1)))
            {            	
            	return ans.get(i).value;
            }
                    
        }
        return null;
    }
    
//	public Object getObject(String className) {        
//        ArrayList<Record> records = getRecords(className);
//        if (records != null) {
//            try {
//                Class c = Class.forName(className);
//                Field[] fields = c.getDeclaredFields();
//                Object obj = c.newInstance();
//                int i = 0;
//                for (Field f : fields) {
//                    String name = f.getName();
//                    String type = f.getType().toString();
//                    String value = getValue(records,name);//records.get(i).value;
//                    System.out.println(name + " " + type + " " + value);
//                    if (type.equals("int")) {
//                        f.set(obj, (Integer.parseInt(value)));
//                    }
//                    if (type.equals("float")) {
//                        f.set(obj, Long.parseLong(value));
//                    }
//                    if (type.equals("float")) {
//                        f.set(obj, (Float.parseFloat(value)));
//                    }
//                    if (type.equals("double")) {
//                        f.set(obj, (Double.parseDouble(value)));
//                    }
//                    if (type.equals("class java.lang.String")) {
//                        f.set(obj, (value));
//                    }
//                    if (type.equals("char")) {
//                        f.set(obj, value.charAt(0));
//                    }
//                    i++;
//                }
//                return obj;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
	
	public Object getObject(String className, ArrayList<Record> records) {        
        if (records != null) {
            try {
                Class c = Class.forName(className);
                Field[] fields = c.getDeclaredFields();
                Object obj = c.newInstance();
                int i = 0;
                for (Field f : fields) {
                    String name = f.getName();
                    String type = f.getType().toString();
                    String value = getValue(records,name);//records.get(i).value;
                    System.out.println(name + " " + type + " " + value);
                    
                    if (type.equals("int")) {
                        f.set(obj, (Integer.parseInt(value)));
                    }
                    if (type.equals("long")) {
                        f.set(obj, Long.parseLong(value));
                    }
                    if (type.equals("float")) {
                        f.set(obj, (Float.parseFloat(value)));
                    }
                    if (type.equals("double")) {
                        f.set(obj, (Double.parseDouble(value)));
                    }
                    if (type.equals("class java.lang.String")) {
                        f.set(obj, (value));
                    }
                    if (type.equals("char")) {
                        f.set(obj, value.charAt(0));
                    }
                    i++;
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
	    
    public ArrayList<Set> getObjects(String className)
    {
        ArrayList<Record> records = getRecords(className);
        if(records!=null)
        {
        	String key = records.get(0).key;
        	String namespace = key.substring(0,key.indexOf("."));
        	System.out.println("NAMESPACE IS : "+namespace);	        	
            ArrayList<Set> objects = new ArrayList<Set>();
            int l = records.size();
            Record p = records.get(0);
            Record c = records.get(0);
            int i=0;
            while(i<l)
            {
                ArrayList<Record> r = new ArrayList<Record>(); 
                Set o = new Set();
                while(p.groupid.equals(c.groupid))
                {                      
                    r.add(c);                	                                   
                    p = c;
                    i++;
                    if(i==l)
                        break;
                    c = records.get(i);
                }
                for(int f=0;f<r.size();f++)
                	printRecord(r.get(f));
                o.groupid = p.groupid;	
                o.namespace = namespace;
                o.obj = getObject(className, r);
                objects.add(o);   
                p = c;
            }
            return objects;
        }
        System.out.println("RETURNING NULL AS RECORDS IN NULL");
        return null;
    }
    
    public void printRecord(Record r)
    {
    	System.out.println("ROWID : " + r.rowid);
    	System.out.println("GROUPID : " + r.groupid);
    	System.out.println("KEY : " + r.key);
    	System.out.println("VALUE : " + r.value);
    	System.out.println("USER : " + r.user);
    	System.out.println("DATATYPE : " + r.datatype);
    	System.out.println("TIMESTAMP : " + r.timestamp);
    	System.out.println("SYNCHED : " + r.synched);
    }
    	     
    public void putToRepository(Record[] records)
	{
		String groupid = UUID.randomUUID().toString(); //getID();		
		long timestamp = System.currentTimeMillis();
		boolean flag = true;
		try {
			do {
				try {
					db.beginTransaction();		 
					for(int i=0;i<records.length;i++)
					{
						putToRepository(0,groupid,records[i].key, records[i].value, user, records[i].datatype, timestamp, " ");				   
					}
					db.execSQL("UPDATE "+CLIENT_TABLE+" SET synched='N' WHERE groupid = '"+groupid+"'");
					db.setTransactionSuccessful();
				    flag = false;
				} catch(Exception e) {
					System.out.println("HELPER");
					e.printStackTrace();
				} 
			} while(flag);
		} catch(Exception e) {}
		finally {
		     db.endTransaction();
		}
		Log.d("CHECK PUT",Long.toString(System.currentTimeMillis()));
	}
    
    public long putToRepository(long rowid,String gid,String key,String value,String user,String datatype,long timestamp,String synched)
    {		   	
 	   	this.insertStmt.bindLong(1, rowid);
 		this.insertStmt.bindString(2, gid);
 		this.insertStmt.bindString(3, key);
 		this.insertStmt.bindString(4, value);
 		this.insertStmt.bindString(5, user);
 		this.insertStmt.bindString(6, datatype);
 		this.insertStmt.bindLong(7, timestamp);
 		this.insertStmt.bindString(8, synched);
 		//insert(rowid,gid,key, value, user, datatype, timestamp, synched);
 		//Log.d("CHECK INSERT",Long.toString(System.currentTimeMillis()));
 	   	return this.insertStmt.executeInsert();
    }
    
	public void putToRepository(ArrayList<Record> records)
	{		
		boolean flag = true;
		try {
			do {
				try {
					db.beginTransaction();		 
					for(int i=0;i<records.size();i++)
					{
						insert(records.get(i).rowid,records.get(i).groupid,records.get(i).key, records.get(i).value, records.get(i).user, records.get(i).datatype, records.get(i).timestamp, records.get(i).synched);				   
					}					
					db.execSQL("UPDATE "+CLIENT_TABLE+" SET timestamp="+records.get(0).timestamp+" WHERE groupid='"+records.get(0).groupid+"'");
					db.setTransactionSuccessful();
				    flag = false;
				} catch(Exception e) {
					System.out.println("HELPER");
					e.printStackTrace();
				} 
			} while(flag);
		} catch(Exception e) {}
		finally {
		     db.endTransaction();
		}
		Log.d("CHECK INSERT",Long.toString(System.currentTimeMillis()));
	}

	public long insert(long rowid,String groupid,String key,String value,String user,String datatype,long timestamp,String synched) {
		long result = -1;
		db.execSQL("DELETE FROM "+CLIENT_TABLE+" WHERE groupid='"+groupid+"' AND key ='"+key+"'");		
		/* We bind the values to the '?'s */
		this.insertStmt.bindLong(1, rowid);
		this.insertStmt.bindString(2, groupid);
		this.insertStmt.bindString(3, key);
		this.insertStmt.bindString(4, value);
		this.insertStmt.bindString(5, user);
		this.insertStmt.bindString(6, datatype);
		this.insertStmt.bindLong(7, timestamp);
		this.insertStmt.bindString(8, synched);

		/* The actual insert to the database takes place here*/
		result = this.insertStmt.executeInsert();				
		return result;
	}

//	public void setSynched(String key, long timestamp)
//	{
//		boolean flag = true;
//		do {
//			try {
//				db.execSQL("UPDATE "+CLIENT_TABLE+" SET synched='Y' WHERE key ='"+key+"' AND timestamp = "+timestamp+" AND synched ='N' ");
//				flag = false;
//			} catch(Exception e) {
//				e.printStackTrace();
//			}
//		} while(flag);		
//	}

	public long getMaxRowIdGivenNamespace(String namespace)
	{
		int r = 0;
		String query = "SELECT MAX(RECORDID) FROM "+CLIENT_TABLE+" WHERE KEY LIKE %'"+namespace+"'%";
		System.out.println(query);
		Cursor cursor=db.query(CLIENT_TABLE, new String[] {"MAX(recordid)"},"key like '%"+namespace+"%'", null, null, null, null);
		//Cursor cursor = db.rawQuery("SELECT MAX(RECORDID) FROM "+CLIENT_TABLE+" WHERE KEY LIKE %'"+namespace+"'%", null);
		if(cursor.moveToFirst())
			r = cursor.getInt(0);
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		System.out.println("ROWID : "+r);
		return r;		
	}
	
	public Long[] getRowIds(String[] namespaces)
	{
		int l = namespaces.length;
		Long[] rowids = new Long[l];
		for(int i=0;i<l;i++)
		{
			rowids[i] = getMaxRowIdGivenNamespace(namespaces[i]);
		}
		return rowids;
	}	
	
	/* clear the contents of the database */
	public void clearRepositoryContents() {

		this.db.delete(CLIENT_TABLE, null, null);
		//this.db.delete(CLIENT_TABLE1, null, null);

	}

//	public List<String[]> selectRecords(int gid) {
//		List<String[]> records = new ArrayList<String[]>();
//		Cursor cursor=db.query(CLIENT_TABLE, new String[] {"groupid","key","value","user", "datatype", "timestamp"},"synched='N' AND groupid="+gid, null, null, null, null);
//		if (cursor.moveToFirst()) {
//			do {
//				records.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)});
//				//list.add(cursor.getString(1));
//			} while (cursor.moveToNext());
//		}
//		if (cursor != null && !cursor.isClosed()) {
//			cursor.close();
//		}
//		if(cursor==null)
//		{
//			records=null;
//		}
//		return records;
//	}
	
//	public boolean existNewRecords()
//	{
//		Cursor cursor = db.rawQuery("SELECT RECORDID FROM "+CLIENT_TABLE+" WHERE SYNCHED='N'", null);
//		//Cursor cursor=db.query(CLIENT_TABLE, new String[] {"groupid","key","value","user", "datatype", "timestamp"},"synched='N'", null, null, null,null );
//		if(cursor!=null)
//			return true;
//		else
//			return false;
//	}
	/*Select the unsynched records which were created after the given timestamp. It basically collects which need to be synched to the server stub.*/
	public List<String[]> selectNewRecords() {
		List<String[]> list = null;
		Cursor cursor=db.query(CLIENT_TABLE, new String[] {"groupid","key","value","user", "datatype", "timestamp"},"synched='N'", null, null, null, "timestamp, datatype desc, groupid");

		if (cursor.moveToFirst()) {
			list = new ArrayList<String[]>();
			do {
				list.add(new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)});
				//list.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		/*Creates the table CLIENT_TABLE. Should be used only once - at the beginning.*/
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + CLIENT_TABLE  +"(RECORDID NUMBER,GROUPID TEXT,KEY TEXT,VALUE TEXT,USER TEXT,DATATYPE TEXT,TIMESTAMP NUMBER,SYNCHED TEXT, PRIMARY KEY(RECORDID,KEY,TIMESTAMP))");
			//db.execSQL("CREATE TABLE " + CLIENT_TABLE1  +"(record TEXT,timestamp NUMBER,seq TEXT)");
			db.execSQL("CREATE TABLE " + NAMESPACES_TABLE  +"(method TEXT,userid TEXT,namespace TEXT,permission TEXT,timestamp NUMBER)");
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example", "Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE);
			//db.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE1);
			db.execSQL("DROP TABLE IF EXISTS " + NAMESPACES_TABLE);
			onCreate(db);
		}
	}
}