/**
 * 
 */
package redis.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @title AcceptEventHandler
 * @author lvzhaoyang
 * @date 2018年11月23日
 */
public class AcceptEventHandler implements EventHandler {

	
	@Override
	public void handle(EventDispatcher dispatcher, SelectionKey key) throws IOException {
		ServerSocketChannel sc = (ServerSocketChannel) key.channel();
		SocketChannel channel = sc.accept();
		System.out.println("Accept " + channel.getRemoteAddress().toString());
		dispatcher.registerChannel(channel, SelectionKey.OP_READ);		
	}

}
