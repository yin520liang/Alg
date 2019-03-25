package simplejava.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LockTimeoutTest {	
	
	public static void main(String[] args) throws InterruptedException {
//		Lock[] lck = new Lock[3];
//		for(int i = 0; i < lck.length; ++ i)
//			lck[i] = new ReentrantLock();
		Lock lck1 = new ReentrantLock();
		Lock lck2 = new ReentrantLock();
		Lock lck3 = new ReentrantLock();
		
		Lock[] array1 = {lck1, lck2, lck3};
		Lock[] array2 = {lck1, lck3, lck2};
		
		Thread other1 = new Thread(new TimedTask(array1), "other-1");
		Thread other2 = new Thread(new TimedTask(array2), "other-2");
		other1.start();
		other2.start();
		
		TimeUnit.SECONDS.sleep(10);
		other1.interrupt();
		other2.interrupt();
	}
		
	
	static class TimedTask implements Runnable {
		
		private Lock[] locks;
		private int ownedIndex = -1;
		
		TimedTask(Lock[] locks) {
			this.locks = locks;
		}

		@Override
		public void run() {
			retry:
			for(;;) {
				final int len = locks.length;
				for(int i = 0; i < len; ++ i) {
					if(locks[i] == null) continue;
					try {
						if(! locks[i].tryLock(500, TimeUnit.MILLISECONDS)) {
							releaseOwned();
							TimeUnit.MILLISECONDS.sleep(500);
							continue retry;
						} else {
							log.info("Accessed lock-{}", i);
							ownedIndex = i;
						}
					} catch (InterruptedException e) {
						releaseOwned();
						return;
					}
				}
				break;
			}
			
			log.info("do some work");
			try {
				// do real work
			} finally {
				releaseOwned();
			}
		}

		private void releaseOwned() {
			for(int i = 0; i <= ownedIndex; ++ i) {
				if(locks[i] != null) {
					locks[i].unlock();
					log.info("Release lock-{}", i);
				}
			}			
		}
		
	}

}
