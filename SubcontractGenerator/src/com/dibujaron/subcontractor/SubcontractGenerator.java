package com.dibujaron.subcontractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SubcontractGenerator {
	
	public static void main(String[] args){
		String executionPath = System.getProperty("user.dir");
		File sourceFolder = new File(executionPath + File.separator + "to Process");
		if(!sourceFolder.exists()) sourceFolder.mkdirs();
		
		try{
			for(File f : sourceFolder.listFiles()){
				if(f.getName().endsWith(".cert")){
					System.out.println("Creating subcontracts for: " + f.getName());
					process(f);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private static void process(File f) throws IOException {
		Certification cert = new Certification(f.getAbsolutePath());
		System.out.println(cert.getDescription());
		String shipclass = cert.getIdentifier();
		
		File directory = f.getParentFile();
		
		//generate armor 1
		generateShipclassSubCert(directory, cert, "armor", 1, 2);
		
		//generate armor 2
		generateShipclassSubCert(directory, cert, "armor", 2, 6);
		
		//generate armor 3
		if(isCombatShipclass(shipclass)){
			generateShipclassSubCert(directory, cert, "armor", 3, 14);
		}
		
		//generate speed 1
		generateShipclassSubCert(directory, cert, "speed", 1, 2);
		
		//generate speed 2
		generateShipclassSubCert(directory, cert, "speed", 2, 6);
		
		//generate speed 3
		if(!isCombatShipclass(shipclass)){
			generateShipclassSubCert(directory, cert, "speed", 3, 14);
		}
		//generate capacity 1
		generateShipclassSubCert(directory, cert, "capacity", 1, 2);
		
		//generate capacity 2
		generateShipclassSubCert(directory, cert, "capacity", 2, 6);
		
		//generate capacity 3
		if(!isCombatShipclass(shipclass)){
			generateShipclassSubCert(directory, cert, "capacity", 3, 14);
		}
		//generate guns 1
		generateShipclassSubCert(directory, cert, "guns", 1, 2);
		
		//generate guns 2
		generateShipclassSubCert(directory, cert, "guns", 2, 6);
		
		//generate guns 3
		if(isCombatShipclass(shipclass)){
			generateShipclassSubCert(directory, cert, "guns", 3, 14);
		}
		
		//generate captain cert
		generateCaptainCert(directory, cert);
	}
	
	private static boolean isCombatShipclass(String shipclass){
		switch(shipclass.toLowerCase()){
			case "fighter":
			case "starfighter":
			case "gunship":
			case "cruiser":
			case "dreadnought":
			case "pursuer":
			case "interceptor":
			case "carrier":
			case "warpcarrier":
			case "warpdreadnought":
				return true;
			default:
				return false;
		}
	}
	
	private static void generateShipclassSubCert(File directory, Certification cert, String type, int level, int incrementation) throws IOException{
		String shipClass = cert.getIdentifier();
		HashMap<String,String> values = new HashMap<String, String>();
		ArrayList<String> onAdd = new ArrayList<String>();
		ArrayList<String> onRemove = new ArrayList<String>();
		
		values.put("default", "" + false);
		values.put("identifier", shipClass + "_" + type + level);
		values.put("description", "Upgrade your " + shipClass + " to support level " + level + " " + type);
		if(level == 1){
		values.put("preRequisites", shipClass);
		values.put("altPreRequisites", shipClass);
		} else {
			String preReq = shipClass;
			for(int i = 1; i < level; i++){
				String miniPrereq = shipClass + "_" + type + i;
				preReq = preReq + ", " + miniPrereq;
			}
			values.put("preRequisites", preReq);
			values.put("altPreRequisites", preReq);
		}
		values.put("costCredits", "" + ((int) level * cert.getCostInCurrency("credits") / 10));
		values.put("subCertCheckExempt", "false");
		onAdd.add("perms player {name} set movecraft." + shipClass + "." + type + "." + level);
		onRemove.add("perms player {name} unset movecraft." + shipClass + "." + type + "." + level);
		putMinValues(values, cert.values, incrementation);
		Certification armor1 = new Certification(values, onAdd, onRemove);
		File writeFile = new File(directory + File.separator + "finished" + File.separator + shipClass + "_" + type + level + ".cert");
		if(!writeFile.exists()){
			writeFile.getParentFile().mkdirs();
			writeFile.createNewFile();
		}
		armor1.writeToFile(writeFile);
	}
	
	private static void generateCaptainCert(File directory, Certification cert) throws IOException{
		String shipClass = cert.getIdentifier();
		HashMap<String,String> values = new HashMap<String, String>();
		ArrayList<String> onAdd = new ArrayList<String>();
		ArrayList<String> onRemove = new ArrayList<String>();
		
		values.put("default", "" + false);
		values.put("identifier", shipClass + "_captain");
		values.put("description", "A captain title for " + shipClass + ".");
		
		values.put("tag", capitalizeFirstLetter(shipClass) + " Captain");
		if(isCombatShipclass(shipClass)){
			values.put("preRequisites", shipClass + "_guns3");
			values.put("altPreRequisites", shipClass + "_armor3");
			values.put("tagColor", 4 + "");
			values.put("altTagColor", 1 + "");
		} else {
			values.put("preRequisites", shipClass + "_speed3");
			values.put("altPreRequisites", shipClass + "_capacity3");
			values.put("tagColor", 6 + "");
			values.put("altTagColor", 3 + "");
		}
		
		values.put("costCredits", "" + ((int) cert.getCostInCurrency("credits") / 10));
		values.put("subCertCheckExempt", "false");
		putMinValues(values, cert.values, 15);
		Certification armor1 = new Certification(values, onAdd, onRemove);
		File writeFile = new File(directory + File.separator + "finished" + File.separator + shipClass + "_captain.cert");
		if(!writeFile.exists()){
			writeFile.getParentFile().mkdirs();
			writeFile.createNewFile();
		}
		armor1.writeToFile(writeFile);
	}
	
	private static String capitalizeFirstLetter(String shipClass) {
		return shipClass.substring(0,1).toUpperCase() + shipClass.substring(1, shipClass.length());
	}

	private static void putMinValues(HashMap<String, String> values, HashMap<String, String> existing, int i){
		values.put("infamyMin", checkAndIncrement(existing.get("infamymin"), i));
		values.put("philanthropyMin", checkAndIncrement(existing.get("philanthropymin"), i));
		values.put("tradingMin", checkAndIncrement(existing.get("tradingmin"), i));
		values.put("smugglingMin", checkAndIncrement(existing.get("smugglingmin"), i));
		values.put("reputationMin", checkAndIncrement(existing.get("reputationmin"), i));
		
		values.put("altInfamyMin", checkAndIncrement(existing.get("altinfamymin"), i));
		values.put("altPhilanthropyMin", checkAndIncrement(existing.get("altphilanthropymin"), i));
		values.put("altTradingMin", checkAndIncrement(existing.get("alttradingmin"), i));
		values.put("altSmugglingMin", checkAndIncrement(existing.get("altsmugglingmin"), i));
		values.put("altReputationMin", checkAndIncrement(existing.get("altreputationmin"), i));
	}
	
	private static String checkAndIncrement(String valueString, int incrementation){
		int value = Integer.parseInt(valueString);
		if(value == 0) return "" + 0;
		else return "" + (value + incrementation);
	}
}
