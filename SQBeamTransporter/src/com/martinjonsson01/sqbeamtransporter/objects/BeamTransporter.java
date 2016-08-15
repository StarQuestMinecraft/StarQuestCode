package com.martinjonsson01.sqbeamtransporter.objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.martinjonsson01.sqbeamtransporter.SQBeamTransporter;
import com.martinjonsson01.sqbeamtransporter.detection.Detection;
import com.martinjonsson01.sqbeamtransporter.tasks.BeamTask;


public class BeamTransporter {

	public byte color;
	public double speed;
	public Floor destFloor;
	public Floor startFloor;
	public TreeMap<Integer, Floor> floorMap = new TreeMap<Integer, Floor>();
	public Block groundBlock;
	public boolean goingUp = false;
	public boolean isGoingFromGround = false;
	public List<Sign> sign = new ArrayList<Sign>();
	public Beam beam;
	public ParticleBeam pBeam;
	public OfflinePlayer owner;

	@SuppressWarnings("deprecation")
	public BeamTransporter(Block stainedGlass, OfflinePlayer owner) {


		this.speed = 0.5D;
		scanFloors(stainedGlass);
		this.color = stainedGlass.getData();
		this.owner = owner;
		SQBeamTransporter.beamTransporterList.add(this);

	}

	public void beamToFloor(Floor from, Floor to, Entity entity, Player messageReciever, boolean fromIsGround) {

		if (isEntityOnTransporter(entity) || fromIsGround) {

			if (isEntityOnFloor(to)) {

				messageReciever.sendMessage(ChatColor.RED + "Error - There is an entity on the floor above you, activating transporter would cause that entity to fall and die.");
				return;

			}

			if (from == null) {
				messageReciever.sendMessage(ChatColor.RED + "Error - Could not find the floor 'from'");
				return;
			} else if (to == null) {
				messageReciever.sendMessage(ChatColor.RED + "Error - Could not find the floor 'to'");
				return;
			}
			//Determine if player is going up or down
			if (from.getY() > to.getY()) {
				this.goingUp = false;
			} else if (to.getY() > from.getY()) {
				this.goingUp = true;
			}

			if (this.goingUp) {
				if (!scanShaftUp(entity.getLocation(), to)) {
					entity.sendMessage(ChatColor.RED + "Error - Block is in the way of beam");
					return;
				}
			} else if (!this.goingUp) {
				if (!scanShaftDown(entity.getLocation(), to)) {
					entity.sendMessage(ChatColor.RED + "Error - Block is in the way of beam");
					return;
				}
			}
			//Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "testing: " + this + " FULL LIST: " + SQBeamTransporter.currentlyBeaming);
			if (SQBeamTransporter.currentlyBeaming.contains(this)) {
				
				entity.sendMessage(ChatColor.RED + "Error - This beam transporter is currently being used.");
				return;

			}

			this.startFloor = from;
			this.destFloor = to;
			// DEBUG entity.sendMessage("Start: " + this.startFloor.getY() + " Dest: " + this.destFloor.getY());
			//this.startFloor.getStainedGlass().setType(Material.AIR); SHOULD NOT BE COMMENTED OUT !!!!

			//First remove all stained glass from all of the floors
			this.removeStainedGlass();

			SQBeamTransporter.beamEntities.add(entity);
			SQBeamTransporter.transporterMap.put(entity, this);
			//Create particle beam
			Location startFloorLoc = this.startFloor.getStainedGlass().getLocation();
			Location destFloorLoc = this.destFloor.getStainedGlass().getLocation();
			double height = startFloorLoc.distance(destFloorLoc);
			if (this.goingUp) {
				createParticleBeam(startFloorLoc, height);
			} else if (!this.goingUp) {
				createParticleBeam(destFloorLoc, height);
			}
			//Start beaming player
			(new BeamTask()).run();

		}

	}

