package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;

/**
 * Datagram is the package using UDP protocol
 *
 */
public class DatagramChannelTest {
	
	private static final int BUFF_SIZE = 1024;
	
	private static final int PORT = 9999;

	/**
	 * i
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(PORT));
		
		ByteBuffer buff = ByteBuffer.allocate(BUFF_SIZE); // 1k
		// start client thread
		Thread client = new Thread(new Client());
		client.start();
		
		while(true) {
			buff.clear();
			// block mode
			SocketAddress sourceAddr = channel.receive(buff);
			System.out.println(String.format("receive message [%s] from [%s]", 
					new String(buff.array()), sourceAddr.toString()));
		}

	}

	
	private static class Client implements Runnable{
		
		private Random rand = new Random(47);
		
		private ByteBuffer buff; // 1k
		
		private DatagramChannel channel;
		
		public Client() {
			buff = ByteBuffer.allocate(BUFF_SIZE); // 1k
			try {
				channel = DatagramChannel.open();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(true) {
				buff.clear();
				buff.put(String.valueOf(rand.nextInt(100)).getBytes());
				buff.flip();
				try {
					channel.send(buff, new InetSocketAddress("127.0.0.1", PORT));
					try {
						Thread.sleep(2 * 1000);
					} catch (InterruptedException e) {
						channel.close();
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}
		}
		
	}
}
