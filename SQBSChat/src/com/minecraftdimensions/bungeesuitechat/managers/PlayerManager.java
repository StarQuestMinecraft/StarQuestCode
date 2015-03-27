package com.minecraftdimensions.bungeesuitechat.managers;

import com.minecraftdimensions.bungeesuitechat.BungeeSuiteChat;
import com.minecraftdimensions.bungeesuitechat.objects.BSPlayer;
import com.minecraftdimensions.bungeesuitechat.tasks.PluginMessageTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class PlayerManager {

    private static HashMap<String, BSPlayer> onlinePlayers = new HashMap<>();


    public static void addPlayer( BSPlayer player ) {
        onlinePlayers.put( player.getName(), player );
        player.updateDisplayName();
    }

    public static Collection<BSPlayer> getOnlinePlayers() {
        return onlinePlayers.values();
    }

    public static void unloadPlayer( String player ) {
        onlinePlayers.remove( player );
    }

    public static BSPlayer getPlayer( String player ) {
        return onlinePlayers.get( player );
    }

    public static BSPlayer getSimilarPlayer( String player ) {
        for ( String p : onlinePlayers.keySet() ) {
            if ( p.toLowerCase().contains( player.toLowerCase() ) ) {
                return onlinePlayers.get( p );
            }
        }
        return null;
    }

    public static boolean isPlayerOnline( String player ) {
        return onlinePlayers.containsKey( player );
    }

    public static BSPlayer getPlayer( CommandSender sender ) {
        return onlinePlayers.get( sender.getName() );
    }

    public static void setPlayerAFK( Player sender ) {
        BSPlayer p = getPlayer( sender );
        if ( p == null ) {
            return;
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "AFKPlayer" );
            out.writeUTF( p.getName() );
            out.writeBoolean( p.isAFK() );
            out.writeBoolean( sender.hasPermission( "bungeesuite.chat.command.afk.global" ) );
            out.writeBoolean( sender.hasPermission( "bungeesuite.chat.command.afk.display" ) );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
        if ( p.isAFK() ) {
            p.setAFK( false );
        } else {
            p.setAFK( true );
        }
    }

    public static void reloadPlayer( String player ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "GetPlayer" );
            out.writeUTF( player );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static ArrayList<Player> getChatSpies( Player player, Set<Player> set ) {
        ArrayList<Player> spies = new ArrayList<Player>();
        for ( BSPlayer p : onlinePlayers.values() ) {
            if ( p.isChatSpying() && !set.contains( p ) && !p.getName().equals( player.getName() ) ) {
                spies.add( p.getPlayer() );
            }
        }
        return spies;
    }

    public static void setChatSpyPlayer( String name ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "SetChatSpy" );
            out.writeUTF( name );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void ignorePlayer( CommandSender sender, String target ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "IgnorePlayer" );
            out.writeUTF( sender.getName() );
            out.writeUTF( target );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void unIgnorePlayer( CommandSender sender, String target ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "UnIgnorePlayer" );
            out.writeUTF( sender.getName() );
            out.writeUTF( target );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void listPlayersIgnores( CommandSender sender ) {
        ArrayList<String> ignores = getPlayer( sender ).getIgnores();
        if ( ignores.isEmpty() ) {
            sender.sendMessage( ChatColor.DARK_RED + "You are not ignoring anyone" );
        } else {

            String message = ChatColor.BLUE + "You are currently ignoring: " + ChatColor.WHITE;
            for ( String ignore : ignores ) {
                message += ignore + " , ";
            }
            sender.sendMessage( message );
        }
    }

    public static void sendPrivateMessage( String sender, String reciever, String message ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "PrivateMessage" );
            out.writeUTF( sender );
            out.writeUTF( reciever );
            out.writeUTF( message );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );

    }

    public static void muteAll( CommandSender sender ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "MuteAll" );
            out.writeUTF( sender.getName() );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );

    }

    public static void mutePlayer( CommandSender sender, String target, boolean command ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "MutePlayer" );
            out.writeUTF( sender.getName() );
            out.writeUTF( target );
            out.writeBoolean( command );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void nicknamePlayer( String name, String otherplayer, String nickname, boolean command ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "NickNamePlayer" );
            out.writeUTF( name );
            out.writeUTF( otherplayer );
            out.writeUTF( nickname );
            out.writeBoolean( command );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );

    }

    public static void replyToPlayer( CommandSender sender, String message ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "ReplyToPlayer" );
            out.writeUTF( sender.getName() );
            out.writeUTF( message );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void tempMutePlayer( String name, String target, int time ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "TempMutePlayer" );
            out.writeUTF( name );
            out.writeUTF( target );
            out.writeInt( time );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }

    public static void reloadChat( CommandSender sender ) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "ReloadChat" );
            out.writeUTF( sender.getName() );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );

        if ( BungeeSuiteChat.factionChat ) {
            ChannelManager.requestFactionChannels();
        }
        if ( BungeeSuiteChat.towny ) {
            ChannelManager.requestTownyChannels();
        }
    }

    public static void reload() {
        onlinePlayers.clear();

    }

    public static void sendVersion() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream( b );
        try {
            out.writeUTF( "SendVersion" );
            out.writeUTF( ChatColor.RED + "Chat - " + ChatColor.GOLD + BungeeSuiteChat.instance.getDescription().getVersion() );
        } catch ( IOException s ) {
            s.printStackTrace();
        }
        new PluginMessageTask( b ).runTaskAsynchronously( BungeeSuiteChat.instance );
    }
}
