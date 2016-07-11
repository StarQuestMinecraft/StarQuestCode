package com.martinjonsson01.sqbeamtransporter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.martinjonsson01.sqbeamtransporter.detection.Detection;
import com.martinjonsson01.sqbeamtransporter.objects.Beam;
import com.martinjonsson01.sqbeamtransporter.objects.BeamTransporter;
import com.martinjonsson01.sqbeamtransporter.objects.Floor;


public class Events implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

			if (e.getClickedBlock().getType() == Material.WALL_SIGN) {

				if (e.getClickedBlock().getState() instanceof Sign) {

					Sign s = (Sign) e.getClickedBlock().getState();

					if (!s.getLine(2).equals(ChatColor.GOLD + "Transporter")) return;

					BlockFace signDirection = Detection.getSignDirection(s.getBlock());

					if (signDirection != null) {

						if (!e.getPlayer().isSneaking()) return;

						if (Detection.detectTransporter(s.getBlock().getRelative(signDirection).getRelative(signDirection))) {

							Block stainedGlass = s.getBlock().getRelative(signDirection).getRelative(signDirection);

							BeamTransporter beamTransporter = new BeamTransporter(stainedGlass, e.getPlayer());

							if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
								e.setCancelled(true);
							}

							e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully created a beam transporter!");
							e.getPlayer().sendMessage(ChatColor.AQUA + "Detected "
									+ ChatColor.GOLD + beamTransporter.floorMap.size() + ChatColor.AQUA + " floors.");


						}

					}

				}

			}

		} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if (e.getClickedBlock().getType() == Material.WALL_SIGN) {

				if (e.getClickedBlock().getState() instanceof Sign) {

					Sign s = (Sign) e.getClickedBlock().getState();

					if (s.getLine(0).equalsIgnoreCase("[btransporter]")) {
						
						s.setLine(0, "");
						s.setLine(1, ChatColor.GOLD + "Beam");
						s.setLine(2, ChatColor.GOLD + "Transporter");
						s.update();
						
					} else if (s.getLine(2).equals(ChatColor.GOLD + "Transporter")) {
						
						BlockFace signDirection = Detection.getSignDirection(s.getBlock());

						if (signDirection == null) {
							e.getPlayer().sendMessage(ChatColor.RED + "Error - Improperly built transporter.");
							return;
						}

						Block stainedGlass = s.getBlock().getRelative(signDirection).getRelative(signDirection);

						Entity passenger = e.getPlayer();

						if (Detection.detectTransporter(s.getBlock().getRelative(signDirection).getRelative(signDirection))) {

							BeamTransporter beamTransporter = BeamTransporter.getBeamTransporterFromStainedGlass(stainedGlass);

							for (Entity entity : e.getPlayer().getNearbyEntities(7, 7, 7)) {
								if (BeamTransporter.isEntityOnTransporter(entity)) {
									passenger = entity;
									break;
								}
							}

							if (beamTransporter == null) {
								e.getPlayer().sendMessage(ChatColor.RED + "Error - You have to redetect the transporter before using it.");
							} else {
								beamTransporter.beamToGround(passenger);
							}

						}

					}

				}

			}

		} else if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {

			if (e.getPlayer().getItemInHand().getType() == Material.WATCH) {
				Block stainedGlass = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
				if (BeamTransporter.getBeamTransporterFromStainedGlass(stainedGlass) != null) {

					BeamTransporter bt = BeamTransporter.getBeamTransporterFromStainedGlass(stainedGlass);

					Floor from = BeamTransporter.getFloorFromStainedGlass(stainedGlass, bt);

					Floor to = BeamTransporter.getAboveFloor(e.getPlayer().getLocation(), bt);

					if (to == null) {
						e.getPlayer().sendMessage(ChatColor.RED + "Error - No floor above you.");
						return;
					}

					bt.beamToFloor(from, to, e.getPlayer(), e.getPlayer(), false);

					return;

				} else if (SQBeamTransporter.beamEntities.contains(e.getPlayer())) {
					e.getPlayer().sendMessage(ChatColor.RED + "Error - You are already getting beamed.");
					return;
				}

				for (BeamTransporter bt :SQBeamTransporter.beamTransporterList) {

					int x = bt.floorMap.firstEntry().getValue().getStainedGlass().getLocation().getBlockX();
					int z = bt.floorMap.firstEntry().getValue().getStainedGlass().getLocation().getBlockZ();

					if (e.getPlayer().getLocation().getBlockX() == x && e.getPlayer().getLocation().getBlockZ() == z) {

						Floor to = bt.floorMap.firstEntry().getValue();

						Block elevatorGlass = to.getStainedGlass();

						Floor from = BeamTransporter.getGroundFloor(elevatorGlass);

						bt.isGoingFromGround = true;
						bt.beamToFloor(from, to, e.getPlayer(), e.getPlayer(), true);

					}

				}

			}

		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {

		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;

		if (e.getPlayer().getItemInHand().getType() == Material.WATCH) {
			if (e.getRightClicked().equals(e.getPlayer())) return;
			Block stainedGlass = e.getRightClicked().getLocation().getBlock().getRelative(BlockFace.DOWN);
			if (BeamTransporter.getBeamTransporterFromStainedGlass(stainedGlass) != null) {

				BeamTransporter bt = BeamTransporter.getBeamTransporterFromStainedGlass(stainedGlass);

				Floor from = BeamTransporter.getFloorFromStainedGlass(stainedGlass, bt);

				Floor to = BeamTransporter.getAboveFloor(e.getRightClicked().getLocation(), bt);

				bt.beamToFloor(from, to, e.getRightClicked(), e.getPlayer(), false);
				e.getPlayer().sendMessage(ChatColor.GREEN + "Beaming up the entity: " + e.getRightClicked().getType());

				return;

			}

			for (BeamTransporter bt :SQBeamTransporter.beamTransporterList) {

				int x = bt.floorMap.firstEntry().getValue().getStainedGlass().getLocation().getBlockX();
				int z = bt.floorMap.firstEntry().getValue().getStainedGlass().getLocation().getBlockZ();

				if (e.getRightClicked().getLocation().getBlockX() == x && e.getRightClicked().getLocation().getBlockZ() == z) {

					Floor to = bt.floorMap.firstEntry().getValue();

					Block elevatorGlass = to.getStainedGlass();

					Floor from = BeamTransporter.getGroundFloor(elevatorGlass);

					bt.isGoingFromGround = true;
					bt.beamToFloor(from, to, e.getRightClicked(), e.getPlayer(), true);
					e.getPlayer().sendMessage(ChatColor.GREEN + "Beaming up the entity: " + e.getRightClicked().getType());
					return;
				}

			}

		}

	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (Beam.groundBlocks.contains(e.getBlock())) {
			Beam.groundBlocks.remove(e.getBlock());
			return;
		}
		
	}


}
