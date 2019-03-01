package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
/**
 * Java NIO selector
 * @author  
 *
 */
public class SelectorTest {
	
	public static void main(String[] args) throws IOException {
		// bind server address
		ServerSocketChannel socketChannel = ServerSocketChannel.open();
		// listen on port 8080
		socketChannel.socket().bind(new InetSocketAddress(8080));
		
		Selector selector = Selector.open();

		socketChannel.configureBlocking(false);
		SelectionKey key = socketChannel.register(selector, SelectionKey.OP_ACCEPT);

	}

}
