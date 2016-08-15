package com.martinjonsson01.sqbeamtransporter.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.martinjonsson01.sqbeamtransporter.tasks.BeamMoveTask;

public class Beam {

	public Block bottomBlock;
	public Block middleBlock;
	public Block topBlock;
	public BlockFace direction;
	public Material blockType;
	public Byte color;
	public BeamMoveTask myTask;
	public static List<Block> beamBlocks = new ArrayList<Block>();
	public static List<Block> groundBlocks = new ArrayList<Block>();

	public Beam(Block bottomBlock, Block middleBlock, Block topBlock, BlockFace direction, Material blockType, Byte color) {

		this.bottomBlock = bottomBlock;
		this.middleBlock = middleBlock;
		this.topBlock = topBlock;
		this.direction = direction;
		this.blockType = blockType;
		this.color = color;
		myTask = new BeamMoveTask(this);

	}

	public void taskMove() {

		if (this.direction.equals(BlockFace.UP)) {

			Block target = this.topBlock.getRelative(this.direction);

			//If target block is not air, something is in the way, stop
			if (target.getType() != Material.AIR) {
				remove();
				return;
			}

		} else if (this.direction.equals(BlockFace.DOWN)) {

			Block target = this.bottomBlock.getRelative(this.direction);

			//If target block is not air, something is in the way, stop
			if (target.getType() != Material.AIR) {
				remove();
				return;
			}

		}

		move();
	}

	@SuppressWarnings("deprecation")
	public void move() {

		if (this.direction.equals(BlockFace.UP)) {

			Block target = this.topBlock.getRelative(this.direction);
			if (target.getType() == Material.AIR) {
				//Bukkit.getServer().broadcastMessage("Replaced " + target.getType());
				target.setType(this.blockType);
				target.setData(this.color);
				Beam.beamBlocks.add(target);
			}
			if (this.bottomBlock.getType() == this.blockType && this.bottomBlock.getData() == this.color && !Beam.groundBlocks.contains(this.bottomBlock)) {
				//Bukkit.getServer().broadcastMessage("Replaced " + this.bottomBlock.getType());
				this.bottomBlock.setType(Material.AIR);
				this.bottomBlock.setData((byte) 0);
				if (Beam.beamBlocks.contains(this.bottomBlock)) {
					Beam.beamBlocks.remove(this.bottomBlock);
				}
			}

			this.bottomBlock = this.middleBlock;
			this.middleBlock = topBlock;
			this.topBlock = target;

		} else if (this.direction.equals(BlockFace.DOWN)) {

			Block target = this.bottomBlock.getRelative(this.direction);
			if (target.getType() == Material.AIR) {
				//Bukkit.getServer().broadcastMessage("Replaced " + target.getType());
				target.setType(this.blockType);
				target.setData(this.color);
				Beam.beamBlocks.add(target);
			}
			if (this.topBlock.getType() == this.blockType && this.topBlock.getData() == this.color) {
				//Bukkit.getServer().broadcastMessage("Replaced " + this.topBlock.getType());
				this.topBlock.setType(Material.AIR);
				this.topBlock.setData((byte) 0);
				if (Beam.beamBlocks.contains(this.topBlock)) {
					Beam.beamBlocks.remove(this.topBlock);
				}
			}

			this.topBlock = this.middleBlock;
			this.middleBlock = this.bottomBlock;
			this.bottomBlock = target;

		}

	}

	@SuppressWarnings("deprecation")
	public void remove() {
		//Bukkit.getServer().broadcastMessage("Replaced " + this.topBlock.getType());
		this.topBlock.setType(Material.AIR);
		this.topBlock.setData((byte) 0);
		//Bukkit.getServer().broadcastMessage("Replaced " + this.middleBlock.getType());
		this.middleBlock.setType(Material.AIR);
		this.middleBlock.setData((byte) 0);
		//Bukkit.getServer().broadcastMessage("Replaced " + this.bottomBlock.getType());
		this.bottomBlock.setType(Material.AIR);
		this.bottomBlock.setData((byte) 0);
		
		Beam.beamBlocks.clear();
		
		this.myTask.cancel();

	}

}
