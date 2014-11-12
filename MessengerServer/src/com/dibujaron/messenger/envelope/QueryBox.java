package com.dibujaron.messenger.envelope;

import java.io.Serializable;
import com.mysql.jdbc.PreparedStatement;

public class QueryBox implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private PreparedStatement query;
	private int id;
	
	public QueryBox(PreparedStatement query, int id){
		this.query = query;
		this.id = id;
	}
	
	public PreparedStatement getQuery(){
		return query;
	}
	
	public int getId(){
		return id;
	}
}
