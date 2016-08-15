package com.ginger_walnut.sqtutorial.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ginger_walnut.sqtutorial.SQTutorial;

public class TutorialStage {

	public String id;
	
	public String message;
	public String voicePath;
	
	public int wait;
	public int delay;
	
	public TutorialStage() {
		
	}
	
	public void start(final Player player, final Tutorial tutorial) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQTutorial.getInstance(), new Runnable() {
			
			public void run() {
				
				player.playSound(player.getLocation(), voicePath, 1.5F, 1F);
				
				player.sendMessage(message);
				
				if (SQTutorial.progress.containsKey(player.getUniqueId())) {
					
					TutorialProgress progress = SQTutorial.progress.get(player.getUniqueId());
					
					if (progress.progress.containsKey(tutorial.id)) {
						
						progress.progress.replace(tutorial.id, id);
						
					} else {
						
						progress.progress.put(tutorial.id, id);
						
					}
					
					SQTutorial.progress.replace(player.getUniqueId(), progress);
					
				} else {
					
					TutorialProgress progress = new TutorialProgress();
					progress.progress.put(tutorial.id, id);
					
					SQTutorial.progress.put(player.getUniqueId(), progress);
					
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(SQTutorial.getInstance(), new Runnable() {
					
					public void run() {
						
						for (int i = 0; i < tutorial.stages.size(); i ++) {
							
							if (tutorial.stages.get(i).id == id) {
								
								if (tutorial.stages.size() != i + 1) {
								
									tutorial.stages.get(i + 1).start(player, tutorial);
									
								} else {
									
									if (SQTutorial.progress.containsKey(player.getUniqueId())) {
										
										TutorialProgress progress = SQTutorial.progress.get(player.getUniqueId());
										
										if (progress.progress.containsKey(tutorial.id)) {
											
											progress.progress.remove(tutorial.id);
											
										}
										
										if (!progress.completed.contains(tutorial.id)) {
											
											progress.completed.add(tutorial.id);
											
										}
										
										SQTutorial.progress.replace(player.getUniqueId(), progress);
										
									} else {
										
										TutorialProgress progress = new TutorialProgress();
										progress.completed.add(tutorial.id);
										
										SQTutorial.progress.put(player.getUniqueId(), progress);
										
									}
									
								}
								
							}

						}
						
					}
				
				}, wait * 20);
	
			}
			
		}, delay * 20);

	}
	
}
