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
					values.put(key, split[1]);
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
	
	public String[] getPreRequisites(){
		String pre = values.get("prerequisites");
		if(pre == null || pre.equals("none")) return new String[0];
		else return pre.split(", ");
	}
	private String[] getAltPreRequisites() {
		String pre = values.get("altprerequisites");
		if(pre == null || pre.equals("none")) return new String[0];
		else return pre.split(", ");
	}
	
	public int getRequirementInContractCurrency(String currency){
		String val = values.get(currency + "min");
		if(val == null) return 0;
		return Integer.parseInt(val);
	}
	
	public int getAltRequirementInContractCurrency(String currency){
		String val = values.get("alt" + currency + "min");
		if(val == null) return 0;
		return Integer.parseInt(val);
	}
	
	public int getCostInCurrency(String currency){
		try{
			return Integer.parseInt(values.get("cost" + currency));
		} catch (Exception e){
			return 0;
		}
	}
	
	public int getCost(){
		return getCostInCurrency("credits");
	}
	
	public boolean satisfiesCurrencyRequirements(ContractPlayerData d){
		for(String s : ContractPlayerData.getCurrencies()){
			int pBal = d.getBalanceInCurrency(s);
			int reqBal = getRequirementInContractCurrency(s);
			if(pBal < reqBal) return false;
		}
		return true;
	}
	
	public boolean satisfiesAltCurrencyRequirements(ContractPlayerData d){
		for(String s : ContractPlayerData.getCurrencies()){
			int pBal = d.getBalanceInCurrency(s);
			int reqBal = getAltRequirementInContractCurrency(s);
			if(pBal < reqBal) return false;
		}
		return true;
	}
	
	public boolean satisfiesPreReqs(List<String> existing){
		for(String s : existing) System.out.println("existing: " + s);
		for(String s : getPreRequisites()){
			System.out.println("testing prereq: " + s);
			if(!existing.contains(s) && !existing.contains("alt-" + s)){
				System.out.println("Existing does not contain!");
				return false;
			}
		}
		System.out.println("Passed!");
		return true;
	}
	

	public boolean satisfiesAltPreReqs(ArrayList<String> existing) {
		for(String s : getAltPreRequisites()){
			if(!existing.contains(s) && !existing.contains("alt-" + s)) return false;
		}
		return true;
	}
	

	public String getTag(){
		return values.get("tag");
	}
	

	public Object getAltTag() {
		String altTag = values.get("alttag");
		if(altTag == null) return getTag();
		return altTag;
	}
	
	public String getTagColor(){
		String tagColor = values.get("tagcolor");
		if(tagColor == null) return null;
		return "§" + tagColor;
	}
	
	public String getAltTagColor(){
		String altTagColor = values.get("alttagcolor");
		if(altTagColor == null) return getTagColor();
		return "§" + altTagColor;
	}
	
	public String getTagFormatted(){
		return getTagFormatted(ChatColor.WHITE);
	}
	
	public String getTagFormatted(ChatColor previous){
		if(getTag() == null) return null;
		return ChatColor.WHITE + "[" + getTagColor() + getTag() + ChatColor.WHITE + "]" + previous;
	}
	
	public String getAltTagFormatted(){
		return getAltTagFormatted(ChatColor.WHITE);
	}
	
	public String getAltTagFormatted(ChatColor previous){
		if(getAltTag() == null) return null;
		return ChatColor.WHITE + "[" + getAltTagColor() + getAltTag() + ChatColor.WHITE + "]" + previous;
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
		"\nCosts " + getCostInCurrency("credits") + " credits.";
		return retval;
	}

	private String getTagDescriptionFormatted(ChatColor color) {
		String tag = getTagFormatted(color);
		if(tag == null) return "";
		String altTag = getAltTagFormatted(color);
		if(altTag.equals(tag)){
			return "\nUnlocks the tag " + tag;
		} else {
			return "\nUnlocks the tag " + tag + " or " + altTag + " (alternate)";
		}
	}

	private String printConflicts() {
		String[] conflicts = getConflicts();
		if(conflicts.length == 0) return "none";
		return formatStringArray(conflicts);
	}

	private String printPreReqs() {
		String[] preReqs = getPreRequisites();
		String[] altReqs = getAltPreRequisites();
		if(altReqs.length == 0){
			if(preReqs.length == 0){
				return "none";
			} else {
				return formatStringArray(preReqs);
			}
		}
		else {
			return formatStringArray(preReqs) + " or  " + formatStringArray(altReqs) + " (alternate)";
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

	private String printRequirements() {
		Set<String> currencies = ContractPlayerData.getCurrencies();
		ArrayList<String> main = new ArrayList<String>();
		ArrayList<String> alts = new ArrayList<String>();

		for(String s : currencies){
			int reqBal = getRequirementInContractCurrency(s);
			if(reqBal > 0){
				main.add(reqBal + " " + s);
			}
			int altBal = getAltRequirementInContractCurrency(s);
			if(altBal > 0){
				alts.add(altBal + " " + s);
			}
		}
		if(alts.size() == 0){
			return formatStringArray(main.toArray(new String[0]));
		} else {
			return formatStringArray(main.toArray(new String[0])) + " or " + formatStringArray(alts.toArray(new String[0])) + " (alternate)";
		}
	}

	public boolean canAffordCosts(Player p) {
		// TODO Auto-generated method stub
		return SQRankup2.eco.has(p, getCost());
	}

	public boolean hasLevels(Player p) {
		return satisfiesCurrencyRequirements(SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId()));
	}
	
	public boolean hasAltLevels(Player p){
		return satisfiesAltCurrencyRequirements(SQContracts.get().getContractDatabase().getDataOfPlayer(p.getUniqueId()));
	}

	public void takeCost(Player p) {
		SQRankup2.eco.withdrawPlayer(p, getCost());

	}

}
