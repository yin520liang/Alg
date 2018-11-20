/**
 * 
 */
package simplejava.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.ref.ReferenceQueue;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * @title MappedByteBufferTest
 * @author lvzhaoyang
 * @date 2018年10月30日
 */
public class MappedByteBufferTest {

//	private static ReferenceQueue<MappedByteBuffer> refQueue = new ReferenceQueue<>();

	/**
	 * @author lvzhaoyang
	 * @throws IOException
	 * @date 2018年10月30日
	 */
	public static void main(String[] args) {
//		writeTest();
		combineTogether("wiki-horespool.txt");

	}

	private static void writeTest() {
		String fp = "holy-file.txt";
		RandomAccessFile f = null;
		FileChannel fc = null;
		try {
			f = new RandomAccessFile(fp, "rw");
			fc = f.getChannel();
			// 该对象无法显示关闭或释放（只能等GC或程序运行结束）
			MappedByteBuffer filedata = fc.map(MapMode.READ_WRITE, 0, 5);
			filedata.clear();
			filedata.put("nicai".getBytes());
			filedata.flip(); // 必须
			filedata.force();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fc != null)
					fc.close();
				if (f != null)
					f.close();
			} catch (IOException e) {

			}
		}
	}

	private static void combineTogether(String inFile) {
		String outFile = "MappedOut.txt";
		final String LINE_SEP = "\r\n";
		final String HTTP_OK = "HTTP/1.0 200 OK" + LINE_SEP;
		final String HTTP_404 = "HTTP/1.0 404 NOT FOUND" + LINE_SEP;
		String defaultHeaders = "Content-type: %s" + LINE_SEP + "Server: Tomcat" + LINE_SEP + LINE_SEP;
		ByteBuffer title = ByteBuffer.wrap(Math.random() < 0.5 ? HTTP_OK.getBytes() : HTTP_404.getBytes());
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer[] response = { title, header, null };
		// read in response body
		try {
			FileInputStream fis = new FileInputStream("wiki-horspool.txt");
			FileChannel fc = fis.getChannel();
			MappedByteBuffer body = fc.map(MapMode.READ_ONLY, 0, fc.size());
			response[2] = body;
			header.put(String.format(defaultHeaders, URLConnection.guessContentTypeFromName(inFile)).getBytes());
			header.flip(); // ready to read

			// write response
			FileOutputStream fos = new FileOutputStream(outFile);
			FileChannel outFc = fos.getChannel();
			while (outFc.write(response) > 0)
				;
			System.out.println("Write response to file:" + outFile);
			outFc.close();
			fos.close(); 
			fc.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	public static void clean(final Object buffer) throws Exception {
//		AccessController.doPrivileged(new PrivilegedAction() {
//			public Object run() {
//				try {
//					Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
//					getCleanerMethod.setAccessible(true);
//					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
//					cleaner.clean();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return null;
//			}
//		});
//
//	}

//	static class CheckPhantomRefThread extends Thread {
//
//		boolean running = false;
//
//		public void run() {
//			running = true;
//			while (running) {
//				try {
//					Reference<? extends MappedByteBuffer> obj = refQueue.poll();
//					if (obj != null) {
//						System.out.println("MappedByteBuffer Object has been gc.");
//						running = false;
//					}
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					running = false;
//				}
//			}
//		}
//
//		public void setStop() {
//			running = false;
//		}
//	}

}
