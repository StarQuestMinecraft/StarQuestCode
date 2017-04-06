package net.homeip.hall.sqnetevents.networking;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import net.homeip.hall.sqnetevents.packet.EmptyPacket;
import net.homeip.hall.sqnetevents.packet.IOPacket;
import net.homeip.hall.sqnetevents.packet.IOPacketWrapper;
import net.homeip.hall.sqnetevents.packet.Packet;

public class Connection {

	private SocketChannel channel;
	
	private SocketAddress remoteAddress;
	
	private String remoteName;
	
	private String localName;
	
	private long timeOfLastPacketReceive;
	
	private boolean isPolling;
	
	private Thread listenThread;
	
	private Thread connectThread;
	
	private Thread pollThread;
	
	
	public Connection(SocketAddress remoteAddress, String remoteName, String localName) throws IOException {
		System.out.println("[SQNetEvents] Creating new connection");
		setRemoteAddress(remoteAddress);
		setConnectThread(new ConnectThread());
		setRemoteName(remoteName);
		setLocalName(localName);
		initialize();
	}
	public Connection(SocketChannel channel, String remoteName, String localName) throws IOException {
		System.out.println("[SQNetEvents] Creating new connection");
		setChannel(channel);
		getChannel().configureBlocking(true);
		setRemoteAddress(getChannel().getRemoteAddress());
		setRemoteName(remoteName);
		setLocalName(localName);
		initialize();
	}
	
	public void initialize() throws IOException {
		setTimeOfLastPacketReceive(System.currentTimeMillis());
		setListenThread(new ListenThread());	
		setPollThread(new PollThread());
	}
	
	private class ListenThread extends Thread {
		@Override
		public void run() {
			//while the channel is open, listens for and receives incoming packets
			while(true) {
				while(getChannel() == null || !getChannel().isConnected()) {
					try {
						sleep(20000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				while(getChannel().isOpen() && getChannel().isConnected()) {
					System.out.println("[SQNetEvents] Beginning to read...");
					byte[] bytes = new byte[4096];
					ByteBuffer buffer = ByteBuffer.wrap(bytes);
					try {
						int length = getChannel().read(buffer);
						System.out.println("localname: " + getLocalName() + "^");
						if(getLocalName().equals("Hub")) {
							System.out.println("In if block");
							IOPacketWrapper wrapper = IOPacketWrapper.read(buffer);
							System.out.println("Wrapper destination: " + wrapper.getPacketDestination());
							ConnectionHolder.getInstance().send(wrapper, wrapper.getPacketDestination());
						}
						else {
							Packet packet = IOPacketWrapper.read(buffer).unwrap();
							setTimeOfLastPacketReceive(System.currentTimeMillis());
							System.out.println("Packetnll: " + packet == null);
							System.out.println("getLocalName: " + getLocalName());
							packet.handle();
						}
						System.out.println("[SQNetEvents] Read packet of length " + length + " from " + getRemoteName());
					} catch (IOException e) {
						try {
							sleep(20000L);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
				if(!getChannel().isConnected()) {
					try {
						sleep(20000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private class PollThread extends Thread {
		@Override
		public void run() {
			while(getChannel() == null || !getChannel().isConnected()) {
				try {
					sleep(20000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(true) {
				if(getChannel() != null && getChannel().isConnected()) {
					try {
						poll();
					} catch (IOException e) {
						try {
							sleep(20000L);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					try {
						sleep(20000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else try {
					sleep(60000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class ConnectThread extends Thread {
		@Override
		public void run() {
			while(true) {
				while(getChannel() == null || !getChannel().isConnected() || getTimeSinceLastPacketReceive() > 60000L) {
					System.out.println("[SQNetEvents] Attempting to connect to " + getRemoteAddress().toString() + "...");
					try {
						close();
						setChannel(SocketChannel.open(getRemoteAddress()));
					} catch (IOException e) {
						try {
							sleep(20000L);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					if(getChannel() != null && getChannel().isConnected()) {
						System.out.println("[SQNetEvents] Successfully established connection to " + getRemoteName());
					}
					try {
						sleep(20000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					getChannel().socket().setSendBufferSize(1024*1024);
					getChannel().socket().setReceiveBufferSize(1024*1024);
				} catch (SocketException e) {
					e.printStackTrace();
				}
				try {
					sleep(20000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//sends a packet to the associated address
	public void sendPacket(Packet packet) throws IOException {
		if(getChannel() != null && getChannel().isOpen() && getChannel().isConnected()) {
			if(packet instanceof IOPacket) {
				sendPacket(IOPacketWrapper.wrap((IOPacket) packet));
			}
		}
		else {
			System.out.println("[SQNetEvents] Could not send packet to " + getRemoteName() + " due to connection issues still being resolved");
		}
	}
	
	public void sendPacket(IOPacketWrapper packet) throws IOException {
		if(getChannel() != null && getChannel().isOpen() && getChannel().isConnected()) {
			int length = getChannel().write(packet.write());
			System.out.println("[SQNetEvents] Sent packet of length " + length + " to " + getRemoteName());
		}
	}
	
	//sends a handshake
	public void poll() throws IOException {
		setPolling(true);
		sendPacket(new EmptyPacket(getRemoteName(), getLocalName()));
		System.out.println("[SQNetEvents] Polling...");
	}
	
	public void close() throws IOException {
		System.out.println("[SQNetEvents] Closing idle SocketChannel");
		if(getChannel() != null && getChannel().isOpen()) {
			getChannel().close();
		}
	}
	
	public Thread getListenThread() {
		return listenThread;
	}
	private void setListenThread(Thread listenThread) {
		this.listenThread = listenThread;
		listenThread.start();
	}

	public Thread getConnectThread() {
		return connectThread;
	}
	private void setConnectThread(Thread connectThread) {
		this.connectThread = connectThread;
		connectThread.start();
	}
	
	public Thread getPollThread() {
		return pollThread;
	}
	private void setPollThread(Thread pollThread) {
		this.pollThread = pollThread;
		pollThread.start();
	}
	public SocketChannel getChannel() {
		return channel;
	}
	public void setChannel(SocketChannel channel) {
		this.channel = channel;
	}
	
	public long getTimeOfLastPacketReceive() {
		return timeOfLastPacketReceive;
	}
	private void setTimeOfLastPacketReceive(long timeOfLastPacketReceive) {
		this.timeOfLastPacketReceive = timeOfLastPacketReceive;
	}
	public long getTimeSinceLastPacketReceive() {
		return System.currentTimeMillis() - getTimeOfLastPacketReceive();
	}
	
	public boolean isPolling() {
		return isPolling;
	}
	private void setPolling(boolean isPolling) {
		this.isPolling = isPolling;
	}
	
	public String getRemoteName() {
		return remoteName;
	}
	private void setRemoteName(String remoteName) {
		this.remoteName = remoteName;
	}
	
	public String getLocalName() {
		return localName;
	}
	private void setLocalName(String localName) {
		this.localName = localName;
	}
	
	public SocketAddress getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(SocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	
	public SocketAddress getLocalAddress() throws IOException {
		return getChannel().getLocalAddress();
	}

}
