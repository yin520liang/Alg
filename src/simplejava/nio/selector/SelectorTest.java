/**
 * 
 */
package simplejava.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Iterator;

/**
 * @title SelectorTest
 */
public class SelectorTest {
	
	private static final int DEF_PORT = 1234;
	
	private ByteBuffer buff = ByteBuffer.allocate(1024); // new buffer
	
	private ThreadPool threadPool = new ThreadPool(10);

	public static void main(String[] args) throws IOException {
		SelectorTest test = new SelectorTest();
		test.run(DEF_PORT);

	}
	
	public void run(int port) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress("localhost", port));
		System.out.println("Listening on port " + port);
		
		Selector selector = Selector.open();
		serverChannel.configureBlocking(false);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		buff.order(ByteOrder.BIG_ENDIAN);	
		
		while(true) {
			int n;
			while( (n = selector.select(1 * 1000)) == 0 ); // wait for 1 seconds 
			if(n > 0) {
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while(it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					
					if(key.isAcceptable()) {
						ServerSocketChannel sc = (ServerSocketChannel) key.channel();
						SocketChannel channel = sc.accept();
						System.out.println("Accept " + channel.getRemoteAddress().toString());
						registerChannel(channel, selector, SelectionKey.OP_READ, null);
					}
					
					if(key.isReadable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						System.out.println("Read from " + channel.getRemoteAddress().toString());
						readFromChannel(key);					
						// imitate perform a service call and return an object as result
						// this action is usually executed in another thread 
//						Object result = performService(channel, content);
						// register for write, with attachment
//						registerChannel(channel, selector, SelectionKey.OP_WRITE, result);
					}
					
//					if(key.isWritable()) {
//						SocketChannel channel = (SocketChannel) key.channel();
//						String msg = (String) key.attachment();
//						writeToChannel(channel, msg);
//						System.out.println("Send response to " + channel.getRemoteAddress().toString() 
//								+ ": " + msg);
//						key.cancel(); // cancel register for this connection
//					}
					
					it.remove(); // remove from selected keys
				}
			}
		}
	}
	
//	private Object performService(SocketChannel ch, String param) {
//		System.out.println("Call service with parameter: " + param);
//		return param;
//	}
	
//	private String readFromChannel(ReadableByteChannel channel) throws IOException {
//		StringBuilder str = new StringBuilder();
//		buff.clear();
//		while(channel.read(buff) > 0);
//		buff.flip();
//		while(buff.hasRemaining()) {
//			str.append((char) buff.get());
//		}	
//		return str.toString();
//	}
	
	private void readFromChannel(SelectionKey key) throws IOException {
		WorkerThread t = threadPool.getWorker();
		if(t != null) {
			t.serviceChannel(key);
		}
	}
	
	
	private void writeToChannel(WritableByteChannel channel, String msg) throws IOException {
		buff.clear();
		buff.put(msg.getBytes());
		buff.flip();
		channel.write(buff);
	}
	
	private void registerChannel(SelectableChannel ch, Selector selector, int ops, Object attach) throws IOException {
		if(ch == null)
			return;
		ch.configureBlocking(false);
		ch.register(selector, ops, attach);
	}

}
