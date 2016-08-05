package com.starquestminecraft.sqtechfarm.Object;

import org.bukkit.Material;

public enum Farmables
{
	CROPS(7)
	{
		@Override
		public Material material()
		{
			return Material.CROPS;
		}
		
		@Override
		public boolean plantable()
		{
			return true;
		}
		
		@Override
		public Material seed()
		{
			return Material.SEEDS;
		}
	},
	CARROT(7)
	{
		@Override
		public Material material()
		{
			return Material.CARROT;
		}
		
		@Override
		public boolean plantable()
		{
			return true;
		}
		
		@Override
		public Material seed()
		{
			return Material.CARROT_ITEM;
		}
	},
	POTATOE(7)
	{
		@Override
		public Material material()
		{
			return Material.POTATO;
		}
		
		@Override
		public boolean plantable()
		{
			return true;
		}
		
		@Override
		public Material seed()
		{
			return Material.POTATO_ITEM;
		}
	},
	BEETROOT_BLOCK(3)
	{
		@Override
		public Material material()
		{
			return Material.BEETROOT_BLOCK;
		}
		
		@Override
		public boolean plantable()
		{
			return true;
		}
		
		@Override
		public Material seed()
		{
			return Material.BEETROOT_SEEDS;
		}
	},
	NETHER_WARTS(3)
	{
		@Override
		public Material material()
		{
			return Material.NETHER_WARTS;
		}
		
		@Override
		public boolean plantable()
		{
			return true;
		}
		
		@Override
		public Material seed()
		{
			return Material.NETHER_STALK;
		}
	},
	PUMPKIN(-1)
	{
		@Override
		public Material material()
		{
			return Material.PUMPKIN;
		}
		
		@Override
		public boolean plantable()
		{
			return false;
		}
		
		@Override
		public Material seed()
		{
			return null;
		}
	},
	MELON_BLOCK(-1)
	{
		@Override
		public Material material()
		{
			return Material.MELON_BLOCK;
		}
		
		@Override
		public boolean plantable()
		{
			return false;
		}
		
		@Override
		public Material seed()
		{
			return null;
		}
	};

	private final int maxAge;
	
	private Farmables(int maxAge)
	{
        this.maxAge = maxAge;
    }
	
	public int getmaxAge()
	{
        return maxAge;
    }
	
	public abstract Material material();
	public abstract Material seed();
	public abstract boolean plantable();
}
