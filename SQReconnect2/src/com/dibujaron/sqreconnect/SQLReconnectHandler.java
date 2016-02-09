package com.dibujaron.sqreconnect;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SQLReconnectHandler implements ReconnectHandler {

	ReconnectHandler conventional;
	
	public SQLReconnectHandler(ReconnectHandler conventional){
		this.conventional = conventional;
	}
	
	@Override
	public void close() {
		conventional.close();
	}

	@Override
	public ServerInfo getServer(ProxiedPlayer p) {
		//when getting server, we want to return the main server they were on
		String server = Database.getServer(p, false);
		System.out.println(p.getName() + "'s last server is " + server);
		if(server == null){
			ServerInfo i = conventional.getServer(p);
			System.out.println("Sending to conventional location: " + i);
			return conventional.getServer(p);
		}
		return ProxyServer.getInstance().getServerInfo(server);
	}

	@Override
	public void save() {
		conventional.save();
	}

	@Override
	public void setServer(ProxiedPlayer p) {
		//set the conventional just to make sure.
		conventional.setServer(p);
		//if it's a main server, set main. Else set alt.
		String servername = p.getServer().getInfo().getName();
		if(SQReconnect.isSQServer(servername)){
			System.out.println(servername + " is a main server.");
			Database.updateServer(p, servername, true);
		} else {
			System.out.println(servername + " is not a main server.");
			Database.updateServer(p, servername, false);
		}
	}

}
