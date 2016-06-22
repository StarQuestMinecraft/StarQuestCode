package net.homeip.hall.sqnetevents.packet;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;


public interface Packet extends Serializable {
    public void handle() throws IOException;
    public ByteBuffer write() throws IOException;
}