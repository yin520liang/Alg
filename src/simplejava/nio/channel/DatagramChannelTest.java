/**
 * 
 */
package simplejava.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @title DatagramChannelTest
 * 
 */
public class DatagramChannelTest {

	public static void main(String[] args) throws Exception {
		// client
//		TimeClient client = new TimeClient (); 
//		client.sendRequests( ); 
//		client.getReplies( );
		
		// server
		TimeServer server = new TimeServer (); 
		server.listen( );

	}

	static class TimeClient {
		private static final int DEFAULT_TIME_PORT = 37;
		private static final long DIFF_1900 = 2208988800L;
		protected int port = DEFAULT_TIME_PORT;
		protected List<InetSocketAddress> remoteHosts = new ArrayList<> (); // = Arrays.asList(a); this usage dont support add() method
		protected DatagramChannel channel;

		TimeClient() throws IOException {
			channel = DatagramChannel.open();
			remoteHosts.add(new InetSocketAddress("time.nist.gov", port));
		}

		protected InetSocketAddress receivePacket(DatagramChannel channel, ByteBuffer buffer) throws Exception {
			buffer.clear();
			// Receive an unsigned 32-bit, big-endian value
			return ((InetSocketAddress) channel.receive(buffer));
		}

		protected void sendRequests() throws Exception {
			ByteBuffer buffer = ByteBuffer.allocate(1);
			Iterator it = remoteHosts.iterator();
			while (it.hasNext()) {
				InetSocketAddress sa = (InetSocketAddress) it.next();
				System.out.println("Requesting time from " + sa.getHostName() + ":" + sa.getPort());
				// Make it empty (see RFC868)
				buffer.clear().flip(); // limit = position = 0
				// Fire and forget
				channel.send(buffer, sa);
			}
		}

		public void getReplies() throws Exception {
			// Allocate a buffer to hold a long value
			ByteBuffer longBuffer = ByteBuffer.allocate(8);
			// Assure big-endian (network) byte order
			longBuffer.order(ByteOrder.BIG_ENDIAN);
			// Zero the whole buffer to be sure
			longBuffer.putLong(0, 0);
			// Position to first byte of the low-order 32 bits
			longBuffer.position(4);
			// Slice the buffer; gives view of the low-order 32 bits
			ByteBuffer buffer = longBuffer.slice();
			int expect = remoteHosts.size();
			int replies = 0;
			System.out.println("");
			System.out.println("Waiting for replies...");
			while (true) {
				InetSocketAddress sa;
				sa = receivePacket(channel, buffer);
				buffer.flip();
				replies++;
				printTime(longBuffer.getLong(0), sa);
				if (replies == expect) {
					System.out.println("All packets answered");
					break;
				}
				// Some replies haven't shown up yet
				System.out.println("Received " + replies + " of " + expect + " replies");
			}
		}

		protected void printTime(long remote1900, InetSocketAddress sa) {
			// local time as seconds since Jan 1, 1970
			long local = System.currentTimeMillis() / 1000;
			// remote time as seconds since Jan 1, 1970
			long remote = remote1900 - DIFF_1900;
			Date remoteDate = new Date(remote * 1000);
			Date localDate = new Date(local * 1000);
			long skew = remote - local;
			System.out.println("Reply from " + sa.getHostName() + ":" + sa.getPort());
			System.out.println(" there: " + remoteDate);
			System.out.println(" here: " + localDate);
			System.out.print(" skew: ");
			if (skew == 0) {
				System.out.println("none");
			} else if (skew > 0) {
				System.out.println(skew + " seconds ahead");
			} else {
				System.out.println((-skew) + " seconds behind");
			}
		}
		
	}

	
	
	static class TimeServer {
		private static final int DEFAULT_TIME_PORT = 37;
		private static final long DIFF_1900 = 2208988800L;
		protected DatagramChannel channel;
		
		TimeServer(int port) throws IOException {
			channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress (port));
			System.out.println ("Listening on port " + port + " for time requests");
		}
		
		TimeServer() throws IOException {
			this(DEFAULT_TIME_PORT);
		}
		
		public void listen( ) throws Exception { 
			// Allocate a buffer to hold a long value 
			ByteBuffer longBuffer = ByteBuffer.allocate (8); 
			// Assure big-endian (network) byte order 
			longBuffer.order (ByteOrder.BIG_ENDIAN); 
			// Zero the whole buffer to be sure 
			longBuffer.putLong (0, 0); 
			// Position to first byte of the low-order 32 bits 
			longBuffer.position (4); 
			// Slice the buffer; gives view of the low-order 32 bits 
			ByteBuffer buffer = longBuffer.slice( ); 
			while (true) { 
				buffer.clear( ); 
				SocketAddress sa = this.channel.receive (buffer); 
				if (sa == null) { 
					continue; 
					// defensive programming 
				}
				// Ignore content of received datagram per RFC 868 
				System.out.println ("Time request from " + sa); 
				buffer.clear( ); // sets pos/limit correctly
				// Set 64-bit value; slice buffer sees low 32 bits
				longBuffer.putLong (0, (System.currentTimeMillis( ) / 1000) + DIFF_1900); 
				this.channel.send (buffer, sa);
			}
		}
	}
}
