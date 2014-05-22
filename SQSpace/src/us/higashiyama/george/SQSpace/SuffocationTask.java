package us.higashiyama.george.SQSpace;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SuffocationTask
  extends BukkitRunnable
{
  Player p;
  SQSpace plugin;
  boolean canceled = false;
  
  SuffocationTask(SQSpace plugin, Player p)
  {
    this.p = p;
    runTaskTimer(plugin, 20L, 20L);
    this.plugin = plugin;
  }
  
  public void run()
  {
    if (!this.plugin.isInSpace(this.p)) {
      cancel(this.p);
    }
    if (this.plugin.hasSpaceHelmet(this.p)) {
      cancel(this.p);
    }
    if (this.p.isDead()) {
      cancel(this.p);
    }
    if (!this.canceled) {
      this.p.damage(1.0D);
    }
  }
  
  public void cancel(Player p)
  {
    cancel();
    SQSpace.Players.remove(p);
    p.sendMessage(ChatColor.AQUA + "[Space] " + ChatColor.GREEN + "You are no longer suffocationg!");
    this.canceled = true;
  }
}
