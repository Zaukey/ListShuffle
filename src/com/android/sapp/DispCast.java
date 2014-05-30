package com.android.sapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DispCast extends Activity {
	
	static ArrayAdapter<Player> aAdapter;
	static ArrayList<Player> dispP;
	Globals g;
	ListView slv;
    
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.sub);

       slv = (ListView)findViewById(R.id.subListView);
       dispP =  new ArrayList<Player>();
       Button ok = (Button)findViewById(R.id.okButton);
       g = (Globals)this.getApplication();
       
       ok.setText("ホームに戻る");
       ok.setOnClickListener(new OnClickListener() {
   		
   		@Override
   		public void onClick(View v) {
   			switch(v.getId()){
   			case R.id.okButton:
   				Log.d("DispCast","g初期化しまー");
   				g.init();
   				Intent back = new Intent(DispCast.this, Sapp.class);
   				Log.d("DispCast","ホームもどりまー");
   				startActivityForResult(back, 0);
   				break;
   			}
   		}
        });
       
       
       Log.d("DispCast","Player初期化");
       dispP.clear();
       Log.d("DispCast","Player読み込み");
       for(int i=0;i<g.dispM.size();i++){
    	   addPlayer(i,g.dispM.get(i),g.dispC.get(i),0);
       }
       
       
       
       Log.d(Sapp.TAG, "アダプタ設定");
       aAdapter = new DispListAdapter(this, android.R.layout.simple_list_item_1, dispP);
       slv.setAdapter(aAdapter);
       Log.d(Sapp.TAG, "アダプタ設定完了");
   }
   
   

   class DispListAdapter extends ArrayAdapter<Player>{
 	  private ArrayList<Player> players;
 	  private LayoutInflater inflater;
 	  private int res;

 	  public DispListAdapter(Context context,int textViewResourceId, ArrayList<Player> players) {
 		  super(context, textViewResourceId);
 		  this.players = players;
 		  this.res = textViewResourceId;
 		  this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 		
 	  }

 	  @Override
 	  public int getCount() {
 		  return players.size();
 	  }

 	  @Override
 	  public Player getItem(int position) {
 		  return players.get(position);
 	  }

 	  @Override
 	  public long getItemId(int position) {
 		  return position;
 	  }

 	  public View getView(int position, View convertView, ViewGroup parent) {
 			ViewHolder holder;
 			View view = convertView;
 		 	Player player = players.get(position);
 			if(view==null){
 				view = this.inflater.inflate(res, null);
 				holder = new ViewHolder();
 				holder.tv = (TextView)view.findViewById(android.R.id.text1);
 				view.setTag(holder);
 			}else{
 				holder = (ViewHolder)view.getTag();
 			}
 			
 				slv.setOnItemClickListener(new OnItemClickListener() {
 					@Override
 					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Log.d("DispCast","おされたよ");
						int d = players.get(position).death;
						int[] co = {Color.RED,Color.WHITE};
						players.get(position).death = d==1 ? 0 : 1;
		 				view.setBackgroundColor(co[d]);
					}
				});
 				
 				holder.tv.setText(player.name + " → " + player.cast);
 			return view;
 		}
 	  
 		class ViewHolder{
 			public TextView tv;
 		}
 		
 	  
   }
	
	void addPlayer(int i ,String name, String cast, int death){
		dispP.add(i,new Player(name,cast,death));
	}
	
	class Player{
		String name;
		String cast;
		int death;
	
		public Player(String name, String cast, int death) {
			this.name = name;
			this.cast = cast;
			this.death = death;
		}
	}
	
	
   
}
   

