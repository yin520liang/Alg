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
	private Thread currentWriter;
	private int writerRequests;
	
	public synchronized void lockRead() throws InterruptedException {
		Thread t = Thread.currentThread();
		while(! accessRead(t))
			this.wait();
		readerMap.put(t, getReaderAccess(t) + 1);
	}

	private boolean accessRead(Thread t) {
		if(isWriter()) return true;
		if(currentWriter != null) return false;
		if(isReader()) return true;
		if(writerRequests > 0) return false;
		return true;
	}
	
	
	public synchronized void unlockRead() {
		Thread callThread = Thread.currentThread();
		int accesses = getReaderAccess(callThread);
		if(accesses == 1) {
			readerMap.remove(callThread);
		} else {
			readerMap.put(callThread, accesses - 1);
		}
		this.notify();
	}
	
	
	
	public synchronized void lockWrite() throws InterruptedException {
		++ writerRequests;
		Thread t = Thread.currentThread();
		while(! accessWrite(t))
			this.wait();
		currentWriter = t;
		-- writerRequests;
	}

	private boolean accessWrite(Thread t) {
		if(isOnlyReader()) return true;
		if(isWriter()) return true;
		if(currentWriter != null || readerMap.size() > 0) return false;
		return true;
	}
	
	
	public synchronized void unlockWrite() {
		Thread callThread = Thread.currentThread();
		if(callThread != currentWriter) {
			throw new IllegalStateException();
		}
		currentWriter = null;
		notifyAll();
	}
	

	private int getReaderAccess(Thread t) {
		Integer count = readerMap.get(t);
		return (count == null) ? 0 : count;
	}

	
	private boolean isWriter() {
		return Thread.currentThread() == currentWriter;
	}

	private boolean isOnlyReader() {
		return readerMap.size() == 1 && readerMap.containsKey(Thread.currentThread());
	}
	
	private boolean isReader() {
		return readerMap.containsKey(Thread.currentThread());
	}
}
