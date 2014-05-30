package com.android.sapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	

	static final String DATABASE_NAME = "member.db";
	static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_1 = "members";
	public static final String TABLE_NAME_2 = "casts";
	public static final String COL_NO = "no";
	public static final String COL_NAME = "name";
	public static final String COL_FLAG = "flg";
	public static final String COL_CAMP = "camp";
	public static final String COL_SCORE = "score";
	public static final String COL_AMOUNT = "amount";
	
	static Context context;
	
	
    public DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
      this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    Log.d(Sapp.TAG,TABLE_NAME_1+"ÇçÏÇËÇ‹Ç∑ ");
      db.execSQL(
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_1 + " ("
        + COL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COL_NAME + " TEXT NOT NULL,"
        + COL_FLAG + " INTEGER NOT NULL DEFAULT 0,"
        + COL_SCORE + " INTEGER NOT NULL DEFAULT 0"
        +");"
        );
      db.execSQL(
    	"CREATE TABLE IF NOT EXISTS " + TABLE_NAME_2 + " ("
        + COL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COL_NAME + " TEXT NOT NULL,"
        + COL_CAMP + " INTEGER NOT NULL DEFAULT 0,"
        + COL_FLAG + " INTEGER NOT NULL DEFAULT 0,"
        + COL_AMOUNT + " INTEGER NOT NULL DEFAULT 0"
        +");"
    	);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
      onCreate(db);
    }
    
    /* CUSTOM method */
    
    public void addMember(SQLiteDatabase db, String name){
  		db.execSQL(
                "INSERT INTO "+TABLE_NAME_1+"("+COL_NAME+","+COL_FLAG+","+COL_SCORE+") VALUES('"+
                name+"', 0, 0);"
                );
    }
    
    public void addCast(SQLiteDatabase db, String name){
  		db.execSQL(
                "INSERT INTO "+TABLE_NAME_2+"("+COL_NAME+","+COL_FLAG+","+COL_CAMP+","+COL_AMOUNT+") VALUES('"+
                name+"', 0, 0, 0);"
                );
    }
    
    
    public void deleteMember(SQLiteDatabase db, int no){
    	db.execSQL("DELETE FROM "+TABLE_NAME_1+" WHERE "+COL_NO+"="+no);
    }
    
    public void deleteCast(SQLiteDatabase db, int no){
    	db.execSQL("DELETE FROM "+TABLE_NAME_2+" WHERE "+COL_NO+"="+no);
    }
    
    
    public void onUpdateMember(SQLiteDatabase db, Member member){
        db.execSQL(
          "UPDATE "+TABLE_NAME_1+" SET "+
          COL_NAME+" = '"+member.getName()+"', "+
          COL_FLAG+" = "+member.getFlg()+ ", " +
          COL_SCORE+" = "+member.getScore()+
          " WHERE "+COL_NO+" = "+member.getNo()+";");
    }
    public void onUpdateCast(SQLiteDatabase db, Cast cast){
        db.execSQL(
          "UPDATE "+TABLE_NAME_2+" SET "+
          COL_NAME+" = '"+cast.getName()+"', "+
          COL_FLAG+" = "+cast.getFlg()+", "+
          COL_CAMP+" = "+cast.getCamp()+", "+
          COL_AMOUNT+" = "+cast.getAmount()+
          " WHERE "+COL_NO+" = "+cast.getNo()+";");
    }
    
  }