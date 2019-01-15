/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @title ReadEventHandler
 */
public class ReadEventHandler implements EventHandler {
	
	private ByteBuffer buffer = ByteBuffer.allocate(1024); // 

	@Override
	public void handle(EventDispatcher dispatche, SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		System.out.print("Read from " + channel.getRemoteAddress().toString() 
				+ ":" + drainChannel(channel));
	}


	private boolean drainChannel(SocketChannel channel) throws IOException {
		int count;
		buffer.clear();
		while( (count = channel.read(buffer)) > 0);
		return count < 0;
	}

}
