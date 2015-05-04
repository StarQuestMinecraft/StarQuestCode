package com.dibujaron.worldrestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldRestore extends BukkitRunnable {

	World to, from;
	boolean doWG, doTowns, doFactions;

	static ArrayList<Block> fragileProcessQueue = new ArrayList<Block>();
	static ArrayList<Block> mainProcessQueue = new ArrayList<Block>();
	ArrayList<Exception> exceptions = new ArrayList<Exception>();
	ArrayList<PreprocessTask> preTasks = new ArrayList<PreprocessTask>();

	int index = 0;
	int preIndex = 0;

	int runs = 0;

	boolean running = false;
	long totalDone = 0;
	long totalSize = 0;
	public static long month = 2592000000L;
	final int MAX_PER_RUN = 10000;

	public static final int[] fragileBlocks = new int[] { 8, 9, 10, 11, 29, 33, /* 34, */50, 52, 55, 59, 63, 64, 65, 68, 69, 70, 71, 72, 75, 76, 77, 93, 94, 96, 131, 132, 143, 147, 148, 149, 150, 151, 171, 323, 324, 330, 331,
			356, 404 };

	public WorldRestore(World to, World from, boolean doWG, boolean doTowns, boolean doFactions) {
		this.to = to;
		this.from = from;
		PluginManager pm = Bukkit.getServer().getPluginManager();

		Arrays.sort(fragileBlocks);

		System.out.println("Beginning Preprocessing.");
		if (doWG) {
			Collection<ProtectedRegion> wgRegions = ((WorldGuardPlugin) pm.getPlugin("WorldGuard")).getRegionManager(from).getRegions().values();
			for (ProtectedRegion r : wgRegions) {
				preTasks.add(new WGPreprocessTask(r));
			}
			System.out.println("added worldguard to preprocess list.");
		}

		if (doTowns) {
			for (Town t : TownyUniverse.getDataSource().getTowns()) {
				preTasks.add(new TownyPreprocessTask(t));
			}
			System.out.println("added towny to preprocess list");
		}

		if (doFactions) {
			Collection<Faction> factions = FactionColl.get().getAll();
			for (Faction f : factions) {
				preTasks.add(new FactionsPreprocessTask(f));
			}
			System.out.println("added factions to preprocess list");
		}

	}

	public static void addToProcessQueues(Block b) {
		if (b.getType() == Material.SKULL) {
			System.out.println("Skipping skull!");
			return;
		}
		/*
		 * if(b.getType() == Material.CHEST){ Chest c = (Chest) b.getState();
		 * BlockFace dir = c.getFacing(); BlockFace right = di }
		 */
		if (Arrays.binarySearch(fragileBlocks, b.getTypeId()) >= 0) {
			fragileProcessQueue.add(b);
		} else {
			mainProcessQueue.add(b);
		}
	}

	@Override
	public void run() {
		if(!running){
			long timestamp = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - timestamp < 5 && preIndex < preTasks.size()){
				try{
					PreprocessTask t = preTasks.get(preIndex);
					t.process(from, to);
				} catch(Exception e){
					e.printStackTrace();
					exceptions.add(e);
				}
				preIndex++;
			}
			if(preIndex >= preTasks.size()){
				for (Block b : fragileProcessQueue) {
					mainProcessQueue.add(b);
				}
				preIndex = 0;
				totalSize = mainProcessQueue.size();
				System.out.println("Total process size: " + totalSize);
				running = true;
				run();
			}
		}else{
			System.out.println("runs: " + runs);
			runs++;
			int processedThisRun = 0;
			while(processedThisRun < MAX_PER_RUN && index < mainProcessQueue.size()){
				try{
				Block b = mainProcessQueue.get(index);
				process(b);
				} catch (Exception e){
					e.printStackTrace();
					exceptions.add(e);
				}
				processedThisRun++;
				index++;
			}
			totalDone += processedThisRun;
			if(runs % 100 == 0) System.out.println("100 runs completed! Total done: " + totalDone + " out of " + totalSize);
			if(index >= mainProcessQueue.size()){
				System.out.println("Finished!");
				for(Exception e : exceptions){
					System.out.println("============================");
					e.printStackTrace();
				}
				/*for(LivingEntity e : from.getEntitiesByClass){
					Location l = e.getLocation();
					e.teleport(new Location(to, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
				}*/
				System.out.println("Attempting to warp entities...");
				warpEntities(from, to);
				System.out.println("Done!");
				this.cancel();
			}
		}
	}

	private void warpEntities(World from, World to){
		for(Entity e : from.getEntities()){
			if(e instanceof LivingEntity){
				if(!(e instanceof Player)){
					LivingEntity le = (LivingEntity) e;
					Location l = le.getLocation();
					le.teleport(new Location(to, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch()));
				}
			}
		}
	}
	private void process(Block b) {
		
		Chunk c = b.getChunk();
		if(!c.isLoaded()){
			c.load();
		}
		Block from = b;
		Block to = this.to.getBlockAt(from.getLocation());
		Chunk c2 = to.getChunk();
		if(!c2.isLoaded()){
			c2.load();
		}
		to.setType(from.getType());
		to.setData(from.getData());

		// if it's a sign update the sign.
		if (from.getTypeId() == 63 || from.getTypeId() == 68) {
			// wrt.write("Found to be a sign");
			// wrt.newLine();
			String[] lines = ((Sign) from.getState()).getLines();
			Sign ns = (Sign) to.getState();
			// wrt.write("    " + lines[0]);
			// wrt.write("    " + lines[1]);
			// wrt.write("    " + lines[2]);
			// wrt.write("    " + lines[3]);
			ns.setLine(0, lines[0]);
			ns.setLine(1, lines[1]);
			ns.setLine(2, lines[2]);
			ns.setLine(3, lines[3]);
			ns.update();
		}
		if (b.getState() != null && b.getState() instanceof InventoryHolder) {
			System.out.println(b.getType());
			/*
			 * if(b.getState() instanceof DoubleChest){
			 * System.out.println("instance of double chest!"); if(to.getState()
			 * instanceof DoubleChest){
			 * System.out.println("Completed target double chest!"); //only copy
			 * if target is already double chest DoubleChest c = (DoubleChest)
			 * b.getState(); DoubleChest t = (DoubleChest) to.getState();
			 * t.getLeftSide
			 * ().getInventory().setContents(c.getLeftSide().getInventory
			 * ().getContents());
			 * t.getRightSide().getInventory().setContents(c.getRightSide
			 * ().getInventory().getContents()); }
			 * 
			 * } else {
			 */
			InventoryHolder h = (InventoryHolder) b.getState();
			Inventory inv = h.getInventory();
			InventoryHolder th = ((InventoryHolder) to.getState());
			if (inv.getType() == th.getInventory().getType() && inv.getSize() == th.getInventory().getSize()) {
				th.getInventory().setContents(inv.getContents());
			}
			/* } */
		}
	}
}