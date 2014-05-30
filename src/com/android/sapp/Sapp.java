package com.android.sapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Sapp extends Activity{
  
  static final String TAG = "SQLiteTest1";
  static final int MENUITEM_ID_DELETE = 1;
  ListView itemListView;
  EditText castEditText;
  Button  saveButton,startButton,castButton;
  static MemberListAdapter listAdapter = null;
  static ArrayList<Member> memberList = new ArrayList<Member>();
  static DatabaseHelper helper;
  static SQLiteDatabase db;
  Globals g;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        g = (Globals)this.getApplication();
        
        
        findViews();
        setListeners();
        
        listAdapter = new MemberListAdapter(this, R.layout.row, memberList);
        itemListView.setAdapter(listAdapter);

        loadLen();
        loadMember();
    }
    
    protected void findViews(){
    	itemListView = (ListView)findViewById(R.id.itemListView);
    	castEditText = (EditText)findViewById(R.id.memoEditText);
    	saveButton = (Button)findViewById(R.id.saveButton);
    	startButton = (Button)findViewById(R.id.startButton);
    	castButton = (Button)findViewById(R.id.castButton);
    }

	protected void setListeners(){
    	saveButton.setOnClickListener(new ClickListener());
    	startButton.setOnClickListener(new ClickListener());
    	castButton.setOnClickListener(new ClickListener());
      
    	itemListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){
    		@Override
    		public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    			menu.add(0, MENUITEM_ID_DELETE, 0, "さくじょ");
    		}
    	});
    }
	
	class ClickListener implements OnClickListener{
		public void onClick(View v){
			switch(v.getId()){
			case R.id.saveButton:
				saveItem(castEditText.getText().toString());
				break;
			case R.id.startButton:
				Intent intentSub = new Intent(Sapp.this, SubSapp.class);
				ArrayList<String> n = new ArrayList<String>();

				Log.d(TAG, "すたーとボタンが押された");
				g.lenMem=0;
				Log.d(TAG, "g.lenMem=0");
				for(int i=0;i<memberList.size();i++){
					if(memberList.get(i).flg == 1){
						n.add(memberList.get(i).name);
						g.lenMem++;
					}
				}
				Log.d(TAG, g.lenCast+" "+g.lenMem);
				if(g.lenCast!=g.lenMem){
	                Toast toast2 = Toast.makeText(Sapp.this, "いあｗｗｗ\n人数と役職数合ってないからｗｗｗｗ", Toast.LENGTH_SHORT);
	                toast2.show();
					break;
				}
				
				Log.d(TAG, "リストにつめた");
				intentSub.putStringArrayListExtra("NDATA", n);
				Log.d(TAG, "NDATAにつめた");
				startActivityForResult(intentSub, 0);
				break;
			case R.id.castButton:
				Intent intentCast = new Intent(Sapp.this, CastSapp.class);
				startActivityForResult(intentCast, 0);
				break;
			}
			
		}
	}
	
    
    private void loadMember(){
      memberList.clear();
      
      // Read = 
      Cursor c = db.query(DatabaseHelper.TABLE_NAME_1, null, null, null, null, null, null);

      Log.d(TAG,"Cursor opened");
      
      
      if(c.moveToFirst()){
        do {
          Log.d(TAG,"c.move");
          Member member = new Member(
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_NO)),
            c.getString(c.getColumnIndex(DatabaseHelper.COL_NAME)),
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_FLAG)),
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_SCORE))
            );
          memberList.add(member);
          Log.d(TAG,"なまえは・・・"+member.name);
        } while(c.moveToNext());
      }
      c.close();
      
      listAdapter.notifyDataSetChanged();
    }
    
    protected void saveItem(String name){
    	Log.d(TAG,"saveItem");
    	helper.addMember(db,name);
    	castEditText.setText("");
    	loadMember();
    }
    
    private void loadLen(){
    	g.lenCast=0;
        Cursor c = db.query(DatabaseHelper.TABLE_NAME_2, null, "flg=1", null, null, null, null);
        if(c.moveToFirst()){
            do {
            	g.lenCast+=c.getInt(c.getColumnIndex(DatabaseHelper.COL_AMOUNT));
            } while(c.moveToNext());
         }
        Log.d(TAG,"g.lenCast="+g.lenCast);
         c.close();
         Log.d(TAG,"c.close");
    }
    
  @Override
  public boolean onContextItemSelected(MenuItem item) {
  
    switch(item.getItemId()){
    case MENUITEM_ID_DELETE:
      AdapterView.AdapterContextMenuInfo menuInfo
        = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        Member member = memberList.get(menuInfo.position);
        final int no = member.getNo();
        
      new AlertDialog.Builder(this)
      	.setTitle("けすねんな？")
      	.setPositiveButton("おけ",
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                helper.deleteMember(db,no);
                Toast toast1 = Toast.makeText(Sapp.this, "消したった", Toast.LENGTH_SHORT);
                toast1.show();
                loadMember();
            }
          })
        .setNegativeButton("いやや",null)
        .show();
      return true;
    }
    return super.onContextItemSelected(item);
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