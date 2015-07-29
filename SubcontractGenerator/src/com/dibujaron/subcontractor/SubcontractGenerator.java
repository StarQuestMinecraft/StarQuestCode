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
	
	private static void generateShipclassSubCert(File directory, Certification cert, String type, int level, int incrementation) throws IOException{
		String shipClass = cert.getIdentifier();
		HashMap<String,String> values = new HashMap<String, String>();
		ArrayList<String> onAdd = new ArrayList<String>();
		ArrayList<String> onRemove = new ArrayList<String>();
		
		values.put("default", "" + false);
		values.put("identifier", shipClass + "_" + type + level);
		values.put("description", "Upgrade your " + shipClass + " to support level " + level + " " + type);
		if(level == 1){
			values.put("lawfulPreRequisites", shipClass);
			values.put("outlawPreRequisites", shipClass);
		} else {
			String preReq = shipClass;
			/*for(int i = 1; i < level; i++){
				String miniPrereq = shipClass + "_" + type + i;
				preReq = preReq + ", " + miniPrereq;
			}*/
			preReq = preReq + "_" + type + (level-1);
			values.put("lawfulPreRequisites", preReq);
			values.put("outlawPreRequisites", preReq);
		}
		values.put("costCredits", "" + (30 * cert.getCostInCurrency("credits") / 100));
		values.put("subCertCheckExempt", "false");
		values.put("relevantShipClass", shipClass);
		values.put("maxUpgrades", "" + 4);
		onAdd.add("perms player {name} set movecraft." + shipClass + "." + type + "." + level);
		onRemove.add("perms player {name} unset movecraft." + shipClass + "." + type + "." + level);
		putMinValues(values, cert.values, incrementation);
		if(level == 3){
			lockForClass(values, cert.values, type, incrementation);
		}
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
		values.put("relevantShipClass", shipClass);
		values.put("minUpgrades", "" + 5);
		String tag = capitalizeFirstLetter(shipClass) + " Captain";
		values.put("lawfulTag", tag);
		values.put("outlawTag", tag);
		if(isCombatShipclass(shipClass)){
			values.put("outlawTagColor", 4 + "");
			values.put("lawfulTagColor", 1 + "");
		} else {
			values.put("outlawTagColor", 6 + "");
			values.put("lawfulTagColor", 3 + "");
		}
		
		values.put("costCredits", "" + 0);
		values.put("subCertCheckExempt", "false");
		putMinValues(values, cert.values, 15);
		Certification cap = new Certification(values, onAdd, onRemove);
		File writeFile = new File(directory + File.separator + "finished" + File.separator + shipClass + "_captain.cert");
		if(!writeFile.exists()){
			writeFile.getParentFile().mkdirs();
			writeFile.createNewFile();
		}
		cap.writeToFile(writeFile);
	}
	
	private static boolean isCombatShipclass(String shipClass) {
		switch(shipClass.toLowerCase()){
		case "carrier":
		case "cruiser":
		case "dreadnought":
		case "fighter":
		case "flagdreadnought":
		case "gunship":
		case "interceptor":
		case "pursuer":
		case "tank":
		case "warmachine":
			return true;
		default:
			return false;
		}
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
		values.put("tradingOrSmugglingMin", checkAndIncrement(existing.get("tradingorsmugglingmin"), i));
		values.put("reputationOrInfamyMin", checkAndIncrement(existing.get("reputationorinfamymin"), i));
		values.put("totalMin", checkAndIncrement(existing.get("totalmin"), i));
	}
	
	private static void lockForClass(HashMap<String, String> values, HashMap<String, String> existing, String type, int i){
		if(type == "guns"){
			values.put("infamyMin", checkAndIncrement(existing.get("reputationorinfamymin"), i));
			return;
		}
		if(type == "armor"){
			values.put("reputationMin", checkAndIncrement(existing.get("reputationorinfamymin"), i));
			return;
		}
		if(type == "capacity"){
			values.put("tradingMin", checkAndIncrement(existing.get("tradingorsmugglingmin"), i));
			return;
		}
		if(type == "speed"){
			values.put("smugglingMin", checkAndIncrement(existing.get("tradingorsmugglingmin"), i));
			return;
		}
	}
	
	private static String checkAndIncrement(String valueString, int incrementation){
		int value = Integer.parseInt(valueString);
		if(value == 0) return "" + 0;
		else return "" + (value + incrementation);
	}
}
