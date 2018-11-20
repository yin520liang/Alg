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
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @title ClientTest
 * @author lvzhaoyang
 * @date 2018年11月13日
 */
public class ClientTest {
	
	private ByteBuffer send = ByteBuffer.allocate(1024);
	
	private ByteBuffer receive = ByteBuffer.allocate(1024);

	/**
	 * @author lvzhaoyang
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @date 2018年11月13日 
	 */
	public static void main(String[] args) throws Exception {
		ClientTest test = new ClientTest();
		test.run();
	}
	
	private void run() throws IOException, InterruptedException {
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(true);
		sc.socket().setKeepAlive(true);
		
		StringBuilder str = new StringBuilder();
		if(sc.connect(new InetSocketAddress("localhost", 1234))) {
			sendMessage(sc, "hello");
		}
		
	}
	
//	private void receiveMessage(ReadableByteChannel channel, StringBuilder str) throws IOException {
//		receive.clear();
//		while(channel.read(receive) > 0);
//		receive.flip();
//		str.setLength(0);
//		while(receive.hasRemaining())
//			str.append((char) receive.get());
//	}
	
	private void sendMessage(WritableByteChannel channel, String msg) throws IOException {
		send.clear();
		send.order(ByteOrder.BIG_ENDIAN);
		send.put(msg.getBytes());
		send.flip();
		channel.write(send);
	}

}
