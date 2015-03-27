package com.minecraftdimensions.bungeesuitechat.tasks;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;


public class PluginMessageTask extends BukkitRunnable {
    private final ByteArrayOutputStream bytes;

    public PluginMessageTask( ByteArrayOutputStream bytes ) {
        this.bytes = bytes;
    }

    public void run() {
    	
    	Bukkit.getOnlinePlayers();
        if ( Bukkit.getOnlinePlayers().size() > 0 ) {
            Bukkit.getOnlinePlayers().iterator().next().sendPluginMessage( BungeeSuiteChat.instance, BungeeSuiteChat.OUTGOING_PLUGIN_CHANNEL, bytes.toByteArray() );
        }
    }

}