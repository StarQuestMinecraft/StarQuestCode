package com.minecraftdimensions.bungeesuitechat.objects;


public class ServerData {
    static String serverName;
    static String shortName;
    static int localDistance;
    static boolean connectionMessages;
    static String globalRegex;


    public ServerData( String name, String shortName, int localDistance, boolean connectionMessages, String regex ) {
        this.serverName = name;
        this.shortName = shortName;
        this.localDistance = localDistance;
        this.connectionMessages = connectionMessages;
        this.globalRegex = regex;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getServerShortName() {
        return shortName;
    }

    public static int getLocalDistance() {
        return localDistance;
    }

    public static boolean usingConnectionMessages() {
        return connectionMessages;
    }

    public static String getGlobalRegex() {
        return globalRegex;
    }
}
