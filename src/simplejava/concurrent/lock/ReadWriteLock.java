package simplejava.concurrent.lock;
/**
 * 读写锁：多个读者线程可以同时读；单个写者线程与其他所有线程互斥 <p>
 * 注意此处unlock调用notifyAll的原因：
 * <li>调用unlock时，可能有读线程和写线程阻塞在当前对象，若唤醒的是单个读线程，因有写线程存在，不会获得锁，此次唤醒不会发生其他动作
 * <li>若有多个读线程被唤醒，可以一次性获得访问权限，不需要一个一个唤醒
 */
public class ReadWriteLock {
	private int readers;
	private int writers;
	private int writerRequests;
	
	
	public synchronized void lockRead() throws InterruptedException {
		while(writers > 0 || writerRequests > 0)
			this.wait();
		++ readers;
	}
	
	public synchronized void unlockRead() {
		-- readers;
		this.notifyAll();
	}
	
	
	public synchronized void lockWrite() throws InterruptedException {
		++ writerRequests;
		while(readers > 0 || writers > 0)
			this.wait();
		-- writerRequests;
		++ writers;
	}
	
	public synchronized void unlockWrite() {
		-- writers;
		this.notifyAll();
	}

}
