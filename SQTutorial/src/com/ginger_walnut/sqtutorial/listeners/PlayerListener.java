package com.ginger_walnut.sqtutorial.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ginger_walnut.sqtutorial.SQTutorial;
import com.ginger_walnut.sqtutorial.objects.TutorialProgress;
import com.ginger_walnut.sqtutorial.objects.TutorialStage;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQTutorial.getInstance(), new Runnable() {
		
			public void run() {
			
				if (!SQTutorial.progress.containsKey(event.getPlayer().getUniqueId())) {
					
					SQTutorial.tutorials.get(0).start(event.getPlayer());
					
				} else {
					
					TutorialProgress progress = SQTutorial.progress.get(event.getPlayer().getUniqueId());
					
					if (!progress.completed.contains(SQTutorial.tutorials.get(0).id)) {
						
						if (progress.progress.containsKey(SQTutorial.tutorials.get(0).id)) {
							
							for (TutorialStage stage : SQTutorial.tutorials.get(0).stages) {
								
								if (stage.id.equals(progress.progress.get(SQTutorial.tutorials.get(0).id))) {
									
									stage.start(event.getPlayer(), SQTutorial.tutorials.get(0));
									
								}
								
							}
							
						} else {
							
							SQTutorial.tutorials.get(0).start(event.getPlayer());
							
						}
						
					}
					
				}
			
			}
		
		}, 19);

	}
	
}
