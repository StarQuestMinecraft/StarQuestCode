package us.higashiyama.george.SQSpace;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
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
    long delay = 20L;
    if (null != p.getInventory().getHelmet())
    {
    	switch (p.getInventory().getHelmet().getEnchantmentLevel(Enchantment.OXYGEN))
    	{
    	case 3: // Should never trigger - qualifies as hasSpaceHelmet
    		delay *= 50;
    	// Intentionally use fall-through to create exponential time growth in delay
    	case 2:
    		delay *= 10;
    	case 1:
    		delay *= 4;
    		
		default: break;
    	}
    }
    runTaskTimer(plugin, delay, delay);
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
