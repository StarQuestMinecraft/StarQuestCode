package com.dibujaron.uuencode;

import java.io.IOException;
import java.util.UUID;

import sun.misc.UUDecoder;
import sun.misc.UUEncoder;

public class UUEncode {

	static UUEncoder uuec = new UUEncoder();
	static UUDecoder uudc = new UUDecoder();

	public static void main(String[] args){
		UUID u = UUID.randomUUID();
		System.out.println(u);
		String data = encodeUUID(u);
		System.out.println(data);
		UUID u2 = decodeUUID(data);
		System.out.println(u2);
	}
	private static String encode(byte[] data) {
		return uuec.encode(data);
	}
	
	private static byte[] decode(String data){
		try {
			return uudc.decodeBufferToByteBuffer(data).array();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static byte[] stringToBytes(String s){
		return s.getBytes();
	}
	
	private static String stringFromBytes(byte[] b){
		return new String(b);
	}
	
	public static String encodeUUID(UUID u){
		return encode(stringToBytes(u.toString()));
	}
	
	public static UUID decodeUUID(String data){
		return UUID.fromString(stringFromBytes(decode(data)));
	}
}
