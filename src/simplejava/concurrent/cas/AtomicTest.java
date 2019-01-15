/**
 * 
 */
package simplejava.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Title AtomicTest
 * @Description atomic-* classes test
 */
public class AtomicTest {
	
	private volatile int a = 0;

	public static void main(String[] args) throws InterruptedException {
		/*
		 * AtomicInteger
		 * AtomicLong
		 * AtomicBoolean
		 * AtomicReference
		 * ----
		 * AtomicIntegerArray
		 * AtomicLongArray
		 * AtomicReferenceArray
		 * ----
		 * AtomicIntegerFieldUpdater
		 * AtomicLongFieldUpdater
		 * AtomicReferenceFieldUpdater
		 */
		AtomicIntegerFieldUpdater<AtomicTest> updater = 
				AtomicIntegerFieldUpdater.newUpdater(AtomicTest.class, "a");
		AtomicTest testObj = new AtomicTest();
		
		Thread[] ths = new Thread[10];
		for(int i = 0; i != ths.length; ++i) {
			ths[i] = new Thread(() -> updater.incrementAndGet(testObj));
			ths[i].start();
			ths[i].join();
		}
		
		System.out.println(updater.get(testObj));
		
	}	
	

	/**
	 * 使用CAS的递增计数器
	 *
	 */
	class CASCounter {
		private volatile AtomicInteger a;
		
		public int incrementAndGet() {
			int saw;
			// self-rotate
			do {
				saw = a.get();
			}while(!a.compareAndSet(saw, saw + 1));
			return saw + 1;
		}
	}
}
