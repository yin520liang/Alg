/**
 * 
 */
package simplejava.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @title SocketChannel
 * @author lvzhaoyang
 * @date 2018年11月2日
 */
public class SocketChannelTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		connectTest();
	}

	
	private static void connectTest() throws IOException, InterruptedException {
		InetSocketAddress addr = new InetSocketAddress ("3578.mzhen.cn", 3306);
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		sc.connect(addr);
		while(!sc.finishConnect()) {
			TimeUnit.SECONDS.sleep(1);
		}
		System.out.println("Connection established.");
		System.out.println("Perform service.");
		sc.close();
	}
}
