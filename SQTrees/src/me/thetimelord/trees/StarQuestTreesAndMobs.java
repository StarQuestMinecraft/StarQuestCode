package me.thetimelord.trees;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class StarQuestTreesAndMobs extends org.bukkit.plugin.java.JavaPlugin
{
  public static FileConfiguration config;
  
  public void onEnable()
  {
    new TreeListener(this);
    
    getConfiguration();
    saveConfig();
    TreeListener.readConfig();
  }
  
  public void getConfiguration()
  {
    config = getConfig();
  }
  
  @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!sender.hasPermission("starquest.trees"))
    {
      sender.sendMessage("You don't have permissions to do this");
    }
    
    if (sender.hasPermission("starquest.trees"))
    {
      if (cmd.getName().equalsIgnoreCase("readtreesconfig"))
      {
        TreeListener.readConfig();
      }
      if (cmd.getName().equalsIgnoreCase("preptree"))
      {
        if (args.length == 4)
        {
          List<String> dataString = new ArrayList<String>();
          for (int q = 0; q < Integer.parseInt(args[0]); q++)
          {
            for (int w = 0; w < Integer.parseInt(args[1]); w++)
            {
              String line = "";
              for (int e = 0; e < Integer.parseInt(args[2]); e++)
              {
                if (e > 0)
                {
                  line = line + ",";
                }
                line = line + "0";
              }
              
              dataString.add(line);
            }
          }
          getConfig().set("schematics." + args[3] + ".data", dataString);
          getConfig().set("schematics." + args[3] + ".meta", dataString);
          saveConfig();
          
          return true;
        }
        return false;
      }
      if (cmd.getName().equalsIgnoreCase("readtree"))
      {
        if (args.length == 5)
        {
          int treeX = Integer.parseInt(args[0]);
          int treeY = Integer.parseInt(args[1]);
          int treeZ = Integer.parseInt(args[2]);
          String schematicName = args[3];
          String treeWorld = args[4];
          World world = sender.getServer().getWorld(treeWorld);
          //Location treeCenter = new Location(world, treeX, treeY, treeZ);
          
          for (int i = 0; i < TreeListener.Schematics.size(); i++)
          {
            if (((schematic)TreeListener.Schematics.get(i)).name.equalsIgnoreCase(schematicName))
            {

              schematic s = (schematic)TreeListener.Schematics.get(i);
              Location treeLowCorner = new Location(world, treeX - s.schematicOffset.getBlockX(), treeY - s.schematicOffset.getBlockY(), treeZ - s.schematicOffset.getBlockZ());
              List<String> dataString = new ArrayList<String>();
              


              for (int q = 0; q < s.schematicDimensions.getBlockX(); q++)
              {
                for (int w = 0; w < s.schematicDimensions.getBlockY(); w++)
                {
                  String line = "";
                  for (int e = 0; e < s.schematicDimensions.getBlockZ(); e++)
                  {
                    if (e > 0)
                    {
                      line = line + ",";
                    }
                    line = line + Integer.toString(world.getBlockAt(treeLowCorner.getBlockX() + q, treeLowCorner.getBlockY() + w, treeLowCorner.getBlockZ() + e).getTypeId());
                  }
                  
                  dataString.add(line);
                }
              }
              
              for (int u = 0; u < dataString.size(); u++) {}
              



              getConfig().set("schematics." + s.name + ".data", dataString);
              
              List<String> metaString = new ArrayList<String>();
              for (int q = 0; q < s.schematicDimensions.getBlockX(); q++)
              {
                for (int w = 0; w < s.schematicDimensions.getBlockY(); w++)
                {
                  String line = "";
                  for (int e = 0; e < s.schematicDimensions.getBlockZ(); e++)
                  {
                    if (e > 0)
                    {
                      line = line + ",";
                    }
                    line = line + Integer.toString(world.getBlockAt(treeLowCorner.getBlockX() + q, treeLowCorner.getBlockY() + w, treeLowCorner.getBlockZ() + e).getData());
                  }
                  
                  metaString.add(line);
                }
              }
              

              getConfig().set("schematics." + s.name + ".meta", metaString);
            }
          }
          
          saveConfig();
          
          return true;
        }
      }
    }
    

    return false;
  }
}