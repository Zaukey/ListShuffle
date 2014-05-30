package com.android.sapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class MemberListAdapter extends ArrayAdapter<Member>{
	  	private ArrayList<Member> members;
	  	private LayoutInflater inflater;
	  
	  	public MemberListAdapter(Context context, int textViewResourceId, ArrayList<Member> members) {
	  		super(context, textViewResourceId);
	  		this.members = members;
	  		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  	}
	  	
		@Override
		public int getCount() {
			return members.size();
		}

		@Override
		public Member getItem(int position) {
				return members.get(position);
		}

		@Override
		public long getItemId(int position) {
				return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder;
			TextView nt;
			View view = convertView;
			if(view==null){
				view = this.inflater.inflate(R.layout.row, null);
				holder = new ViewHolder();
				holder.tv = (TextView)view.findViewById(R.id.noteTextView);
				view.setTag(holder);
			}else{
				holder = (ViewHolder)view.getTag();
			}
			
			Member member = (Member)members.get(position);
			if(member != null){
				CheckBox cb = (CheckBox)view.findViewById(R.id.CheckBox); 
				final int p = position;
				cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						int f = members.get(p).getFlg();
						if(isChecked){
							members.get(p).setFlg(1);
						}else{
							members.get(p).setFlg(0);
						}
						if(f!=members.get(p).getFlg()){
								Sapp.helper.onUpdateMember(Sapp.db,members.get(p));
						}
					}
				});
	            if(member.getFlg() == 1){
	            	cb.setChecked(true);
	             	}else{
	                cb.setChecked(false);
	            }
	            nt = (TextView)view.findViewById(R.id.NumberText);
				nt.setText("Score: "+String.valueOf(member.getScore()));
				holder.tv.setText(member.getName());
			}

      
			return view;
		}
		
		class ViewHolder{
			public TextView tv;
		}
		
		
  	}