package net.homeip.hall.sqnetevents.packet;

import java.io.IOException;

public class EmptyPacket extends IOPacket {

	private static final long serialVersionUID = 1L;

	public EmptyPacket(String destination, String origin) {
		super(destination, origin);
	}

	@Override
	public void handle() throws IOException {}

}
