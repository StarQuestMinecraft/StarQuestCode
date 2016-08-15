package me.thetimelord.trees;

import org.bukkit.block.Block;

public class BlockSetTask extends org.bukkit.scheduler.BukkitRunnable
{
  Block b;
  int type;
  byte data;
  
  BlockSetTask(Block b, int type, byte data) {
    this.b = b;
    this.type = type;
    this.data = data;
  }
  
  @SuppressWarnings("deprecation")
public void run() {
    this.b.setTypeIdAndData(this.type, this.data, false);
  }
}