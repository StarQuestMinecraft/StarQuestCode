package com.starquestminecraft.greeter;

import net.md_5.bungee.config.Configuration;

public class Settings {
	Configuration config;
	public static String dbuser;
	public static String dbpass;
	public static String dbhost;
	public static String dbdb;

	public Settings(Configuration config) {
		this.config = config;
		dbuser = config.getString("dbuser");
		dbpass = config.getString("dbpass");
		dbhost = config.getString("dbhost");
		dbdb = config.getString("dbdb");
	}

}

/*
 * Location: C:\Users\Drew\Desktop\SQDynamicWhitelist.jar Qualified Name:
 * com.starquestminecraft.dynamicwhitelist.Settings JD-Core Version: 0.6.2
 */