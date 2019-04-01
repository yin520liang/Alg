package simplejava.concurrent.lock;

import java.util.LinkedList;
import java.util.List;

public class FairLock {
	private boolean isLocked;	
	private Thread lockThread;
	private List<QueueObject> waitingThreads = new LinkedList<>();
	
	
	public void lock() throws InterruptedException {
		QueueObject obj = new QueueObject();
		synchronized(this) {
			waitingThreads.add(obj);
		}
		
		boolean shouldWait = true;
		while(shouldWait) {
			synchronized(this) {
				shouldWait = isLocked || waitingThreads.get(0) != obj;
				if(! shouldWait) {
					isLocked = true;
					lockThread = Thread.currentThread();
					waitingThreads.remove(0);
					return;
				}
			}

			try {
				obj.doWait();
			} catch (InterruptedException e) {
				synchronized(this) {
					waitingThreads.remove(0);
				}
				throw e;
			}
		}
	}

	public synchronized void unlock() {
		if(Thread.currentThread() != lockThread) 
			throw new IllegalStateException("Current thread does not own this lock");
		isLocked = false;
		lockThread = null;
		if(waitingThreads.size() > 0)
			waitingThreads.remove(0).doNotify();
	}
	
	
	
	
	/**
	 * 该类是一个Semaphore
	 * @author yang
	 *
	 */
	class QueueObject {
		private boolean locked;
		
		public synchronized void doWait() throws InterruptedException {
			while(locked) 
				this.wait();
			locked = true;
		}
		
		public synchronized void doNotify() {
			locked = false;
		}
	}
}
