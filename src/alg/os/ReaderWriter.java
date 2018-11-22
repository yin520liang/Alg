/**
 * 
 */
package alg.os;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 读者写者问题：多个读者可以同时读，单个的写者和所有其他进程互斥
 * <p>
 * <a href=
 * "https://tadvent.wordpress.com/2008/12/20/%E8%AF%BB%E8%80%85-%E5%86%99%E8%80%85%E9%97%AE%E9%A2%98-%E5%86%99%E8%80%85%E4%BC%98%E5%85%88%E4%B8%8E%E5%85%AC%E5%B9%B3%E7%AB%9E%E4%BA%89/">读者优先、写者优先、公平竞争解答</a>
 * <br>
 * 注意：file对应的互斥量不能用lock，因为第一个申请file锁的读线程并不一定是最后一个退出的线程，因此可能会导致异常;应当用信号量，信号量和锁的区别见java
 * <a href="https://docs.oracle.com/javase/8/docs/api/">Semaphore官方文档</a>
 * 
 * @title ReaderWriter
 */
public class ReaderWriter {

	private static Random rand = new Random(43); // imitate read/write time cost

	private static Lock token = new ReentrantLock(); // 令牌资源 --为实现公平竞争
	private static Semaphore fileState = new Semaphore(1); // 文件资源 -- 非公平
	private static AtomicInteger readerCount = new AtomicInteger(0); // 读者计数器

	public static void main(String[] args) throws InterruptedException {
		int readerNum = 3;
		int writerNum = 2;
		Reader[] readers = new Reader[readerNum];
		Writer[] writers = new Writer[writerNum];
		for (int i = 0; i < readerNum; ++i) {
			readers[i] = new Reader(i);
			readers[i].start();
		}

		for (int i = 0; i < writerNum; ++i) {
			writers[i] = new Writer(i);
			writers[i].start();
		}

		// wait 20 seconds
		TimeUnit.SECONDS.sleep(20);
		// stop all the thread
		for (int i = 0; i < readerNum; ++i) {
			readers[i].tryStop();
			readers[i].join();
		}
		for (int i = 0; i < writerNum; ++i) {
			writers[i].tryStop();
			writers[i].join();
		}
	}

	public static void echo(String msg) {
		System.out.println(msg);
	}

	/**
	 * writer process
	 * 
	 * @title Writer
	 */
	static class Writer extends Thread {
		boolean running;
		int tag;

		Writer(int tag) {
			this.tag = tag;
		}

		public void run() {
			running = true;
			while (running) {
				try {
					beforeWrite();
					write();
					afterWrite();
				} catch (InterruptedException e) {
					echo("Writer-" + tag + " is interrupted, exit");
					running = false;
				}

			}
		}

		private void afterWrite() {
			fileState.release();
		}

		private void beforeWrite() throws InterruptedException {
			try {
				token.lock();
				fileState.acquire();
			} finally {
				token.unlock();
			}
		}

		private void write() throws InterruptedException {
			echo("Writer-" + tag + " write something..");
			TimeUnit.SECONDS.sleep(rand.nextInt(3));
		}

		public void tryStop() {
			running = false;
		}
	}

	/**
	 * reader process
	 * 
	 * @title Reader
	 */
	static class Reader extends Thread {
		boolean running;
		int tag;

		Reader(int tag) {
			this.tag = tag;
		}

		public void run() {
			running = true;
			while (running) {
				try {
					beforeRead();
					read();
					afterRead();
				} catch (InterruptedException e) {
					echo("Reader-" + tag + " is interrupted, exit");
					running = false;
				}
			}
		}

		private void afterRead() {
			if (readerCount.decrementAndGet() == 0)
				fileState.release();
		}

		private void read() throws InterruptedException {
			echo("Reader-" + tag + " read something...");
			TimeUnit.SECONDS.sleep(rand.nextInt(3));
		}

		private void beforeRead() throws InterruptedException {
			try {
				token.lock();
				if (readerCount.getAndIncrement() == 0)
					fileState.acquire();
			} finally {
				token.unlock();
			}
		}

		public void tryStop() {
			running = false;
		}

	}

}
