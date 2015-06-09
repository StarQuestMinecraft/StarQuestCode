package com.dibujaron.skywatch;

import java.util.HashMap;
import java.util.UUID;

public enum SkywatchStatus {
	NONE,
	EN_ROUTE,
	FIGHTERS,
	ALL,
	WARN_SHORT,
	WARN_LONG,
	OFFLINE;
	
	private static HashMap<UUID, SkywatchStatus> statusMap = new HashMap<UUID, SkywatchStatus>();
	
	public static SkywatchStatus statusOf(UUID u){
		SkywatchStatus s = statusMap.get(u);
		if(s == null) return NONE;
		return s;
	}
	
	public static void setStatus(UUID u, SkywatchStatus s){
		statusMap.put(u, s);
	}
}
