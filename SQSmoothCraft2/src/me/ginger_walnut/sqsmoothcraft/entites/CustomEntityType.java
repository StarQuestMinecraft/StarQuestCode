package me.ginger_walnut.sqsmoothcraft.entites;

import java.lang.reflect.Method;

import org.bukkit.entity.EntityType;

import net.minecraft.server.v1_10_R1.EntityArmorStand;
import net.minecraft.server.v1_10_R1.EntityLiving;
import net.minecraft.server.v1_10_R1.EntityTypes;

public enum CustomEntityType {

	MAIN_ARMOR_STAND("ArmorStand", 30, EntityType.ARMOR_STAND, EntityArmorStand.class, MainArmorStand.class);
	
	private String name;
	private int id;
	private EntityType entityType;
	private Class<? extends EntityLiving> nmsClass;
	private Class<? extends EntityLiving> customClass;
	
	private CustomEntityType(String name, int id, EntityType entityType, Class<? extends EntityLiving> nmsClass, Class<? extends EntityLiving> customClass) {
	
		this.name = name;
		this.id = id;
		this.entityType = entityType;
		this.nmsClass = nmsClass;
		this.customClass = customClass;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public int getID() {
		
		return id;
		
	}
	
	public EntityType getEntityType() {
		
		return entityType;
		
	}
	
	public Class<? extends EntityLiving> getNMSClass() {
		
		return nmsClass;
		
	}
	
	public Class<? extends EntityLiving> getCustomClass() {
		
		return customClass;
		
	}
	
	public static void registerEntities() {
		
		for (CustomEntityType entity : values()) {
			
			try {
				
				Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
				a.setAccessible(true);
				a.invoke(null, entity.getCustomClass(), entity.getName(), entity.getID());
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
}
