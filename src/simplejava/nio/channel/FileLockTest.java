/**
 * 
 */
package simplejava.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * @title FileLockTest
 */
public class FileLockTest {
	
	private static boolean writer = false;
	
	private static final int SIZEOF_INT = 4; 
	
	private static final int INDEX_START = 0; 
	
	private static final int INDEX_COUNT = 10; 
	
	private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
	
	private static ByteBuffer buf = ByteBuffer.allocate(INDEX_SIZE);
	private static IntBuffer ibuf = buf.asIntBuffer();
	
	private static Random rand = new Random();
	
	private static boolean running = false;
	
	private static int idxval = 1;


	public static void main(String[] args) throws IOException {
		RandomAccessFile f = null;
		FileChannel fc = null;
		writer = args[0].equals("-w");
		try {
			f = new RandomAccessFile("D:\\file-lock.txt", writer? "rw" : "r");
			fc = f.getChannel();
			if(writer) {
				doUpdate(fc);
			} else {
				doQuery(fc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fc != null)
				fc.close();
			if(f != null)
				f.close();
		}
	}

	/**
	 * 更新
	 * @throws IOException 
	 */
	private static void doUpdate(FileChannel fc) throws IOException {
		running = true;
		while(running) {
			FileLock lock = null;
			try {
				while((lock = fc.tryLock(INDEX_START, INDEX_SIZE, true)) == null) {
				}
				ibuf.clear();
				for (int i = 0; i < INDEX_COUNT; i++) { 
					idxval++; 
					System.out.println("Updating index " + i + "=" + idxval); 
					ibuf.put (idxval); 
					// Pretend that this is really hard work 
					Thread.sleep (500); 
				}
				// leaves position and limit correct for whole buffer 
				buf.clear( );
				fc.write (buf, INDEX_START);
				Thread.sleep (rand.nextInt (2000) + 500);
			} catch(InterruptedException e) {
				running = false;
			} finally {
				if(lock != null)
					lock.release();
			}	
		}
		System.out.println("end");
		
	}

	/**
	 * 	只读
	 */
	private static void doQuery(FileChannel fc) throws IOException {
		running = true;
		while(running) {
			FileLock lock = null; 
			try {
				while((lock = fc.tryLock(INDEX_START, INDEX_SIZE, true)) == null) {
				}
				for(int i = 0; i < 20; ++i) { // read for 20 times
					int index = rand.nextInt(INDEX_COUNT); // pick an element randomly
					int position = INDEX_START + SIZEOF_INT * index;
					buf.clear();
					int bytes = fc.read(buf, position);
					System.out.printf("--- reader --->: %d bytes read \n", bytes);
					int a = ibuf.get(index);
					System.out.printf("--- reader --->: entry %d = %d \n", index, a);
					Thread.sleep (100);
				}
				Thread.sleep(rand.nextInt(3000) + 500); // wait for a while
			} catch(InterruptedException e) {
				running = false;
			} finally {
				if(lock != null)
					lock.release();
			}		
		}
		System.out.println("end");
	}


}
