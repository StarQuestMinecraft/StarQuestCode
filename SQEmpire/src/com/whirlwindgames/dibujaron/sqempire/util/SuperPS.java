package com.whirlwindgames.dibujaron.sqempire.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class SuperPS {

	PreparedStatement ps;
	int numArgs;
	
	public SuperPS(PreparedStatement ps, int numArgs){
		this.ps = ps;
		this.numArgs = numArgs;
	}
	public void setDuplicate(int firstIndex, String value){
		try {
			ps.setString(firstIndex, value);
			ps.setString(firstIndex + numArgs, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setInt(int firstIndex, int value){
		try {
			ps.setInt(firstIndex, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setString(int firstIndex, String value){
		try {
			ps.setString(firstIndex, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setBoolean(int i, boolean g) {
		try {
			ps.setBoolean(i, g);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setLong(int i, long timestamp) {
		try {
			ps.setLong(i, timestamp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setDuplicate(int firstIndex, int value){
		try {
			ps.setInt(firstIndex, value);
			ps.setInt(firstIndex + numArgs, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setDuplicate(int firstIndex, long value){
		try {
			ps.setLong(firstIndex, value);
			ps.setLong(firstIndex + numArgs, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setDuplicate(int firstIndex, Date value){
		try {
			ps.setDate(firstIndex, value);
			ps.setDate(firstIndex + numArgs, value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void executeAndClose(){
		try{
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
