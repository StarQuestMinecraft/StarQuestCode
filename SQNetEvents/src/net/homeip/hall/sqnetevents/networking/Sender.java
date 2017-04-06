package net.homeip.hall.sqnetevents.networking;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.bukkit.Bukkit;

import net.homeip.hall.sqnetevents.SQNetEvents;
import net.homeip.hall.sqnetevents.packet.Packet;

public class Sender implements Closeable {

	private SocketAddress sendAddress;
	
	private SocketChannel clientChannel;
	
	public SocketAddress getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(SocketAddress address) {
		sendAddress = address;
	}
	
	public SocketChannel getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(SocketChannel channel) {
		clientChannel = channel;
	}
	
	public Sender(String remoteAddress) {
		String[] address = remoteAddress.split(":");
		System.out.println("address[1]: " + address[1]);
		setSendAddress(new InetSocketAddress(address[0], Integer.parseInt(address[1])));
		System.out.println("[NetEvents] Creating sender. SendTo address: " + getSendAddress());
		// establishes connection with remote address
		connect();
	}
	// Sends a packet to the sendAddress, called externally
	public void send(Packet packet) {
		if ((getClientChannel().isOpen()) && (getClientChannel().isConnected())) {
			System.out.println("[NetEvents] Attempting to write packet...");
			try {
				System.out.println("[NetEvents] Client local address: " + getClientChannel().getLocalAddress().toString());
				System.out.println("[NetEvents] Client remote address: " + getClientChannel().getRemoteAddress().toString());
				// writes bytebuffer from packet and sends
				// TODO: Add breakdown and EOF implementation
				ByteBuffer byteBuffer = packet.write();
				System.out.println("[NetEvents] Sending packet with length of " + byteBuffer.array().length);
				System.out.println("[NetEvents] Done");
				int bytesWritten = getClientChannel().write(byteBuffer);
				System.out.println("[NetEvents] Bytes written: " + bytesWritten);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("[NetEvents] Sent packet to address " + getSendAddress());
		}
		// Will attempt to reestablish connection after 10000ms delay
		else {
			System.out.println("[NetEvents] Attempting to reestablish connection with address " + getSendAddress());
			connect();
		}
	}

	// attempts to connect
	private void connect() {
		System.out.println("[NetEvents] Attempting to establish connection to address " + getSendAddress());
		// Will try to connect until it can successfully do so
		ConnectThread connectThread = new ConnectThread();
		connectThread.start();
	}

	// Establishes connection
	private class ConnectThread extends Thread {
		public ConnectThread() {
			super("NetEvents-Connect");
		}

		@Override
		public synchronized void run() {
			// Will attempt to connect until a connection is
			// established/reestablished
			if (getClientChannel() == null) {
				System.out.println("channel is null");
			}
			// while not yet connected, attempt to connect every 2 seconds
			while ((getClientChannel() == null) || (!(getClientChannel().isConnected()))) {
				try {
					setClientChannel(SocketChannel.open(getSendAddress()));
					try {
						sleep(2000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					//System.out.println("[NetEvents] Failed to connect. Reattempting...");
					try {
						sleep(10000L);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
			System.out.println("[NetEvents] Successfully established connection to address " + getSendAddress());
		}
	}

	// closes the session
	@Override
	public void close() throws IOException {
		if (getClientChannel() != null) {
			getClientChannel().close();
		}
	}
}