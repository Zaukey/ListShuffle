package com.android.sapp;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SubSapp extends Activity {
	
	static ArrayList<String> memName = new ArrayList<String>();
	static ArrayAdapter<String> aAdapter;
	static DatabaseHelper helper;
	static SQLiteDatabase db;
	Globals g;
    
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.sub);
       
       g = (Globals)this.getApplication();

       ListView slv = (ListView)findViewById(R.id.subListView);
       Button ok = (Button)findViewById(R.id.okButton);
       
       ok.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.okButton:
				Intent next = new Intent(SubSapp.this, ShowCast.class);
				startActivityForResult(next, 0);
				break;
			}
		}
       });
       
       Log.d(Sapp.TAG, "Extra取得");
       Bundle extras = getIntent().getExtras();
       Log.d(Sapp.TAG, "リストにつめる");
       memName = extras.getStringArrayList("NDATA");
       Collections.shuffle(memName);
       
       Log.d(Sapp.TAG, "アダプタ設定");
       aAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, memName);
       slv.setAdapter(aAdapter);
       Log.d(Sapp.TAG, "アダプタ設定完了");
   }
   
}
   

