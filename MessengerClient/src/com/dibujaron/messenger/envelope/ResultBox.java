package com.dibujaron.messenger.envelope;

import java.io.Serializable;
import java.sql.ResultSet;

public class ResultBox implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ResultSet set;
	private int id;
	
	public ResultBox(ResultSet set, int id){
		this.set = set;
		this.id = id;
	}
	
	public ResultSet getResultSet(){
		return set;
	}
	
	public int getId(){
		return id;
	}
}
