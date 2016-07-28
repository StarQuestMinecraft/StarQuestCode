package com.ginger_walnut.sqtutorial.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TutorialProgress implements Serializable {

	private static final long serialVersionUID = -8330981831903848624L;
	
	public HashMap<String, String> progress = new HashMap<String, String>();
	public List<String> completed = new ArrayList<String>();

	public TutorialProgress() {
		
	}
	
}
