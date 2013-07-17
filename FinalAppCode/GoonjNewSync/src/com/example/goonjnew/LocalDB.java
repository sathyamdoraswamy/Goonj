package com.example.goonjnew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDB {
	
	/* Add functions here for the following:
	 *  1. Extracting list of locations
	 *  2. Extracting list of topics
	 *  3. Updating a column of the story table
	 *  4. Adding a new entry to the relation tables of story and topic
	 */
	
	private static final String DATABASE_NAME ="localDB";
	private static final int DATABASE_VERSION = 1;
	
	   private static class OpenHelper extends SQLiteOpenHelper {
		   		    
		   OpenHelper(Context context) {
		       super(context, DATABASE_NAME, null, DATABASE_VERSION);
		    }

		    @Override
		    public void onCreate(SQLiteDatabase db) {
		      
		    }
		    
		    @Override
	   	      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	   	         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
	   	         //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	   	         //onCreate(db);
	   	      }
	   }

}
