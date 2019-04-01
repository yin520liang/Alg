package simplejava.concurrent.lock;

import java.util.HashMap;
import java.util.Map;

/**
 * 可重入的读写锁 <p>
 * <li> 读重入：
 * <li> 写重入：
 * @author Administrator
 *
 */
public class ReentrantReadWriteLock {
	private Map<Thread, Integer> readerMap = new HashMap<>( );
	private int writers;
	private int writerRequests;
	
	public synchronized void lockRead() throws InterruptedException {
		Thread t = Thread.currentThread();
		while(! accessRead(t))
			this.wait();
		readerMap.put(t, readerMap.get(t) + 1);
	}

	private boolean accessRead(Thread t) {
		if(writers > 0) return false;
		if(readerMap.containsKey(t)) return true;
		if(writerRequests > 0) return false;
		return true;
	}
}
