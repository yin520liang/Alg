package simplejava.concurrent;

import java.util.concurrent.Semaphore;
/**
 *  指定n个线程，按顺序打印0~100的数字，效果如下：<p>
 *  Thread-0: 0, <br>
 *  Thread-1: 1, <br>
 *  Thread-n: n, <br>
 *  Thread-0: n+1, 
 * @author yang
 *
 */
public class SequentialPrint {
	
	private static int counter = 0;
	private static int MAX = 100;
	private static int n = 3;
	
	
	public static void main(String[] args) {
		try {
			useSynchronized();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void useSemophore() throws InterruptedException {	
		Thread[] ths = new Thread[n];
		Semaphore[] sem = new Semaphore[n];
		for(int i = 0; i < n; ++ i) {
			sem[i] = new Semaphore(1);
			if(i != n - 1) sem[i].acquire();
		}
		for(int i = 0; i < n; ++ i) {
			final Semaphore prev  = (i == 0) ? sem[n - 1] : sem[i - 1];
			final Semaphore next = sem[i];
			final int curIndex = i;
			ths[i] = new Thread("thread-" + i) {
				public void run() {
					while(true) {
						try {
							prev.acquire();
							System.out.println(this.getName() + " : " + counter ++);
							if(counter > MAX) {
								stopAll(ths, curIndex);
								break;
							}
							next.release();
						} catch (InterruptedException e) {
							break;
						}
					}
				}

			};
			
			ths[i].start();
		}

	}
	
	
	private static void stopAll(Thread[] ths, int curIndex) {
		for(int j =0; j < n; ++ j) {
			if(j != curIndex) {
				System.out.println("Stop " + ths[j].getName());
				ths[j].interrupt();
			}
		}					
	}
	
	
	
	public static void useSynchronized() throws InterruptedException {
		Thread[] ths = new Thread[n];
		boolean[] flag = new boolean[n];
		flag[0] = true;
		for(int i = 0; i < n; ++ i) {
			final int curIndex = i;
			ths[i] = new Thread("thread-" + i) {
				public void run() {
					synchronized(flag) {
						while(true) {
							while(!flag[curIndex]) {
								try {
									flag.wait();
								} catch (InterruptedException e) {
									System.out.println(this.getName() + " has been interrupted.");
									return;
								}
							}
							System.out.println(counter ++);
							if(counter > MAX) {
								stopAll(ths, curIndex);
								return;
							}
							flag[curIndex] = false;
							flag[next(curIndex)] = true;
							flag.notifyAll();
						}
					}
				}

				private int next(int i) {
					return (i + 1 >= n) ? 0 : i + 1;
				}

			};
			
			ths[i].start();
		}
	}
	
	
}