	public void beamToGround(Entity entity) {

		if (isEntityOnTransporter(entity)) {

			this.startFloor = getFloorFromStainedGlass(entity.getLocation().getBlock().getRelative(BlockFace.DOWN), this);

			this.destFloor = getGroundFloor(entity.getLocation().getBlock().getRelative(BlockFace.DOWN));

			//Determine if player is going up or down
			if (this.startFloor.getY() > this.destFloor.getY()) {
				this.goingUp = false;
			} else if (this.destFloor.getY() > this.startFloor.getY()) {
				this.goingUp = true;
			}

			if (this.goingUp) {
				if (!scanShaftUp(entity.getLocation(), this.destFloor)) {
					entity.sendMessage(ChatColor.RED + "Error - Block is in the way of beam");
					return;
				}
			} else if (!this.goingUp) {
				if (!scanShaftDown(entity.getLocation().add(0, -1, 0), this.destFloor)) {
					entity.sendMessage(ChatColor.RED + "Error - Block is in the way of beam");
					return;
				}
			}

			this.startFloor.getStainedGlass().setType(Material.AIR);

			SQBeamTransporter.beamEntities.add(entity);
			SQBeamTransporter.transporterMap.put(entity, this);
			//Create particle beam
			Location startFloorLoc = this.startFloor.getStainedGlass().getLocation();
			Location destFloorLoc = this.destFloor.getStainedGlass().getLocation();
			double height = startFloorLoc.distance(destFloorLoc);
			if (this.goingUp) {
				createParticleBeam(startFloorLoc, height);
			} else if (!this.goingUp) {
				createParticleBeam(destFloorLoc, height);
			}
			//Start beaming player
			(new BeamTask()).run();

		}

	}

