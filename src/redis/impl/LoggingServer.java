/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import redis.impl.EventDispatcher.EventType;

/**
 * 基于<a href=
 * "http://www.dre.vanderbilt.edu/~schmidt/PDF/reactor-siemens.pdf">Reactor: An
 * Object Behavioral Pattern for Demultiplexing and Dispatching Handles for
 * Synchronous Events</a> 实现的logger server
 * 
 * @title ReactorTest
 */
public class LoggingServer {
	
	private static final int LISTEN_PORT = 1111;  // 监听端口1111
	
	private EventDispatcher dispatcher;


	public static void main(String[] args) throws IOException {
		LoggingServer server = new LoggingServer();
		server.run();

	}
	
	
	public static void log(String msg) {
		System.out.println(msg);
	}


	private void initServer(int port) throws IOException {		
		ServerSocketChannel serverChannel = ServerSocketChannel.open().bind(new InetSocketAddress("localhost", port));
		log("Listening on port " + port);

		dispatcher = new EventDispatcher(Selector.open());
		dispatcher.registerChannel(serverChannel, SelectionKey.OP_ACCEPT);
		dispatcher.registerHandler(EventType.READ, new ReadEventHandler());
		dispatcher.registerHandler(EventType.ACCEPT, new AcceptEventHandler());
	}


	public void run() throws IOException {
		initServer(LISTEN_PORT);
		while(true) {
			dispatcher.handleEvents();
		}

	}

}
