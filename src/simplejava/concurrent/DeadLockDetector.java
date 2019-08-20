package simplejava.concurrent;

import java.lang.management.ThreadMXBean;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * 利用java提供的死锁检测工具检测死锁并解除的示例
 * @author yang
 *
 */
@Slf4j
public class DeadLockDetector extends Thread {
	private ThreadMXBean threadMXBean;
	private volatile boolean running = false;
	
	@Override
	public void run() {
		running = true;
		int next = 0;
		while(running) {
			long[] deadlockThs = findDeadLockThreads();
			if(deadlockThs != null) {
				// 选取任意一个线程发送中断信号
				int target = ++next % deadlockThs.length;
				Thread t = getThread(target);
				if(t != null && interruptThread(t)) {
					log.info("Successfully interrupt thread[%d, %s]", t.getId(), t.getName());
				}
			} else {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					log.warn("Interrupted, exit");
					running = false;
					break;
				}
			}
		}

	}

	/**
	 * 发送中断信号
	 */
	private boolean interruptThread(Thread t) {
		t.interrupt();
		return true;
	}

	private Thread getThread(long id) {
		for(Thread t : Thread.getAllStackTraces().keySet()) {
			if(t.getId() == id)
				return t;
		}
		return null;
	}

	private long[] findDeadLockThreads() {
		return threadMXBean.findDeadlockedThreads();
	}


}
