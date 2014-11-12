package com.dibujaron.messenger.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.dibujaron.messenger.envelope.Envelope;

public class ConnectionHandler implements Runnable{
	
	String name;
	private boolean connected = false;
	Socket s = null;
	
	private DataInputStream din;
	private DataOutputStream dout;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	
	public ConnectionHandler(String name){
		this.name = name;
		try{
			s = new Socket("localhost", 6066);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!connected){
				connect();
				
			}
			
			boolean done = false;
			while(!done){
				try {
					Object o = oin.readObject();
					if(o != null){
						Envelope e = (Envelope) o;
						PostOffice.distributeMessage(e);
					} else {
						done = true;
					}
				} catch (IOException e){
					done = true;
				} catch (ClassNotFoundException e) {
					//this will never happen
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(Envelope e){
		try {
			oout.writeObject(e);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void connect(){
		System.out.println("Attempting Connection.");
		try{			
			InputStream i = s.getInputStream();
			OutputStream o = s.getOutputStream();

			din = new DataInputStream(i);
			dout = new DataOutputStream(o);
			oin = new ObjectInputStream(i);
			oout = new ObjectOutputStream(o);
			
			dout.writeUTF(name);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
