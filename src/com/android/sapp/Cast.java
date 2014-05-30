package com.android.sapp;

import java.io.Serializable;

public class Cast implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int no;
	protected String name;
	protected int flg;
	protected int camp;
	protected int amount;

	public Cast(int no, String name, int camp, int flg, int amount) {
		this.camp = camp;
		this.name = name;
		this.flg = flg;
		this.no = no;
		this.amount = amount;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getCamp(){
		return camp;
	}
	public void setCamp(int camp){
		this.camp = camp;
	}
	

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}
	

	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}
	
	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}

	
}
