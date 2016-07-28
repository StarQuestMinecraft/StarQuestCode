package com.ginger_walnut.sqtutorial.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Tutorial {

	public String id;
	
	public List<TutorialStage> stages = new ArrayList<TutorialStage>();
	
	public Tutorial() {
		
	}
	
	public void start(Player player) {
			
		stages.get(0).start(player, this);
		
	}
	
}
