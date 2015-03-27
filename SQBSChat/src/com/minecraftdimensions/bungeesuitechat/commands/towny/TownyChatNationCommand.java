package com.minecraftdimensions.bungeesuitechat.commands.towny;

import com.minecraftdimensions.bungeesuitechat.managers.ChannelManager;
import com.minecraftdimensions.bungeesuitechat.managers.PlayerManager;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TownyChatNationCommand implements CommandExecutor {


    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {
        if ( args.length > 0 ) {
            String message = "";
            for ( String data : args ) {
                message += data + " ";
            }
            if ( message.charAt( 0 ) == '/' ) {
                message = " " + message;
            }
            BSPlayer p = PlayerManager.getPlayer( sender );
            String channel = p.getChannelName();
            p.setChannel( "Nation" );
            p.getPlayer().chat( message );
            p.setChannel( channel );
        } else {
            ChannelManager.toggleToPlayersTownyChannel( sender, "Nation" );
        }
        return true;
    }
}
