package simplejava.concurrent.lock;

public class ReentrantLock {
	private boolean isLocked;
	private Thread ownerThread;
	private int lockCount;
	
	public synchronized void lock() throws InterruptedException {
		while(isLocked && Thread.currentThread() != ownerThread) {
			this.wait();
		}
		isLocked = true;
		ownerThread = Thread.currentThread();
		lockCount = 1;
	}
	
	public synchronized void unlock() {
		if(Thread.currentThread() == ownerThread) {
			-- lockCount;
			if(lockCount == 0) {
				ownerThread = null;
				isLocked = false;
				this.notify();
			}
		} else {
			throw new IllegalStateException("This thread does not own this lock");
		}
	}
}
