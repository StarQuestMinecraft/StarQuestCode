package com.starquestminecraft.sqcontracts.database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileDatabase implements Database {

	String path;

	public FileDatabase(String path) {
		this.path = path;
	}

	@Override
	public ContractPlayerData getDataOfPlayer(UUID u) {
		File f = new File(path + u);
		if (!f.exists()) {
			return ContractPlayerData.createDefault(u);
		} else {
			ContractPlayerData retval;
			try{
				retval = loadFromFile(f, u);
			} catch(Exception e){
				e.printStackTrace();
				return null;
			}
			return retval;
		}
	}

	@Override
	public void updatePlayerData(UUID u, ContractPlayerData d) {
		File f = new File(path + u);
		if (!f.exists()) {
			f.mkdirs();
		}
		try {
			saveToFile(f, u, d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ContractPlayerData loadFromFile(File f, UUID u) throws Exception {
		InputStream file = new FileInputStream(f);
		InputStream buffer = new BufferedInputStream(file);
		ObjectInput input = new ObjectInputStream(buffer);
		ContractPlayerData data = (ContractPlayerData) input.readObject();
		input.close();
		return data;
	}

	private void saveToFile(File f, UUID u, ContractPlayerData d) throws Exception {
		OutputStream file = new FileOutputStream(f);
		OutputStream buffer = new BufferedOutputStream(file);
		ObjectOutput output = new ObjectOutputStream(buffer);
		output.writeObject(d);
		output.close();
	}

	@Override
	public ContractPlayerData[] getAllPlayerData() {
		// TODO Auto-generated method stub
		return null;
	}
}
