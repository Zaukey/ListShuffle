package com.android.sapp;

import java.io.Serializable;

public class Member implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int no;
	protected String name;
	protected int flg;
	protected int score;
	

	public Member(int no, String name, int flg, int score){
		this.no = no;
		this.name = name;
		this.flg = flg;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNo(){
		return no;
	}
	
	public int getFlg(){
		return flg;
	}
	

	
	public void setFlg(int flg){
		this.flg = flg;
	}

	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
