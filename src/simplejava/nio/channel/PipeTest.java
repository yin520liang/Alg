/**
 * 
 */
package simplejava.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * @title PipeTest
 * @author lvzhaoyang
 * @date 2018年11月5日
 */
public class PipeTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Pipe pipe = Pipe.open();
				
		ReadableByteChannel in = pipe.source();
		WritableByteChannel out = Channels.newChannel (System.out);
		ByteBuffer buff = ByteBuffer.allocate(100);
		Thread th = new Worker(pipe.sink(), 3);
		th.start();
		while(in.read(buff) > 0) {
			buff.flip();
			out.write(buff);
			buff.clear();
		}
		
		out.close();
		in.close();
	}

	static class Worker extends Thread {
		private String[] str = { 
				"No good deed goes unpunished", 
				"To be, or what?",
				"No matter where you go, there you are", 
				"Just say \"Yo\"", 
				"My karma ran over my dogma" 
		};

		private Random rand = new Random();
		private WritableByteChannel channel;
		private int reps;

		Worker(WritableByteChannel channel, int reps) {
			this.channel = channel;
			this.reps = reps;
		}

		public void run() {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			try {
				for (int i = 0; i < this.reps; i++) {
					doSomeWork(buffer);
					// channel may not take it all at once
					while (channel.write(buffer) > 0) {
						// empty }
					}					
				}
				this.channel.close();
			} catch (Exception e) {
				// easy way out; this is demo code
				e.printStackTrace();

			}
		}

		private void doSomeWork(ByteBuffer buffer) {
			int product = rand.nextInt(str.length);
			buffer.clear();
			buffer.put(str[product].getBytes());
			buffer.put("\r\n".getBytes());
			buffer.flip();
		}
	}

}
