/**
 * 
 */
package simplejava.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * @title PhantomReferenceTest
 * @author lvzhaoyang
 * @date 2018年10月31日
 */
public class PhantomReferenceTest {
	
	private static ReferenceQueue <Obj> refQueue = new ReferenceQueue<>();

	public static void main(String[] args) throws InterruptedException {		
		Obj o = new Obj();
		Obj a = new Obj();
		
		PhantomReference<Obj> ref1 = new PhantomReference<>(o, refQueue);
		PhantomReference<Obj> ref2 = new PhantomReference<>(a, refQueue);
		CleanRefCheck t1 = new CleanRefCheck();
		t1.start();
		
		o = null;
		a = null;
		System.out.println("First gc"); // call finalize method
		System.gc();
		TimeUnit.SECONDS.sleep(1);
		
		System.out.println("Second gc"); // release object, which will be detected by check thread
		System.gc();
		
		TimeUnit.SECONDS.sleep(10); // wait 10 more seconds for Check thread
		t1.setStop(); // stop check thread normally

	}
	
	
	static class Obj {
		
		public void finalize() throws Throwable{
			System.out.println("Call Obj::finalize() ");
			super.finalize();
		}
	}

	static class CleanRefCheck extends Thread {
		private boolean running;
		
		private Reference<?> target;
		
		private String varName;
		
		CleanRefCheck(Reference<?> target, String varName) {
			this.target = target;
			this.varName = varName;
		}
		
		CleanRefCheck() {}
		
		
		public void run() {
			running = true;
			while(running) {			
				Reference<?> item = refQueue.poll();
				if(item != null) {
					System.out.println("ggggg");
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					running = false;
				}
			}
		}
		
		public void setStop() {
			running = false;
		}
	}
}
