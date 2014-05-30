package com.android.sapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CastSapp extends Activity{
  
  static final String TAG = "SQLiteTest2";
  static final int MENUITEM_ID_DELETE = 1;
  static final int MENUITEM_ID_EDIT = 2;
  ListView castListView;
  EditText castText;
  Button  castSaveBotton;
  static CastListAdapter listAdapter = null;
  static ArrayList<Cast> castList = new ArrayList<Cast>();
  static DatabaseHelper helper;
  static SQLiteDatabase db;
  Globals g;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.cast);

		Log.d(TAG, "ÉåÉCÉAÉEÉgÇÌÇ∏");
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        g = (Globals)this.getApplication();

        
		Log.d(TAG, "dbÉZÉbÉgÇ®ÇÌÇË");
        findViews();
		Log.d(TAG, "setListeners()");
        setListeners();

		Log.d(TAG, "ÉAÉ_ÉvÉ^Å[ê›íË");
        listAdapter = new CastListAdapter(this, R.layout.row, castList);
		Log.d(TAG, "ÉAÉ_ÉvÉ^ÇπÇ¡Ç∆");
        castListView.setAdapter(listAdapter);
        
        
		Log.d(TAG, "CastÇÉçÅ[Éh");
        loadCast();
    }
    
    protected void findViews(){
    	castListView = (ListView)findViewById(R.id.castListView);
    	castSaveBotton = (Button)findViewById(R.id.castSave);
    	castText = (EditText)findViewById(R.id.castNameText);
    }

	protected void setListeners(){
		Log.d(TAG, "castSaveBottonListener");
    	castSaveBotton.setOnClickListener(new ClickListener());

		Log.d(TAG, "castListViewListener");
    	castListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.add(0, MENUITEM_ID_DELETE, 0, "Ç≥Ç≠Ç∂ÇÂ");
				menu.add(0, MENUITEM_ID_EDIT, 0, "êwâc");
				
			}
    	});
    	
		Log.d(TAG, "ÇËÇ∑ÇπÇ¡Ç∆Ç®ÇÌ");
    }
	
	class ClickListener implements OnClickListener{
		public void onClick(View v){
			switch(v.getId()){
			case R.id.castSave:
				saveItem(castText.getText().toString());
				break;
			}
		}
	}
	
    
    private void loadCast(){
      Log.d(TAG, "clear");
      castList.clear();
      
      // Read = 
      Log.d(TAG, "queryê∂ê¨");
      Cursor c = db.query(DatabaseHelper.TABLE_NAME_2, null, null, null, null, null, DatabaseHelper.COL_CAMP+" ASC");

      Log.d(TAG, "queryê∂ê¨èIÇÌÇË"+c.moveToFirst());
      
      if(c.moveToFirst()){
        do {
          Cast cast = new Cast(
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_NO)),
            c.getString(c.getColumnIndex(DatabaseHelper.COL_NAME)),
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_CAMP)),
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_FLAG)),
            c.getInt(c.getColumnIndex(DatabaseHelper.COL_AMOUNT))
            );
          castList.add(cast);
          Log.d(TAG,"ñêEñºÇÕÅEÅEÅE"+cast.name);
        } while(c.moveToNext());
      }
      Log.d(TAG, "c.close");
      c.close();

      Log.d(TAG, "notify");
      listAdapter.notifyDataSetChanged();
      Log.d(TAG, "notifyÇ®ÇÌÇË");
      
      g.lenCast=0;
      for(int j=0;j<castList.size();j++){
		  if(castList.get(j).flg==1) g.lenCast++;
	  }
      Log.d(Sapp.TAG,"lenCast = "+g.lenCast);
    }
    
    protected void saveItem(String name){
      helper.addCast(db,name);
      castText.setText("");
      loadCast();
    }
    
    
    
  @Override
  public boolean onContextItemSelected(MenuItem item) {

	AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	final int p = menuInfo.position;
    switch(item.getItemId()){
    	case MENUITEM_ID_DELETE:
    		Cast cast = castList.get(menuInfo.position);
    		final int no = cast.getNo();
    		new AlertDialog.Builder(this)
     		.setTitle("ÇØÇ∑ÇÀÇÒÇ»ÅH")
     		.setPositiveButton("Ç®ÇØ",
     			new DialogInterface.OnClickListener() {
     				@Override
     				public void onClick(DialogInterface dialog, int which) {
     					helper.deleteCast(db,no);
     					Toast toast = Toast.makeText(CastSapp.this, "Ç≥Ç≠Ç∂ÇÂÇµÇΩÇÌ", Toast.LENGTH_SHORT);
     					toast.show();
     					loadCast();
     				}
     		})
     		.setNegativeButton("Ç¢Ç‚Ç‚",null)
     		.show();
    		return true;
    	case MENUITEM_ID_EDIT:
    		final String[] jin={"ésñØêwâc","êlòTêwâc","óºêwâc","ódåœêwâc"};
    		new AlertDialog.Builder(this)
    			.setTitle("êwâcëIë")
    			.setItems(jin, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						castList.get(p).setCamp(which);
						helper.onUpdateCast(db, castList.get(p));
     					loadCast();
					}
				}).create().show();
    }
    return super.onContextItemSelected(item);
  }
  
  
  class CastListAdapter extends ArrayAdapter<Cast>{
	  private ArrayList<Cast> casts;
	  private LayoutInflater inflater;
	  private int res;

	  public CastListAdapter(Context context,int textViewResourceId, ArrayList<Cast> casts) {
		  super(context, textViewResourceId);
		  this.casts = casts;
		  this.res = textViewResourceId;
		  this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	  }

	  @Override
	  public int getCount() {
		  return casts.size();
	  }

	  @Override
	  public Cast getItem(int position) {
		  return casts.get(position);
	  }

	  @Override
	  public long getItemId(int position) {
		  return position;
	  }

	  public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			View view = convertView;
			if(view==null){
				view = this.inflater.inflate(res, null);
				holder = new ViewHolder();
				holder.tv = (TextView)view.findViewById(R.id.noteTextView);
				holder.nt = (TextView)view.findViewById(R.id.NumberText);
				view.setTag(holder);
			}else{
				holder = (ViewHolder)view.getTag();
			}
			
			Cast cast = (Cast)casts.get(position);
			if(cast != null){
				CheckBox cb = (CheckBox)view.findViewById(R.id.CheckBox); 
				final int p = position;
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						int f = casts.get(p).getFlg();
						if(isChecked){
							casts.get(p).setFlg(1);
						}else{
							casts.get(p).setFlg(0);
						}
						if(f!=casts.get(p).getFlg()){
							CastSapp.helper.onUpdateCast(db,casts.get(p));
						}
					}
				});

				castListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Log.d("CastSapp","Ç®Ç≥ÇÍÇΩÇÊ");
						int pcamp = casts.get(position).amount;
						if(pcamp<5) casts.get(position).amount++;
						else casts.get(position).amount=0;
						Log.d("CastSapp","pcamp="+pcamp);
						helper.onUpdateCast(db, casts.get(position));
						loadCast();
					}
				});
				
				
				int[] color = {Color.GREEN,Color.RED,Color.YELLOW,Color.GRAY};
				view.setBackgroundColor(color[cast.getCamp()]);
				
				
	            if(cast.getFlg() == 1){
	            	cb.setChecked(true);
	             	}else{
	                cb.setChecked(false);
	            }
				holder.nt.setText("Å~"+String.valueOf(cast.getAmount()));
				holder.tv.setText(cast.getName());
			}

    
			return view;
		}
	  
		class ViewHolder{
			public TextView tv;
			public TextView nt;
		}
	  
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
      g.lenCast=0;
      for(int j=0;j<castList.size();j++){
		  if(castList.get(j).flg==1) g.lenCast+=castList.get(j).amount;
	  }
      Log.d(Sapp.TAG,"lenCast = "+g.lenCast);
      
  }

}