package net.homeip.hall.sqnetevents.packet;

import java.io.Serializable;

import org.bukkit.event.Event;

public interface SerializableEvent extends Serializable {
	public Event getEvent();
}
