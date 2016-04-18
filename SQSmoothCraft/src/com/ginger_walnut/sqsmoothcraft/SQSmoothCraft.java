package com.ginger_walnut.sqsmoothcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.dibujaron.cardboardbox.Knapsack;
import com.ginger_walnut.sqsmoothcraft.gui.Gui;
import com.ginger_walnut.sqsmoothcraft.gui.MainGui;
import com.ginger_walnut.sqsmoothcraft.ship.Ship;
import com.ginger_walnut.sqsmoothcraft.ship.ShipBlock;
import com.ginger_walnut.sqsmoothcraft.ship.ShipEvents;
import com.ginger_walnut.sqsmoothcraft.ship.ShipMovement;
import com.ginger_walnut.sqsmoothcraft.ship.ShipTasks;
import com.ginger_walnut.sqsmoothcraft.ship.ShipUtils;
import com.martinjonsson01.sqsmoothcraft.missile.Missile;
import com.martinjonsson01.sqsmoothcraft.missile.MissileListener;

public class SQSmoothCraft extends JavaPlugin{

	private static Plugin plugin;
	
	public static HashMap<UUID, Ship> shipMap = new HashMap<UUID, Ship>();
	public static ArrayList<Ship> stoppedShipMap = new ArrayList<Ship>();
	public static List<ItemStack> controlItems = new ArrayList<ItemStack>();
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public static HashMap<UUID, Knapsack> knapsackMap = new HashMap<UUID, Knapsack>();
	
	public static List<Material> shipBlockTypes = new ArrayList<Material>();
	public static List<Double> shipBlockHealths = new ArrayList<Double>();
	public static List<Double> shipBlockWeights = new ArrayList<Double>();
	
	public static FileConfiguration config = null;

	public static double nextShipYawCos = 0;
	public static double nextShipYawSin = 0;
	public static double nextShipPitchCos = 0;
	public static double nextShipPitchSin = 0;
	
	public static Location nextShipLocation = null;
	
	public static List<String> guiNames = new ArrayList<String>();
	
	public static HashMap<Player, Gui> currentGui = new HashMap<Player, Gui>();
	
	@Override
	public void onDisable() {
		
		plugin = null;

		for (Ship ship : shipMap.values()) {
			
			boolean succesful = ship.blockify(false);
			ship.exit(false);
			
			if (!succesful) {
				
				for (ShipBlock shipBlock : ship.blockList) {
					
					shipBlock.getLocation().getWorld().dropItem(shipBlock.getLocation(), shipBlock.getArmorStand().getHelmet());
					
					shipBlock.getArmorStand().remove();
					
				}
				
			}
			
		}
		
		for (Ship ship : stoppedShipMap) {
			
			boolean succesful = ship.blockify(false);
			
			if (!succesful) {
				
				for (ShipBlock shipBlock : ship.blockList) {
					
					shipBlock.getLocation().getWorld().dropItem(shipBlock.getLocation(), shipBlock.getArmorStand().getHelmet());
					
					shipBlock.getArmorStand().remove();
					
				}
				
			}
			
		}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been disabled!");
		
		saveDefaultConfig();
		
	}
	
	@Override
	public void onEnable() {
		
		guiNames.add(ChatColor.BLUE + "SQSmoothCraft - Ship");
		guiNames.add(ChatColor.BLUE + "SQSmoothCraft - Options");
		
		plugin = this;
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " has been enabled!");
	
		this.getServer().getPluginManager().registerEvents(new ShipEvents(), this);
		
		Missile.setupMissileAmmoRecipe();
		
		if (!new File(this.getDataFolder(), "config.yml").exists()) {
			
			saveDefaultConfig();
			saveConfig();
			
		}		
		
		config = getConfig();

		this.getServer().getPluginManager().registerEvents(new MissileListener(), this);
		
		List<String> detectableBlocks = new ArrayList<String>();
		
		detectableBlocks.addAll(config.getConfigurationSection("blocks").getKeys(false));
		
		for (String detectableBlock : detectableBlocks) {
			
			shipBlockTypes.add(Material.getMaterial(config.getString("blocks." + detectableBlock + ".material")));
			shipBlockHealths.add(config.getDouble("blocks." + detectableBlock + ".hp"));
			shipBlockWeights.add(config.getDouble("blocks." + detectableBlock + ".weight"));
			
		}
		
