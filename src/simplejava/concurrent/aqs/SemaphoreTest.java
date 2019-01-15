/**
 * 
 */
package simplejava.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Title SemaphoreTest
 * @Description
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semp = new Semaphore(5);
		
		for (int index = 0; index < 20; index++) {
			final int No = index;
			exec.execute(() -> {
				try {
					semp.acquire();
					System.out.println("Accessing: " + No);
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					
				} finally {
					semp.release();
				}
			});
			
		}
		exec.shutdown();

	}

}
