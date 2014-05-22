package us.higashiyama.george.SQSpace;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeReset
  extends BukkitRunnable
{
  SQSpace plugin;
  
  TimeReset(SQSpace plugin)
  {
    runTaskTimer(plugin, 0L, 600L);
  }
  
  public void run()
  {
    if (Bukkit.getServer().getWorld("Defalos") != null) {
      Bukkit.getServer().getWorld("Defalos").setTime(16000L);
    }
    if (Bukkit.getServer().getWorld("AsteroidBelt") != null) {
      Bukkit.getServer().getWorld("AsteroidBelt").setTime(16000L);
    }
    if (Bukkit.getServer().getWorld("Digitalia") != null) {
        Bukkit.getServer().getWorld("Digitalia").setTime(16000L);
      }
    if (Bukkit.getServer().getWorld("Regalis") != null) {
        Bukkit.getServer().getWorld("Regalis").setTime(16000L);
    
      }
  }
}
