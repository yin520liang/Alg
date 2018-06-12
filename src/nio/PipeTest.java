/**
 * 
 */
package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.Random;

/**
 * @Title PipeTest
 * 
 *        Pipe is a intermedia to transfer data from one thread to another.
 */
public class PipeTest {

	private static final int DEF_BUFF_SIZE = 100; // bytes

	/**
	 * @throws IOException
	 * @throws InterruptedException 
	 * @Description
	 * @Author lvzhaoyang
	 * @Date 2018年6月12日
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		PipeTest test = new PipeTest();
		Pipe pipe = Pipe.open();

		Thread consumer = new Thread(test.consumer(pipe));
		Thread producer = new Thread(test.producer(pipe));
		producer.start();
		consumer.start();
		
		Thread.sleep(10 * 1000);
		producer.interrupt();
		consumer.interrupt();
		
	}

	public PipeConsumer consumer(Pipe pipe) {
		return new PipeConsumer(pipe.source());
	}

	public PipeProducer producer(Pipe pipe) {
		return new PipeProducer(pipe.sink());
	}

	class PipeProducer implements Runnable {
		Pipe.SinkChannel sinkChannel;

		ByteBuffer buff;

		Random rand = new Random(43);

		PipeProducer(Pipe.SinkChannel sinkChannel) {
			this.sinkChannel = sinkChannel;
			this.buff = ByteBuffer.allocate(DEF_BUFF_SIZE);
		}

		@Override
		public void run() {
			while (true) {
				buff.clear();
				String msg = String.valueOf(rand.nextInt(100));
				buff.put(msg.getBytes());
				buff.flip();
				try {
					while (buff.hasRemaining()) {
						sinkChannel.write(buff);
						System.out.println(">>> put " + msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					System.out.println("producer is interrupted, stop.");
					break;
				}
			}
		}

	}

	class PipeConsumer implements Runnable {
		Pipe.SourceChannel sourceChannel;

		ByteBuffer buff;

		PipeConsumer(Pipe.SourceChannel sourceChannel) {
			this.sourceChannel = sourceChannel;
			this.buff = ByteBuffer.allocate(DEF_BUFF_SIZE);
		}

		@Override
		public void run() {
			while (true) {
				try {
					buff.clear();
					int bytesRead = sourceChannel.read(buff);
					if (bytesRead > 0) {						
						buff.flip();
						System.out.println("<<< get " + new String(buff.array()));
					} else {
						System.out.println("empty, wait");
						Thread.yield();
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					System.out.println("consumer is interrupted, stop.");
					break;
				}
			}

		}

	}

}
