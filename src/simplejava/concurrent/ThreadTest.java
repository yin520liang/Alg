package simplejava.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 多线程程序调试工具
 * 
 * @author yang
 *
 */
public class ThreadTest extends Thread {

	public static void main(String[] args) throws InterruptedException {
		Probe p = new Probe();
		p.start();
		p.join();
	}
}


class Probe extends Thread {
	
	public Probe() {
		super("Thread-probe");
	}
	
	public void run() {
		while(true) {
			Thread[] x = new Thread[100];
			Thread.enumerate(x);
			for(int i = 0; i < 100; ++ i) {
				Thread t = x[i];
				if(t == null)
					break;
				else {
					System.out.println(String.format("%s\t%d\t%s\t%s\n", t.getName(), t.getPriority(), t.isAlive(), t.isDaemon()));
				}
			}
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
			}
		}
	}
}