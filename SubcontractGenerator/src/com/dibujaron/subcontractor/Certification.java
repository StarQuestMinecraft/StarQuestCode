package com.dibujaron.subcontractor;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Certification {

	public HashMap<String, String> values = new HashMap<String, String>();
	public ArrayList<String> onAdd = new ArrayList<String>();
	public ArrayList<String> onRemove = new ArrayList<String>();

	public Certification(HashMap<String, String> values, ArrayList<String> onAdd, ArrayList<String> onRemove){
		this.values = values;
		this.onAdd = onAdd;
		this.onRemove = onRemove;
	}
	public Certification(String path) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			for (String s : lines) {
				if (s.startsWith("#"))
					continue;
				if (s.length() == 0)
					continue;
				String[] split = s.split(": ");
				if(split.length < 2) continue;
				String key = split[0].toLowerCase();
				if (key.equals("onadd")) {
					onAdd.add(split[1]);
				} else if (key.equals("onremove")) {
					onRemove.add(split[1]);
				} else {
					values.put(key, split[1]);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(File f){
		try{
			PrintWriter w = new PrintWriter(new FileWriter(f));
			for(String s : values.keySet()){
				String value = values.get(s);
				String fnl = s + ": " + value;
				w.println(fnl);
			}
			for(String s : onAdd){
				String fnl = "onAdd: " + s;
				w.println(fnl);
			}
			for(String s : onRemove){
				String fnl = "onRemove: " + s;
				w.println(fnl);
			}
			w.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public String getIdentifier() {
		return values.get("identifier");
	}

	public String getDescription() {
		return values.get("description");
	}

	public String[] getPreRequisites() {
		String pre = values.get("prerequisites");
		if (pre.equals("none"))
			return new String[0];
		else
			return pre.split(", ");
	}

	public int getRequirementInContractCurrency(String currency) {
		String val = values.get(currency + "min");
		if (val == null)
			return 0;
		return Integer.parseInt(val);
	}

	public int getCostInCurrency(String currency) {
		try {
			return Integer.parseInt(values.get("cost" + currency));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getCost() {
		return getCostInCurrency("Credits");
	}

	public boolean satisfiesPreReqs(List<String> existing) {
		for (String s : getPreRequisites()) {
			if (!existing.contains(s))
				return false;
		}
		return true;
	}

	public String getTag() {
		return values.get("tag");
	}

	public String getTagColor() {
		return "§" + values.get("tagcolor");
	}


	public int getMaxSubCerts() {
		String val = values.get("maxsubcerts");
		if (val == null)
			return 0;
		return Integer.parseInt(val);
	}

	public String[] getConflicts() {
		String mut = values.get("conflicts");
		if (mut.equals("none"))
			return new String[0];
		else
			return mut.split(", ");
	}


	private String formatStringArray(String[] str) {
		if (str.length == 1)
			return str[0];
		if (str.length == 2)
			return str[0] + " and " + str[1];
		String retval = "";
		for (int i = 0; i < str.length - 1; i++) {
			retval = retval + str[i] + ", ";
		}
		retval = retval + " and " + str[str.length - 1];
		return retval;
	}

}
