package simplejava.concurrent.cas;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimedWaitExample {
	private static boolean ready = false;
	private static Object lock = new Object();

	public static void main(String[] args) {
		Thread other = new Thread(new Waiter(2000));
		other.start();
	}

	static class Waiter implements Runnable {
		private int timout = 3000; // 3s

		Waiter(int timeout) {
			this.timout = timeout;
		}

		@Override
		public void run() {
			final long start = System.currentTimeMillis();
			synchronized (lock) {
				while (!ready) {
					long elapsed = System.currentTimeMillis() - start;
					long rest = timout - elapsed;
					if (rest < 0) { // timeout
						break;
					}
					log.info("Wait time {} ms remained", rest);
					try {
						lock.wait(rest);
					} catch (InterruptedException e) {
						log.warn("Thread has been interrupted, exit");
						return;
					}
				}

				if (ready) {
					doSomething();
				} else {
					onWaitTimeout();
				}
			}

		}

		private void doSomething() {
			// TODO Auto-generated method stub

		}

		private void onWaitTimeout() {
			log.info("Wait timeout, exit");
		}

	}

}
