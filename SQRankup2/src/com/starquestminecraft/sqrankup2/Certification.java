package com.starquestminecraft.sqrankup2;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqcontracts.SQContracts;
import com.starquestminecraft.sqcontracts.database.ContractPlayerData;


public class Certification {
	
	private HashMap<String, String> values = new HashMap<String, String>();
	private ArrayList<String> onAdd = new ArrayList<String>();
	private ArrayList<String> onRemove = new ArrayList<String>();
	
	public Certification(String path){
		try {
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			for(String s : lines){
				if(s.startsWith("#")) continue;
				if(s.length() == 0) continue;
				String[] split = s.split(": ");
				String key = split[0].toLowerCase();
				if(key.equals("onadd")){
					onAdd.add(split[1]);
				} else if(key.equals("onremove")){
					onRemove.add(split[1]);
				} else {
					if(split.length > 1){
						values.put(key, split[1]);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getIdentifier(){
		return values.get("identifier");
	}
	
	public String getDescription(){
		return values.get("description");
	}
	
	public boolean isDefault(){
		String s = values.get("default");
		if(s == null) return false;
		return Boolean.parseBoolean(values.get("default"));
	}
	
	public String[] getLawfulPreRequisites(){
		String pre = values.get("lawfulprerequisites");
		if(pre == null || pre.equals("none")) return new String[0];
		else return pre.split(", ");
	}
	
	public String[] getOutlawPreRequisites(){
		String pre = values.get("outlawprerequisites");
		if(pre == null || pre.equals("none")) return new String[0];
		else return pre.split(", ");
	}
	
	public int getRequirementInContractLevels(String currency){
		String val = values.get(currency.toLowerCase() + "min");
		if(val == null) return 0;
		return Integer.parseInt(val);
	}
	
	public int getCostInCC3Currency(String currency){
		try{
			return Integer.parseInt(values.get("cost" + currency));
		} catch (Exception e){
			return 0;
		}
	}
	
	public int getCost(){
		return getCostInCC3Currency("credits");
	}
	
	public boolean satisfiesCurrencyRequirements(ContractPlayerData d){
		int ttl = 0;
		for(String s : ContractPlayerData.getCurrencies()){
			int pBal = d.getBalanceInCurrency(s);
			ttl += pBal;
			int reqBal = getRequirementInContractLevels(s);
			if(pBal < reqBal) return false;
		}
		if(SQRankup2.getBalanceInCombat(d) < getRequirementInContractLevels("reputationOrInfamy")) return false;
		if(SQRankup2.getBalanceInTrade(d) < getRequirementInContractLevels("tradingOrSmuggling")) return false;
		if(ttl < getRequirementInContractLevels("total")) return false;
		return true;
	}
		
	public boolean satisfiesLawfulPreReqs(List<String> existing){
		for(String s : existing) System.out.println("existing: " + s);
		for(String s : getLawfulPreRequisites()){
			System.out.println("testing prereq: " + s);
			if(!existing.contains(s) && !existing.contains("alt-" + s)){
				System.out.println("Existing does not contain!");
				return false;
			}
		}
		System.out.println("Passed!");
		return true;
	}
	
	public boolean satisfiesOutlawPreReqs(List<String> existing){
		for(String s : existing) System.out.println("existing: " + s);
		for(String s : getOutlawPreRequisites()){
			System.out.println("testing prereq: " + s);
			if(!existing.contains(s) && !existing.contains("alt-" + s)){
				System.out.println("Existing does not contain!");
				return false;
			}
		}
		System.out.println("Passed!");
		return true;
	}
	

	public String getTag(){
		return values.get("lawfultag");
	}
	
	
	public String getTagColor(){
		String tagColor = values.get("lawfultagcolor");
		if(tagColor == null) return null;
		return "§" + tagColor;
	}
	
	public String getTagFormatted(){
		return getTagFormatted(ChatColor.WHITE);
	}
	
	public String getTagFormatted(ChatColor previous){
		if(getTag() == null) return null;
		return ChatColor.WHITE + "[" + getTagColor() + getTag() + ChatColor.WHITE + "]" + previous;
	}
	
	public String getOutlawTag(){
		String tag = values.get("outlawtag");
		if(tag == null) return getTag();
		return tag;
	}
	
	
	public String getOutlawTagColor(){
		String tagColor = values.get("outlawtagcolor");
		if(tagColor == null) return getTagColor();
		return "§" + tagColor;
	}
	

	public String getOutlawTagFormatted() {
		return getOutlawTagFormatted(ChatColor.WHITE);
	}
	
	public String getOutlawTagFormatted(ChatColor previous){
		if(getOutlawTag() == null) return null;
		return ChatColor.WHITE + "[" + getOutlawTagColor() + getOutlawTag() + ChatColor.WHITE + "]" + previous;
	}

	
	public int getMaxSubCerts(){
		String val = values.get("maxsubcerts");
		if(val == null) return 0;
		return Integer.parseInt(val);
	}
	
	public String[] getConflicts(){
		String mut = values.get("mutuallyExclusives");
		if(mut == null || mut.equals("none")) return new String[0];
		else return mut.split(", ");
	}
	
	public void giveToPlayer(Player p){
		for(String s : onAdd){
			String cmd = s;
			cmd = cmd.replaceAll("\\{name\\}", p.getName());
			cmd = cmd.replaceAll("\\{uuid\\}", p.getUniqueId().toString());
			cmd = cmd.replaceAll("\\{world\\}", p.getWorld().getName());
			cmd = cmd.replaceAll("\\{server\\}", Bukkit.getServerName());
			System.out.println("dispatching: " + cmd);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
	}
	
	public void removeFromPlayer(Player p){
		for(String s : onRemove){
			String cmd = s;
			cmd = cmd.replaceAll("\\{name\\}", p.getName());
			cmd = cmd.replaceAll("\\{uuid\\}", p.getUniqueId().toString());
			cmd = cmd.replaceAll("\\{world\\}", p.getWorld().getName());
			cmd = cmd.replaceAll("\\{server\\}", Bukkit.getServerName());
			System.out.println("dispatching: " + cmd);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
		}
	}

	public String getFullDescription(ChatColor color) {
		
		String retval = getIdentifier() + ": " + getDescription();
		retval = retval + getTagDescriptionFormatted(color) + 
		".\nRequires: " + printRequirements() + 
		".\nPrerequisite Certs: " + printPreReqs() + 
		"\nConflicts with: " + printConflicts() + 
		"\nCosts " + getCostInCC3Currency("credits") + " credits.";
		return retval;
	}

	private String getTagDescriptionFormatted(ChatColor color) {
		String tag = getTagFormatted(color);
		if(tag == null) return "";
		String altTag = getOutlawTagFormatted(color);
		if(altTag.equals(tag)){
			return "\nUnlocks the tag " + tag;
		} else {
			return "\nUnlocks the tag " + tag + " (lawful) or " + altTag + " (outlaw)";
		}
	}

	private String printConflicts() {
		String[] conflicts = getConflicts();
		if(conflicts.length == 0) return "none";
		return formatStringArray(conflicts);
	}

	private String printPreReqs() {
		String[] preReqs = getLawfulPreRequisites();
		String[] altReqs = getOutlawPreRequisites();
		if(altReqs.length == 0){
			if(preReqs.length == 0){
				return "none";
			} else {
				return formatStringArray(preReqs);
			}
		}
		else {
			return formatStringArray(preReqs) + " (lawful) or  " + formatStringArray(altReqs) + " (outlaw)";
		}
	}
	
	private String formatStringArray(String[] str){
		if(str.length == 0) return "";
		if(str.length == 1) return str[0];
		if(str.length == 2) return str[0] + " and " + str[1];
		String retval = "";
		for(int i = 0; i < str.length - 1; i++){
			retval = retval + str[i] + ", ";
		}
		retval = retval + " and " + str[str.length - 1];
		return retval;
	}
	
/*for(String s : ContractPlayerData.getCurrencies()){
			int pBal = d.getBalanceInCurrency(s);
			ttl += pBal;
			int reqBal = getRequirementInContractLevels(s);
			if(pBal < reqBal) return false;
		}
		if(SQRankup2.getBalanceInCombat(d) < getRequirementInContractLevels("reputationOrInfamy")) return false;
		if(SQRankup2.getBalanceInTrade(d) < getRequirementInContractLevels("tradingOrSmuggling")) return false;
		if(ttl < getRequirementInContractLevels("total")) return false;*/
	
	private String printRequirements(){
		ArrayList<String> reqs = new ArrayList<String>();
		for(String s : ContractPlayerData.getCurrencies()){
			int reqBal = getRequirementInContractLevels(s);
			if(reqBal > 0){
				reqs.add(reqBal + " " + s);
			}
		}
		int reqBal;
		reqBal = getRequirementInContractLevels("reputationOrInfamy");
		if(reqBal > 0){
			reqs.add(reqBal + " " + "Reputation (lawful) or Infamy (outlaw)");
		}
		reqBal = getRequirementInContractLevels("tradingOrSmuggling");
		if(reqBal > 0){
			reqs.add(reqBal + " " + "Trading (lawful) or Smuggling (outlaw)");
		}
		reqBal = getRequirementInContractLevels("total");
		if(reqBal > 0){
			reqs.add(reqBal + " " + "total contract levels.");
		}
		return formatStringArray(reqs.toArray(new String[0]));
	}

	public boolean canAffordCosts(Player p) {
		// TODO Auto-generated method stub
		return SQRankup2.eco.has(p, getCost());
	}

	public boolean hasLevels(ContractPlayerData d) {
		return satisfiesCurrencyRequirements(d);
	}

	public void takeCost(Player p) {
		SQRankup2.eco.withdrawPlayer(p, getCost());

	}
}
