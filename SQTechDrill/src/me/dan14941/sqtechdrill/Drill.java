package me.dan14941.sqtechdrill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;

import com.starquestminecraft.sqtechbase.SQTechBase;
import com.starquestminecraft.sqtechbase.gui.GUI;
import com.starquestminecraft.sqtechbase.objects.GUIBlock;
import com.starquestminecraft.sqtechbase.objects.Machine;
import com.starquestminecraft.sqtechbase.objects.MachineType;
import com.starquestminecraft.sqtechbase.util.InventoryUtils;

import me.dan14941.sqtechdrill.gui.DrillGUI;

public class Drill extends MachineType
{	
	
	String name = "Drill";
	BlockFace forward;

	private SQTechDrill main;
	private List<BlockFace> diamond = new ArrayList<BlockFace>();

	final Material[] drillHeadOptions = {Material.DIAMOND_BLOCK, Material.IRON_BLOCK};

	public Drill(int maxEnergy, SQTechDrill main)
	{
		super(maxEnergy);
		this.autodetect = true;
		this.main = main;
		
		diamond.add(BlockFace.NORTH);
		diamond.add(BlockFace.EAST);
		diamond.add(BlockFace.SOUTH);
		diamond.add(BlockFace.WEST);
	}

	@Override
	public boolean detectStructure(GUIBlock guiBlock)
	{
		Material detectedDrillHead = Material.AIR;
		Location lapizLoc = guiBlock.getLocation(); // gets the loc of the lapiz block
		ArrayList<Block> baseBlocks;
		Block chest;
		Block fuelInventory;

		List<Object> drillHeadResult = this.detectDrillHead(lapizLoc);
		for(Object obj : drillHeadResult)
		{
			if(obj instanceof BlockFace)
				forward = (BlockFace)obj;
			else if(obj instanceof Material)
				detectedDrillHead = (Material)obj;
		}

		if(forward == null || detectedDrillHead == Material.AIR)
			return false;

		baseBlocks = this.detectBase(lapizLoc.getBlock(), forward);
		chest = Drill.detectChest(lapizLoc.getBlock());
		fuelInventory = this.detectFuelInventory(lapizLoc.getBlock(), forward);
		
		if(chest == null || baseBlocks == null || baseBlocks.isEmpty() || fuelInventory == null)
			return false;

		return true; // detected drill
	}

