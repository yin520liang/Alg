/**
 * 
 */
package simplejava.nio.selector;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * @title ThreadPool
 * @author lvzhaoyang
 * @date 2018年11月13日
 */
public class ThreadPool {
	
	private List<WorkerThread> idle = new LinkedList<> ( );
	
	public ThreadPool(int size) {
		for(int i = 0; i < size; ++i) {
			WorkerThread th = new WorkerThread(this);
			th.setName("worker-" + i);
			th.start();
			idle.add(th);
		}
	}
	
	public WorkerThread getWorker() {
		WorkerThread t = null;
		synchronized(idle) {
			if(idle.size() > 0)
				t = idle.remove(0);
		}
		return t;
	}
	
	public void returnWorker(WorkerThread t) {
		if(t != null) {
			synchronized(idle) {
				idle.add(t);
			}
		}
	}

}


class WorkerThread extends Thread {
	private ByteBuffer buffer = ByteBuffer.allocate(1024); 
	private ThreadPool pool;	
	private SelectionKey key;
	
	public WorkerThread(ThreadPool pool) {
		this.pool = pool;
	}
	
	public synchronized void run() {
		while(true) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(key == null)
				continue;
			System.out.println(this.getName() + " has been awaken.");
			try {
				drainChannel(key);
			} catch (IOException e) {
				System.out.println("Caught '" + e + "' closing channel"); 
				// Close channel and nudge selector 
				try { 
					key.channel().close(); 
				} catch (IOException ex) { 
					ex.printStackTrace(); 
				}
				key.selector().wakeup();
			}
			key = null;
			pool.returnWorker(this);
		}
	}
	
	public synchronized void serviceChannel(SelectionKey key) {
		this.key = key;
		key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
		this.notify();  // awake this thread
	}


	private void drainChannel(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		StringBuilder str = new StringBuilder();
		int count;
		while( (count = channel.read(buffer)) > 0) {
			buffer.flip();
			while(buffer.hasRemaining()) {
				str.append((char)buffer.get());
			}
			System.out.println(this.getName() + ":" + str.toString());
			buffer.clear();
		}
		if(count < 0) {
			channel.close(); // EOF
			return;
		}	
		key.interestOps(key.interestOps() & SelectionKey.OP_READ);
		key.selector().wakeup();
		
	}
	
}
