package net.homeip.hall.sqnetevents.networking;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import net.homeip.hall.sqnetevents.SQNetEvents;
import net.homeip.hall.sqnetevents.packet.EventPacket;
import net.homeip.hall.sqnetevents.packet.ReceivedDataEvent;

public class Receiver implements Closeable {

	private AcceptConnectionsThread acceptConnectionsThread;

	private SocketAddress bindAddress;

	private ServerSocketChannel server;
	
	private ArrayList<ListenThread> listenThreads;

	// Binds and listens
	public Receiver(String listenAddress) {
		listenThreads = new ArrayList<ListenThread>();
		System.out.println("[NetEvents] Creating receiver. ListenAt address: " + listenAddress);
		String[] address = listenAddress.split(":");
		setBindAddress(new InetSocketAddress(address[0], Integer.parseInt(address[1])));
		// attempts to bind
		bindAndListen();
	}

	private void bindAndListen() {
		// begins attempting to bind to the port
		setAcceptConnectionsThread(new AcceptConnectionsThread());
		getAcceptConnectionsThread().start();
		// when bound, will call listen()
	}

	//registers and starts a new listener given a client socketchannel; called whenever a connection is established
	private void listen(SocketChannel client) {
		ListenThread listenThread = new ListenThread(client);
		listenThread.start();
		addListenThread(listenThread);
	}
	//get and set the ListenAt / bind address
	public SocketAddress getBindAddress() {
		return bindAddress;
	}
	public void setBindAddress(SocketAddress address) {
		bindAddress = address;
	}
	//get and set the bound serversocket
	public ServerSocketChannel getServer() {
		return server;
	}
	public void setServer(ServerSocketChannel serverChannel) {
		server = serverChannel;
	}
	//get and set the thread that binds and continously accepts connections
	public AcceptConnectionsThread getAcceptConnectionsThread() {
		return acceptConnectionsThread;
	}
	public void setAcceptConnectionsThread(AcceptConnectionsThread acceptThread) {
		acceptConnectionsThread = acceptThread;
	}
	//get and add to the list of registered listenthreads through which data is received from clients
	public ArrayList<ListenThread> getListenThreads() {
		return listenThreads;
	}
	public void addListenThread(ListenThread listenThread) {
		listenThreads.add(listenThread);
	}
	
	// Closes all sessions
	@Override
	public void close() throws IOException {
		if (getServer() != null) {
			getServer().close();
		}
		for(ListenThread listenThread : getListenThreads()) {
			SocketChannel client = listenThread.getClient();
			if(client != null) {
				client.close();
			}
		}
	}
	//Binds to proper port and accepts connections until end of program
	private class AcceptConnectionsThread extends Thread {
		public AcceptConnectionsThread() {
			super("NetEvents-AcceptConnections");
		}
		@Override
		public synchronized void run() {
			System.out.println("[NetEvents] Attempting to bind to address " + getBindAddress());
			//opens the socket channel and binds to the proper port
			bind();
			//begins accepting incoming connections
			while(getServer().socket().isBound()) {
				//receives connection
				SocketChannel clientChannel = receiveIncomingConnections();
				//registers and starts a new listenthread for the client
				if(clientChannel != null) {
					listen(clientChannel);
				}
			}
		}
		//opens socket channel and binds
		private void bind() {
			try {
				//opens channel
				setServer(ServerSocketChannel.open());
				getServer().configureBlocking(true);
				// binds to the proper port
				if(getServer().socket().isBound()) {
					getServer().socket().close();
				}
				while (!(getServer().socket().isBound())) {
					getServer().bind(getBindAddress());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("[NetEvents] Bound to address " + getBindAddress());
		}
		//waits for a connect request to be sent, then accepts. returns socketchannel connection
		private SocketChannel receiveIncomingConnections() {
			if ((getServer() != null) && (getServer().isOpen())) {
				try {
					System.out.println("[NetEvents] The server socket is open, waiting for connections...");
					//adds a client
					SocketChannel channel = getServer().accept();
					System.out.println("[NetEvents] Received connection from: " + channel.getRemoteAddress());
					return channel;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	// listens until session closed
	private class ListenThread extends Thread {
		
		private SocketChannel clientChannel;
		
		public ListenThread(SocketChannel channel) {
			super("NetEvents-Listener");
			setClient(channel);
		}
		
		public SocketChannel getClient() {
			return clientChannel;
		}
		public void setClient(SocketChannel channel) {
			clientChannel = channel;
		}
		// waits to receive an event from another port, then processes
		@Override
		public void run() {
			System.out.println("[NetEvents] Listening at address" + getBindAddress());
			try {
				// when everything's working fine, listen for incoming messages
				while ((getServer().isOpen()) && (getClient().isOpen())) {
					// Reads the object and reconstructs from byteinputstream
					// TODO: Remove magic number and add breakdown + EOF
					// detection
					System.out.println("[NetEvents] Client local address: " + getClient().getLocalAddress().toString());
					System.out.println("[NetEvents] Client remote address: " + getClient().getRemoteAddress().toString());
					System.out.println("[NetEvents] Server local address: " + getServer().getLocalAddress());
					byte[] bytes = new byte[4096];
					ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
					System.out.println("[NetEvents] Beginning to read");
					getClient().read(byteBuffer);
					System.out.println("[NetEvents] Reading");
					EventPacket event = EventPacket.read(byteBuffer);
					System.out.println("[NetEvents] Read EventPacket from bytebuffer");
					//if this is the hub, sends again to the correct server
					if(SQNetEvents.getInstance().isHub()) {
						String targetServer = event.getPacketDestination();
						System.out.println("Forwarding packet to: " + targetServer);
						SQNetEvents.getInstance().send(event, targetServer);
					}
					//if this is a spoke, fires
					else {
						// fires the event
						event.handle();
						System.out.println("[NetEvents] Fired event");
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}