	@Override
	public GUI getGUI(Player player, int id)
	{
		return new DrillGUI(main, player, id);
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public void updateEnergy(Machine machine) {
		
		for (Player player : SQTechBase.currentGui.keySet()) {
			
			if (SQTechBase.currentGui.get(player).id == machine.getGUIBlock().id) {
				
				if (player.getOpenInventory() != null) {
					
					if (player.getOpenInventory().getTitle().equals(ChatColor.BLUE + "Drill")) {
						
						player.getOpenInventory().setItem(7, InventoryUtils.createSpecialItem(Material.REDSTONE, (short) 0, ChatColor.GREEN + "Energy: " + machine.getEnergy(), new String[] {ChatColor.GOLD + "Click to refresh", ChatColor.RED + "" + ChatColor.MAGIC + "Contraband"}));
						
					}
					
				}
				
			}
			
		}
		
	}
	
	/**
	 * Detects if a dropper or furnace is placed in the correct position and returns the Block or null if no correct Block was found.
	 * @param base the GuiBlock
	 * @param forward the forward BlockFace of the drill
	 * @return the dropper or furnace Block
	 */
	public Block detectFuelInventory(Block base, BlockFace forward)
	{
		for(BlockFace bf : diamond)
		{
			if(base.getRelative(bf).getState() instanceof Dropper || base.getRelative(bf).getState() instanceof Furnace)
				return base.getRelative(bf);
		}
		
		Block relativePossibleLoc = base.getRelative(BlockFace.DOWN).getRelative(getBlockFaceBack(forward));
		
		if(relativePossibleLoc.getState() instanceof Dropper || relativePossibleLoc.getState() instanceof Furnace)
				return relativePossibleLoc;

		return null;
	}

	/**
	 * Detects if a chest is placed in the correct position and returns the chest Block or null if no chest was found.
	 * @param base the GuiBlock
	 * @return the chest Block
	 */
	public static Block detectChest(final Block base)
	{
		List<BlockFace> diamond = new ArrayList<BlockFace>();
		diamond.add(BlockFace.NORTH);
		diamond.add(BlockFace.EAST);
		diamond.add(BlockFace.SOUTH);
		diamond.add(BlockFace.WEST);

		for(BlockFace bf : diamond)
		{
			if(base.getRelative(bf).getState() instanceof Chest)
				return base.getRelative(bf);
		}
		
		return null;
	}

	/**
	 * Gets a List of possible values indication the drill head Material and direction or an empty List of neither were found.
	 * @param guiBlockLoc the Location of the GUIBlock
	 * @return a List with index 0 being the drill head Material and index 1 being the BlockFace direction of the drill head in proportion to the GUIBlock
	 */
	public List<Object> detectDrillHead(Location guiBlockLoc)
	{
		HashMap<Block, BlockFace> headOptions = this.getDrillHeadOption(guiBlockLoc.getBlock());
		List<Object> result = new ArrayList<Object>();

		for(Block b : headOptions.keySet()) // all blocks in headOptions
			if(b != null && b.getType().isSolid())
				for(Material m : drillHeadOptions)
					if (b.getType() == m)
					{
						result.add(m); // sets drill head material
						result.add(headOptions.get(b)); // sets forward blockface
					}

		// System.out.println(result.toString());

		return result;
	}

	/**
	 * Returns each block next to the arg that could be a drill head.
	 * @param base the Block to search around
	 * @return a HashMap with keys being the block around the base and the value the BlockFace
	 */
	public HashMap<Block, BlockFace> getDrillHeadOption(Block base)
	{
		HashMap<Block, BlockFace> result = new HashMap<Block, BlockFace>();

		result.put(base.getRelative(BlockFace.NORTH), BlockFace.NORTH);
		result.put(base.getRelative(BlockFace.EAST), BlockFace.EAST);
		result.put(base.getRelative(BlockFace.SOUTH), BlockFace.SOUTH);
		result.put(base.getRelative(BlockFace.WEST), BlockFace.WEST);
		result.put(base.getRelative(BlockFace.DOWN), BlockFace.DOWN);

		return result;
	}

	/**
	 * Detects the base block of a drill.
	 * @param base the base Block of the drill
	 * @param forward the BlockFace indicating the forward direction of the drill
	 * @return null if the base cannot be detected or an ArrayList of Blocks containing the block of the base
	 */
	public ArrayList<Block> detectBase(Block base, BlockFace forward)
	{
		ArrayList<Block> baseBlocks = new ArrayList<Block>();

		if(forward != BlockFace.DOWN)
		{
			Block baseMiddle = base.getRelative(BlockFace.DOWN);
			Block baseRight = baseMiddle.getRelative(getBlockFaceRight(forward));
			Block baseLeft = baseMiddle.getRelative(getBlockFaceLeft(forward));

			if(baseMiddle.getType() != Material.WOOD || baseRight.getType() != Material.WOOD
					|| baseLeft.getType() != Material.WOOD) // makes sure all base blocks are
				return null;								// planks

			baseBlocks.add(baseLeft);
			baseBlocks.add(baseMiddle);
			baseBlocks.add(baseRight);

			return baseBlocks;
		}
		else
		{
			HashMap<Block, BlockFace> possibleBaseBlocks = new HashMap<Block, BlockFace>();

			possibleBaseBlocks.put(base.getRelative(BlockFace.NORTH), BlockFace.NORTH);
			possibleBaseBlocks.put(base.getRelative(BlockFace.EAST), BlockFace.EAST);
			possibleBaseBlocks.put(base.getRelative(BlockFace.SOUTH), BlockFace.SOUTH);
			possibleBaseBlocks.put(base.getRelative(BlockFace.WEST), BlockFace.WEST);

			BlockFace back;
			for(Block b : possibleBaseBlocks.keySet())
			{
				back = getBlockFaceBack(possibleBaseBlocks.get(b));
				// Makes sure both ends are wood
				if(b.getType() == Material.WOOD && b.getRelative(back, 2).getType() == Material.WOOD)
				{
					baseBlocks.add(b);
					baseBlocks.add(b.getRelative(back, 2));

					// System.out.println(baseBlocks.toString());

					return baseBlocks;
				}
			}

			return null;
		}
	}

	public static BlockFace getBlockFaceRight(BlockFace forward){
		switch (forward){
		case NORTH:
			return BlockFace.EAST;
		case EAST:
			return BlockFace.SOUTH;
		case SOUTH:
			return BlockFace.WEST;
		case WEST:
			return BlockFace.NORTH;
		default:
			return null;
		}
	}

	public static BlockFace getBlockFaceLeft(BlockFace forward){
		switch (forward){
		case NORTH:
			return BlockFace.WEST;
		case EAST:
			return BlockFace.NORTH;
		case SOUTH:
			return BlockFace.EAST;
		case WEST:
			return BlockFace.SOUTH;
		default:
			return null;
		}
	}

	public static BlockFace getBlockFaceBack(BlockFace forward)
	{
		switch (forward){
		case NORTH:
			return BlockFace.SOUTH;
		case EAST:
			return BlockFace.WEST;
		case SOUTH:
			return BlockFace.NORTH;
		case WEST:
			return BlockFace.EAST;
		case UP:
			return BlockFace.DOWN;
		case DOWN:
			return BlockFace.UP;
		default:
			return null;
		}
	}

	public static BlockFace getDrillForward(Block guiBlock)
	{
		Material detectedDrillHead = Material.AIR;
		BlockFace forward = null;

		List<Object> drillHeadResult = SQTechDrill.getMain().drill.detectDrillHead(guiBlock.getLocation());
		for(Object obj : drillHeadResult)
		{
			if(obj instanceof BlockFace)
				forward = (BlockFace)obj;
			else if(obj instanceof Material)
				detectedDrillHead = (Material)obj;
		}

		if(forward == null || detectedDrillHead == Material.AIR)
			return null;

		return forward;
	}
	
	public int getDrillSpeed(Machine drill)
	{
		List<Object> head = this.detectDrillHead(drill.getGUIBlock().getLocation());
		if(head.get(0) != null && head.get(0) == Material.IRON_BLOCK)
		{
			return main.getNormalDrillSpeed();
		}
		else if(head.get(0) != null && head.get(0) == Material.DIAMOND_BLOCK)
		{
			return main.getFastDrillSpeed();
		}
		else
			return 0;
	}

}