	public void createBeam(Entity entity, BeamTransporter bt) {

		//Prepare variables for beam

		if (!Beam.groundBlocks.contains(entity.getLocation().getBlock().getRelative(BlockFace.DOWN))) {
			if (BeamTransporter.getBeamTransporterFromStainedGlass(entity.getLocation().getBlock().getRelative(BlockFace.DOWN)) == null) {
				Beam.groundBlocks.add(entity.getLocation().getBlock().getRelative(BlockFace.DOWN));
			}
		}

		Block bottomBlock = entity.getLocation().getBlock();
		if (bt.goingUp) bottomBlock = bottomBlock.getRelative(BlockFace.DOWN);
		Block middleBlock = bottomBlock.getRelative(BlockFace.UP);
		Block topBlock = middleBlock.getRelative(BlockFace.UP);
		BlockFace direction = null;
		if (bt.goingUp) direction = BlockFace.UP;
		if (!bt.goingUp) direction = BlockFace.DOWN;

		//Create new beam and start it
		Beam beam = new Beam(bottomBlock, middleBlock, topBlock, direction, Material.STAINED_GLASS, bt.color);
		this.beam = beam;
		if (!this.goingUp) {
			beam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 2, 2);
		} else if (this.goingUp && this.isGoingFromGround) {
			beam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 2, 2);
		} else if (this.goingUp) {
			beam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 3, 2);
		}

	}

	public void createParticleBeam(Location bottom, double height) {

		ParticleBeam pBeam = new ParticleBeam(bottom, height);
		this.pBeam = pBeam;
		pBeam.myTask.runTaskTimer(SQBeamTransporter.getPluginMain(), 0, 10);

	}

	public void scanFloors(Block airBlock) {

		int floorNumber = 1;

		for (int yscan = 0 + 1; yscan <= 256; ++yscan) {
			if (yscan == 256) {
				break;
			}

			Block checkBlock = airBlock.getWorld().getBlockAt(airBlock.getX(), yscan, airBlock.getZ());
			if (!(checkBlock.getType() == Material.AIR)) {
				if (Detection.detectTransporter(checkBlock)) {
					Floor floor = new Floor(checkBlock);
					floor.setFloor(floorNumber);
					floor.setstainedGlass(checkBlock);
					floor.setWorld(checkBlock.getWorld());
					floor.setY(yscan);
					BlockFace signDirection = Detection.getSignDirectionFromStainedGlass(checkBlock);
					this.sign.add((Sign) checkBlock.getRelative(signDirection).getRelative(signDirection).getState());
					sign.get(floorNumber-1).setLine(3, ChatColor.LIGHT_PURPLE + "Level: " + floorNumber);
					sign.get(floorNumber-1).update();
					if (this.floorMap == null || !this.floorMap.containsValue(floor)) {
						this.floorMap.put(floorNumber, floor);
						floorNumber++;
					}
				}
			}
		}

		Floor bottomFloor = this.floorMap.firstEntry().getValue();
		this.groundBlock = getGroundBlock(bottomFloor.getStainedGlass());

		return;
	}

	public static Block getGroundBlock(Block elevatorGlass) {

		Block b = elevatorGlass.getRelative(BlockFace.DOWN);

		while(b.getType() == Material.AIR) {

			b = b.getRelative(BlockFace.DOWN);

		}

		if (!(b.getType() == Material.AIR)) {
			return b;
		}
		return b;

	}

	public static Floor getGroundFloor(Block elevatorGlass) {

		Block b = getGroundBlock(elevatorGlass).getRelative(BlockFace.UP).getRelative(BlockFace.UP);

		Floor f = new Floor(b);
		f.setFloor(0);
		f.setstainedGlass(b);
		f.setWorld(b.getWorld());
		f.setY(b.getY());

		return f;

	}

	public static Floor getAboveFloor(Location bottom, BeamTransporter bt) {

		for (int yscan = bottom.getBlockY() + 1; yscan <= 256; ++yscan) {
			if (yscan == 256) {
				return null;
			}

			Block checkBlock = bottom.getWorld().getBlockAt(bottom.getBlockX(), yscan, bottom.getBlockZ());
			if (checkBlock.getType() == Material.STAINED_GLASS) {

				return getFloorFromStainedGlass(checkBlock, bt);

			}
		}
		return null;

	}

	public List<Block> getGroundShaft(Block elevatorGlass) {

		List<Block> shaft = new ArrayList<Block>();

		Block b = elevatorGlass.getRelative(BlockFace.DOWN);

		while(b.getType() == Material.AIR) {

			shaft.add(b);
			b = b.getRelative(BlockFace.DOWN);

		}

		return shaft;

	}

	public boolean scanShaftUp(Location playerLoc, Floor destFloor) {
		//Bukkit.getServer().broadcastMessage("Are there blocks between " + playerLoc.getBlockY() + " and " + destFloor.getY());
		for (int yscan = playerLoc.getBlockY() + 1; yscan <= 256; ++yscan) {
			if (yscan == destFloor.getY()) {
				return true;
			}

			Block checkBlock = playerLoc.getWorld().getBlockAt(playerLoc.getBlockX(), yscan, playerLoc.getBlockZ());
			//Bukkit.getServer().broadcastMessage("Currently checking y: " + checkBlock.getY() + " Block type: " + checkBlock.getType());
			if (!(checkBlock.getType() == Material.AIR)) {

				return false;

			}
		}
		return false;
	}

	public boolean scanShaftDown(Location playerLoc, Floor destFloor) {
		//Bukkit.getServer().broadcastMessage("Are there blocks between " + playerLoc.getBlockY() + " and " + destFloor.getY());
		for (int yscan = playerLoc.getBlockY() - 1; yscan <= 256; --yscan) {
			if (yscan == destFloor.getY()) {
				return true;
			}

			Block checkBlock = playerLoc.getWorld().getBlockAt(playerLoc.getBlockX(), yscan, playerLoc.getBlockZ());
			//Bukkit.getServer().broadcastMessage("Currently checking y: " + checkBlock.getY() + " Block type: " + checkBlock.getType());
			if (!(checkBlock.getType() == Material.AIR)) {

				return false;

			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void setStainedGlass() {

		for (Map.Entry<Integer, Floor> entry : this.floorMap.entrySet()) {

			Floor f = entry.getValue();

			f.getStainedGlass().setType(Material.STAINED_GLASS);
			f.getStainedGlass().setData(this.color);

		}

	}

	public void removeStainedGlass() {

		for (Map.Entry<Integer, Floor> entry : this.floorMap.entrySet()) {

			Floor f = entry.getValue();

			f.getStainedGlass().setType(Material.AIR);

		}

	}

	public static boolean isEntityOnTransporter(Entity entity) {

		if (Detection.detectTransporter(entity.getLocation().getBlock().getRelative(BlockFace.DOWN))) {
			return true;
		}
		return false;
	}

	public static boolean isEntityOnFloor(Floor floor) {

		Set<Entity> set = getEntitiesInChunks(floor.getStainedGlass().getLocation(), 0);

		for (Entity e : set) {

			if (isEntityOnTransporter(e)) {

				if (getBeamTransporterFromStainedGlass(e.getLocation().getBlock().getRelative(BlockFace.DOWN)) != null) {

					BeamTransporter bt = getBeamTransporterFromStainedGlass(e.getLocation().getBlock().getRelative(BlockFace.DOWN));

					if (getFloorFromStainedGlass(e.getLocation().getBlock().getRelative(BlockFace.DOWN), bt).equals(floor)) {

						return true;

					}

				}

			}

		}
		return false;

	}

	public static BeamTransporter getBeamTransporterFromStainedGlass(Block stainedGlass) {

		for (BeamTransporter bt : SQBeamTransporter.beamTransporterList) {

			for (Map.Entry<Integer, Floor> entry : bt.floorMap.entrySet()) {

				Floor f = entry.getValue();

				if (f.getStainedGlass().equals(stainedGlass)) {

					return bt;

				}

			}

		}
		return null;

	}

	public static Floor getFloorFromStainedGlass(Block stainedGlass, BeamTransporter bt) {

		if (bt == null) Bukkit.getServer().broadcastMessage("Beamtransporter is null");

		if (bt.floorMap.entrySet() == null) Bukkit.getServer().broadcastMessage("Floormap entryset is null");

		for (Map.Entry<Integer, Floor> entry : bt.floorMap.entrySet()) {

			Floor f = entry.getValue();

			if (f.getStainedGlass().equals(stainedGlass)) {

				return f;

			}

		}
		return null;

	}

	public static BeamTransporter getBeamTransporterFromXZ(Location loc) {

		for (int yscan = 0 + 1; yscan <= 256; ++yscan) {

			if (yscan == 256) {
				return null;
			}

			World w = loc.getWorld();
			int x = loc.getBlockX();
			int z =loc.getBlockZ();
			Block checkBlock = w.getBlockAt(x, yscan, z);

			if (checkBlock.getType() == Material.STAINED_GLASS) {

				if (Detection.detectTransporter(checkBlock)) {

					return getBeamTransporterFromStainedGlass(checkBlock);

				}

			}

		}
		return null;

	}

	public static BeamTransporter createBeamTransporterFromXZ(int x, int z, String worldName, OfflinePlayer p) {

		for (int yscan = 0 + 1; yscan <= 256; ++yscan) {

			if (yscan == 256) {
				return null;
			}
			World w = Bukkit.getWorld(worldName);
			Block checkBlock = w.getBlockAt(x, yscan, z);

			if (checkBlock.getType() == Material.STAINED_GLASS) {

				if (Detection.detectTransporter(checkBlock)) {

					BeamTransporter bt = new BeamTransporter(checkBlock, p);

					return bt;

				}

			}

		}
		return null;

	}

	public static Set<Entity> getEntitiesInChunks(Location l, int chunkRadius) {
		Block b = l.getBlock();
		Set<Entity> entities = new HashSet<Entity>();
		for (int x = -16 * chunkRadius; x <= 16 * chunkRadius; x += 16) {
			for (int z = -16 * chunkRadius; z <= 16 * chunkRadius; z += 16) {
				for (Entity e : b.getRelative(x, 0, z).getChunk().getEntities()) {
					entities.add(e);
				}
			}
		}
		return entities;
	}

}
