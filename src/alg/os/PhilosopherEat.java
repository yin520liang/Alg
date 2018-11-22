/**
 * 
 */
package alg.os;

/**
 * 哲学家就餐问题
 * @title PhilosopherEat
 */
public class PhilosopherEat {
	
	public static void main(String[] args) throws InterruptedException {
		int n = 5;
		Philosopher[] philosophers = new Philosopher[n];
		Fork fork = new Fork(n);
		for(int i = 0; i < n; ++ i) {
			philosophers[i] = new Philosopher(i, fork);
			philosophers[i].start();
		}
		// wait for 10 seconds
		Thread.sleep(10 * 1000);
		// stop all the threads
		for(int i = 0; i < n; ++ i) {
			philosophers[i].tryStop();
			philosophers[i].join();
		}
	}
	
	static class Fork {		
		private int n;
		private boolean[] state;
		
		Fork(int n) {
			this.n = n;
			state = new boolean[n];
		}
		
		public synchronized void getFork(int pid) throws InterruptedException {
			while(state[left(pid)] || state[right(pid)])
				this.wait();
			state[left(pid)] = true;
			state[right(pid)] = true;
		}
		
		public synchronized void putFork(int pid) {
			state[left(pid)] = false;
			state[right(pid)] = false;
			this.notifyAll();
		}
		
		private int left(int i) {
			return i;
		}
		
		private int right(int i) {
			return ((i + 1) == n) ? 0 : i + 1;
		}
		
	}
	
	
	public static void echo(String msg) {
		System.out.println(msg);
	}
	
	
	/**
	 *  哲学家线程
	 * @title Philosopher
	 */
	static class Philosopher extends Thread {
		int id;
		boolean running;
		Fork fork;
		
		Philosopher(int id, Fork fork) {
			this.id = id;
			this.fork = fork;
		}
		
		public void run() {
			running = true;
			while(running) {			
				try {
					think();
					fork.getFork(id);
					eating();
					fork.putFork(id);
					think();
				} catch (InterruptedException e) {
					echo("Philosopher " + id + " is interrupted");
					return;
				}
				
			}
		}
		
		public void eating() throws InterruptedException {
			echo("" + id + ": I am eating...");
			Thread.sleep(1 * 1000);
		}
		
		public void think() throws InterruptedException {
			echo("" + id + ": I am thinking...");
			Thread.sleep(1 * 1000);
		}
		
		public void tryStop() {
			running = false;
		}
	}

}
