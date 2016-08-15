package me.thetimelord.trees;

import com.sk89q.worldedit.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.world.StructureGrowEvent;


public class TreeListener
  implements Listener
{
  public TreeListener(StarQuestTreesAndMobs plugin)
  {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
   /**
 * @param event  
 * Don't think this event should be here, but left it in as I'm just tidying up the code and it runs OK
 */
@EventHandler
  public void onPlayerTalk(PlayerEggThrowEvent event) {}
  
  public int getRandom(int lower, int upper)
  {
    Random random = new Random();
    return random.nextInt(upper - lower + 1) + lower;
  }
  
  public static ArrayList<Planet> planets = new ArrayList<Planet>();
  public static ArrayList<schematic> Schematics = new ArrayList<schematic>();
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onTreeGrow(StructureGrowEvent event)
  {
    for (int i = 0; i < planets.size(); i++)
    {
      if (event.getWorld().getName().equalsIgnoreCase(((Planet)planets.get(i)).name))
      {

        Planet p = (Planet)planets.get(i);
        
        int x = getRandom(0, p.schematics.size() - 1);
        String chosenSchemName = (String)p.schematics.get(x);
        for (int k = 0; k < Schematics.size(); k++)
        {
          if (((schematic)Schematics.get(k)).name.equalsIgnoreCase(chosenSchemName))
          {
            schematic chosenSchem = (schematic)Schematics.get(k);
            
            Vector offset = chosenSchem.schematicOffset;
            Vector dimensions = chosenSchem.schematicDimensions;
            Location location = event.getLocation();
            event.setCancelled(true);
            Vector lowCorner = new Vector(location.getBlockX() - offset.getBlockX(), location.getBlockY() - offset.getBlockY(), location.getBlockZ() - offset.getBlockZ());
            for (int q = 0; q < dimensions.getBlockX(); q++)
            {
              for (int w = 0; w < dimensions.getBlockY(); w++)
              {
                for (int e = 0; e < dimensions.getBlockZ(); e++)
                {
                  location.getWorld().getBlockAt(lowCorner.getBlockX() + q, lowCorner.getBlockY() + w, lowCorner.getBlockZ() + e).setTypeId(chosenSchem.data[q][w][e]);
                  location.getWorld().getBlockAt(lowCorner.getBlockX() + q, lowCorner.getBlockY() + w, lowCorner.getBlockZ() + e).setData((byte)chosenSchem.meta[q][w][e]);
                }
              }
            }
          }
        }
      }
    }
  }
  public static void readConfig()
  {
    for (String name : StarQuestTreesAndMobs.config.getConfigurationSection("planets").getKeys(false))
    {
      Planet p = new Planet();
      p.name = name;
      ArrayList<String> schematics = new ArrayList<String>();
      for (String schematic : StarQuestTreesAndMobs.config.getStringList("planets." + name))
      {
        schematics.add(schematic);
      }
      p.schematics = schematics;
      planets.add(p);
    }
    for (String string : StarQuestTreesAndMobs.config.getConfigurationSection("schematics").getKeys(false))
    {
      schematic s = new schematic();
      s.name = string;
      String schemOffset = StarQuestTreesAndMobs.config.getString("schematics." + string + ".offset");
      String[] schemOffsets = schemOffset.split(",");
      Vector schemRealOffsets = new Vector(Integer.parseInt(schemOffsets[0]), Integer.parseInt(schemOffsets[1]), Integer.parseInt(schemOffsets[2]));
      s.schematicOffset = schemRealOffsets;
      
      String schemDimensions = StarQuestTreesAndMobs.config.getString("schematics." + string + ".dimensions");
      String[] schemDimensionss = schemDimensions.split(",");
      Vector schemRealDimensions = new Vector(Integer.parseInt(schemDimensionss[0]), Integer.parseInt(schemDimensionss[1]), Integer.parseInt(schemDimensionss[2]));
      s.schematicDimensions = schemRealDimensions;
      
      String treeType = StarQuestTreesAndMobs.config.getString("schematics." + string + ".treeType");
      s.treeType = treeType;
      
      int[][][] data = new int[schemRealDimensions.getBlockX()][schemRealDimensions.getBlockY()][schemRealDimensions.getBlockZ()];
      List<String> dataString = StarQuestTreesAndMobs.config.getStringList("schematics." + string + ".data");
      for (int m = 0; m < schemRealDimensions.getBlockX(); m++)
      {
        for (int n = 0; n < schemRealDimensions.getBlockY(); n++)
        {
          String line = (String)dataString.get(m * schemRealDimensions.getBlockX() + (n + 1) - 1);
          String[] lineSplit = line.split(",");
          for (int o = 0; o < schemRealDimensions.getBlockZ(); o++)
          {
            data[m][n][o] = Integer.parseInt(lineSplit[o]);
          }
        }
      }
      
      int[][][] meta = new int[schemRealDimensions.getBlockX()][schemRealDimensions.getBlockY()][schemRealDimensions.getBlockZ()];
      List<String> metaString = StarQuestTreesAndMobs.config.getStringList("schematics." + string + ".meta");
      for (int m = 0; m < schemRealDimensions.getBlockX(); m++)
      {
        for (int n = 0; n < schemRealDimensions.getBlockY(); n++)
        {
          String line = (String)metaString.get(m * schemRealDimensions.getBlockX() + (n + 1) - 1);
          String[] lineSplit = line.split(",");
          for (int o = 0; o < schemRealDimensions.getBlockZ(); o++)
          {
            meta[m][n][o] = Integer.parseInt(lineSplit[o]);
          }
        }
      }
      s.data = data;
      s.meta = meta;
      Schematics.add(s);
    }
  }
}