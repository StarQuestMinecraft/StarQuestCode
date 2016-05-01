package com.whirlwindgames.dibujaron.sqempire.util;

public class EmpireAsyncException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EmpireAsyncException(){
		super("Attempted to access an async-only method from the main thread.");
	}

}
