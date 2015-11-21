package nickmiste.dropperTrade;

import org.bukkit.block.Sign;

import net.md_5.bungee.api.ChatColor;

public class SignCooldownTask implements Runnable
{
	private Sign sign;
	
	public SignCooldownTask(Sign sign)
	{
		this.sign = sign;
		sign.setLine(0, ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "Dropper Shop");
		sign.setLine(1, ChatColor.DARK_RED + "" + ChatColor.MAGIC + 
				sign.getLine(1).split(" ")[0].substring(2) + 
				"G" + sign.getLine(2).split("c")[0].substring(2) +
				"GGGGGGGGGGGGGGGGGGGGGGGGGGGG");
		sign.setLine(2, ChatColor.DARK_RED + "Dropper Locked");
		sign.setLine(3, ChatColor.RED + "" + ChatColor.STRIKETHROUGH + sign.getLine(3).substring(2));
		sign.update();
	}
	
	@Override
	public void run()
	{
		sign.setLine(2, ChatColor.DARK_GREEN + "Dropper Unlocked");
		sign.update();
	}
}