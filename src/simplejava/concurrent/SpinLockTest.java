package simplejava.concurrent;

import java.util.concurrent.TimeUnit;

public class SpinLockTest {

	public static void main(String[] args) throws InterruptedException {
		NotifyObj obj = new NotifyObj();
		
		Thread[] th = new Thread[4];
		for(int i = 0; i < th.length; ++ i) {
			th[i] = new Thread(() -> {
				obj.doWait();
				System.out.println("I am " + Thread.currentThread().getName());
			}, "th-" + i);
			th[i].start();
		}
	
		TimeUnit.SECONDS.sleep(3);
		obj.doNotify();
	}
	
	
	
	/**
	 * notification object
	 * @author yang
	 *
	 */
	static class NotifyObj {
		
		boolean signaled = false;
		
		public synchronized void doWait() {
//			while(!signaled) {
			if(!signaled) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
			signaled = false;
		}
		
		public synchronized void doNotify() {
			signaled = true;
			this.notifyAll();
		}
	}
	
}