		(new ShipMovement()).run();
		(new ShipTasks()).run();
		
	}
	
	public static Plugin getPluginMain() {
		
		return plugin;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player player = (Player) sender;
		
		if (commandLabel.equals("ship")) {
			
			MainGui gui = new MainGui(player);
			gui.open();

		}
		
//		else if (commandLabel.equals("ship3rdperson")) {
//		
//			
//			if (shipMap.containsKey(player.getUniqueId())) {
//				
//				Ship ship = shipMap.get(player.getUniqueId());
//				
//				if (ship.thirdPersonBlock == null) {
//					
//					List<ShipBlock> shipBlocks = ship.getShipBlocks();
//					
//					ShipBlock shipBlock = new ShipBlock(new ShipLocation(0, 3, -10, ship.getMainBlock()), new ItemStack(Material.AIR), ship.getMainBlock());
//					shipBlock.getArmorStand().setPassenger(player);
//					shipBlock.invincible = true;
//
//					player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000000, 1));
//					
//					shipBlocks.add(shipBlock);
//
//					ship.setShipBlocks(shipBlocks);
//					ship.thirdPersonBlock = shipBlock;
//					
//					MinecraftServer server = ((CraftServer) this.getServer()).getServer();
//					WorldServer world = ((CraftWorld) ship.getCaptain().getLocation().getWorld()).getHandle();
//					
//					ship.thirdPersonPlayer = new EntityPlayer(server, world, new GameProfile(player.getUniqueId(), player.getName() + "2"), new PlayerInteractManager(world));
//					
//					ship.thirdPersonPlayer.inventory.armor[0] = CraftItemStack.asNMSCopy(player.getInventory().getHelmet());
//					ship.thirdPersonPlayer.inventory.armor[1] = CraftItemStack.asNMSCopy(player.getInventory().getChestplate());
//					ship.thirdPersonPlayer.inventory.armor[2] = CraftItemStack.asNMSCopy(player.getInventory().getLeggings());
//					ship.thirdPersonPlayer.inventory.armor[3] = CraftItemStack.asNMSCopy(player.getInventory().getBoots());
//					
//					ship.thirdPersonPlayer.inventory.items[ship.thirdPersonPlayer.inventory.itemInHandIndex] = CraftItemStack.asNMSCopy(player.getInventory().getItemInHand());
//					
//					for (Player onlinePlayer : this.getServer().getOnlinePlayers()) {
//						
////						onlinePlayer.hidePlayer(player);
//						
//						PlayerConnection connection = ((CraftPlayer) onlinePlayer).getHandle().playerConnection;
//						
//						connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ship.thirdPersonPlayer));
//						connection.sendPacket(new PacketPlayOutNamedEntitySpawn(ship.thirdPersonPlayer));
//							
//						connection.sendPacket(new PacketPlayOutAttachEntity(0, ship.thirdPersonPlayer, ((CraftEntity) ship.getMainBlock().getArmorStand()).getHandle()));
//						
//					}
//					
//				} else {
//					
//					for (Player onlinePlayer : this.getServer().getOnlinePlayers()) {
//						
////						onlinePlayer.showPlayer(player);
//						
//						PlayerConnection connection = ((CraftPlayer) onlinePlayer).getHandle().playerConnection;
//						
//						connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, (SQSmoothCraft.shipMap.get(player.getUniqueId()).thirdPersonPlayer)));
//						connection.sendPacket(new PacketPlayOutEntityDestroy((SQSmoothCraft.shipMap.get(player.getUniqueId()).thirdPersonPlayer.getId())));
//						
//					}
//					
//					ship.getMainBlock().getArmorStand().setPassenger((Player) sender);
//					ship.thirdPersonBlock.getArmorStand().remove();
//					ship.thirdPersonBlock = null;
//					
//					player.removePotionEffect(PotionEffectType.INVISIBILITY);
//					
//				}
//
//			}
//			
//		}

		return false;
		
	}
	
//	@Override
//	public void startup() {
//		
//		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ConnectionSide.SERVER_SIDE, ListenerPriority.HIGH, 20, 5) {
//			
//			@Override		
//			public void onPacketSending(PacketEvent event) {
//
//				if (event.getPacketID() != 20 || event.getPacketID() != 5) { //check if it's the spawn packet or the equipment packet
//
//					return;
//
//				}
//
//				final PacketContainer packetContainer = event.getPacket();
//
//				try {
//
//					if (event.getPacketID() == 20) {
//						
//						packetContainer.getIntegers().write(8, 0);
//						
//					}
//					
//					if (event.getPacketID() == 5) {
//						
//						packetContainer.getStrings().write("c", new ItemStack(Material.AIR));
//						
//						packetContainer.getI
//						
//					}
//					
//				//here check if its the Packet20NamedEntitySpawn, if so then read index 8 (thats the item inhand) and set it to 0 or null.
//
//				
//
//				//if its the Packet5EntityEquipment then read index c, this should return an NMS Itemstack, just create a new bukkit itemstack, use some reflection to convert it to an nms-itemstack and set index c to your fresh created itemstack.
//
//				} catch (final FieldAccessException e) {
//
//				e.printStackTrace();
//
//				}
//
//			}
//
//		});
//		
//	}
	
}
