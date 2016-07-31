package us.higashiyama.george.SQTurrets.types;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;

import us.higashiyama.george.SQTurrets.Ammo;
import us.higashiyama.george.SQTurrets.SQTurrets;

public class BeamTurret extends Turret {

	public BeamTurret(){
		setName("beamturret");
	}
	
	@Override
	public void shoot(Player p) {
		p.sendMessage("Beam turrets require ____ as ammo");
	}

	@Override
	public void shoot(final Player p, Ammo ammo) {
		new BeamTask(p, ammo).runTaskTimer(SQTurrets.getInstance(), 0, 5);
	}
	
	private class BeamTask extends BukkitRunnable{
		Player p;
		Ammo ammo;
		int counter = 0;
		public BeamTask(Player p, Ammo ammo){
			this.p = p;
			this.ammo = ammo;
		}
		@Override
		public void run(){
			if(!SQTurrets.isInTurret(p)) this.cancel();
			Location loc = p.getEyeLocation();
			Vector vec = loc.getDirection();
			for(int i = 0; i < (int) getVelocity(); i++){
				if(!loc.getBlock().getType().equals(Material.AIR) && i > 0) i = (int) getVelocity();
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), loc, Color.LIME);
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), loc.getX()+0.3, loc.getY(), loc.getZ(), Color.LIME);
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), loc.getX()-0.3, loc.getY(), loc.getZ(), Color.LIME);
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), loc.getX(), loc.getY(), loc.getZ()+0.3, Color.LIME);
				ParticleEffect.REDSTONE.sendColor(Bukkit.getOnlinePlayers(), loc.getX(), loc.getY(), loc.getZ()-0.3, Color.LIME);
				
				for (Entity entity : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
					if (entity instanceof LivingEntity && entity.getEntityId() != p.getEntityId()) {
						i = (int) getVelocity();
						LivingEntity target = (LivingEntity) entity;
						target.setHealth(Math.max(0, target.getHealth() - ammo.getYield()));
						loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_HURT, 1.0F, 1.0F);
						
						if(target instanceof Player){
							Player ptarget = (Player) target;
							ptarget.setSaturation((float) (Math.max(0, ptarget.getSaturation() - ammo.getYield2())));
						}
					}	
				}
				loc.add(vec);
			}
			counter++;
			if(counter >= 20) this.cancel();
		}
	}
}


