package com.android.sapp;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ShowCast extends Activity {
	
	static ArrayList<String> memName = new ArrayList<String>();
	static ArrayList<String> casName = new ArrayList<String>();
	static ArrayAdapter<String> aAdapter;
	static DatabaseHelper helper;
	static SQLiteDatabase db;
	Globals g;
	TextView qname;
	Button sorda;
	int i,f;
    
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.show);
       
       helper = new DatabaseHelper(this);
       db = helper.getReadableDatabase();
       g = (Globals)this.getApplication();
       i=0; f=0;

       qname = (TextView)findViewById(R.id.Qname);
       sorda = (Button)findViewById(R.id.sorda);
       
       Log.d("ShowCast","Listener SET");
       sorda.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.sorda:
				Log.d("(Button)sorda",g.lenMem+">="+i);
				if(g.lenMem>i){
					if(f==0){
						Log.d("ShowCast","setText");
						qname.setText("あなたは・・・"+String.valueOf(casName.get(i))+"ですよ");
						sorda.setText("おっけー");
						i++; f=1;
					}else{
						qname.setText("あなたは、\n「"+String.valueOf(memName.get(i))+"」ですか？");
						sorda.setText("そーですよ");
						f=0;
					}
				}else{
					Intent disp = new Intent(ShowCast.this, DispCast.class);
					g.dispC=casName;
					g.dispM=memName;
					startActivityForResult(disp, 0);
				}
				break;
			}
		}
       });

       Log.d("ShowCast","loadName()");
       loadName();
       Collections.shuffle(memName);
       Collections.shuffle(casName);
       Log.d("ShowCast","Qname.setText");
       qname.setText("あなたは、\n「"+String.valueOf(memName.get(i))+"」ですか？");
       
   }
   
   void loadName(){
	      memName.clear();
	      casName.clear();
	      
	      // Read = 
	      Cursor c1 = db.query(DatabaseHelper.TABLE_NAME_1, null, "flg=1", null, null, null, null);
		  Cursor c2 = db.query(DatabaseHelper.TABLE_NAME_2, null, "flg=1", null, null, null, null);
	      

		  int c=0;
	      if(c1.moveToFirst()&&c2.moveToFirst()){
	        do {
	        	memName.add(c1.getString(c1.getColumnIndex(DatabaseHelper.COL_NAME)));
	            casName.add(c2.getString(c2.getColumnIndex(DatabaseHelper.COL_NAME)));
	            c++;
	            if(c2.getInt(c2.getColumnIndex(DatabaseHelper.COL_AMOUNT))==c){
	            	c2.moveToNext();
	            	c=0;
	            }
	            
	        } while(c1.moveToNext());
	      }
	      c1.close();
	      c2.close();
   }

   @Override
   protected void onResume() {
       super.onResume();
       db = helper.getReadableDatabase();
   }

   @Override
   protected void onPause() {
       super.onPause();
       db.close();
   }
   
}
   

