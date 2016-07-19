package com.whirlwindgames.dibujaron.sqempire.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RSReader {

	//a class to catch all of ResultSet's exceptions that demand attention for no reason every time.
	
	private ResultSet rs;
	
	public RSReader(ResultSet rs){
		this.rs = rs;
	}
	
	public boolean next(){
		try{
			return rs.next();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public Date getDate(String col) {
		try{
			return rs.getDate(col);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return new GregorianCalendar().getTime();
	}
	
	public String getString(String col){
		try{
			return rs.getString(col);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return "";
	}
	
	public int getInt(String col){
		try{
			return rs.getInt(col);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public long getLong(String col){
		try{
			return rs.getLong(col);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1L;
	}
	
}
