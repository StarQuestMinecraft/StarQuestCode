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
	},
	CARROT(7)
	{
		@Override
		public Material material()
		{
			return Material.CARROT;
		}
	},
	POTATOE(7)
	{
		@Override
		public Material material()
		{
			return Material.POTATO;
		}
	},
	BEETROOT_BLOCK(3)
	{
		@Override
		public Material material()
		{
			return Material.BEETROOT_BLOCK;
		}
	},
	NETHER_WARTS(3)
	{
		@Override
		public Material material()
		{
			return Material.NETHER_WARTS;
		}
	},
	PUMPKIN(-1)
	{
		@Override
		public Material material()
		{
			return Material.PUMPKIN;
		}
	},
	MELON_BLOCK(-1)
	{
		@Override
		public Material material()
		{
			return Material.MELON_BLOCK;
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
}
