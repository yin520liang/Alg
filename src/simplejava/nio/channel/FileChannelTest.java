/**
 * 
 */
package simplejava.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @title FileChannelTest
 * @author lvzhaoyang
 * @date 2018年10月25日
 */
public class FileChannelTest {

	public static void main(String[] args) throws IOException {
//		readFileAsString("wiki-horspool.txt");
//		holyFile("holy-file.txt");
//		positionShare();
//		fileLockTest();
		copyFile("scatter.txt", "scatter2.txt");
	}
	
	private static void readFileAsString(String fp) throws IOException {
		FileInputStream fis = new FileInputStream(fp);
		FileChannel fc = fis.getChannel();
		
		ByteBuffer buf = ByteBuffer.allocate(2 * 1024); // 2k
		CharBuffer cbuf = CharBuffer.allocate(1024); // 1k
		Charset charset = Charset.forName("UTF-8");
		CharsetDecoder decoder = charset.newDecoder();
		
		StringBuilder sb = new StringBuilder();
		while(fc.read(buf) > -1) {
			buf.flip();
			decoder.decode(buf, cbuf, false);
			cbuf.flip();
			if(cbuf.hasRemaining()) {
				sb.append(cbuf.toString());
			}
			cbuf.clear();
			buf.clear();
		}
		fc.close();
		fis.close();
		System.out.println(sb.toString());
	}
	
	
	/**
	 * @throws IOException 
	 * 创建一个带有空洞的文件，但在windows下似乎会被其他字符填充，文件大小会变
	 */
	public static void holyFile(String fp) throws IOException {
		RandomAccessFile f = new RandomAccessFile(fp, "rw");
		FileChannel fc = f.getChannel();
		ByteBuffer buff = ByteBuffer.allocate(10);
		putData(buff, fc, 0); // write 'hello'
		putData(buff, fc, 2000); // write 'hello'
		putData(buff, fc, 20000); // write 'hello'
		fc.close();
		f.close();
	}


	private static void putData(ByteBuffer buff, FileChannel fc, int position) throws IOException {
		buff.clear();
		buff.put("hello".getBytes("UTF8"));
		buff.flip();
		fc.write(buff, position);
	}
	
	/**
	 * 文件的posiiton信息是共享的
	 */
	private static void positionShare() throws IOException {
		RandomAccessFile f = new RandomAccessFile("wiki-horspool.txt", "rw");
		FileChannel fc1 = f.getChannel();
		FileChannel fc2 = f.getChannel();
		System.out.println("position of fc1:" + fc1.position());
		System.out.println("position of fc2:" + fc2.position());
		
		f.seek(1000);
		System.out.println("position of fc1:" + fc1.position());
		System.out.println("position of fc2:" + fc2.position());
		
		fc1.position(2000);
		System.out.println("position of fc1:" + fc1.position());
		System.out.println("position of fc2:" + fc2.position());
		
		fc1.close();
		fc2.close();
		f.close();
	}
	
	private static void fileLockTest() throws IOException {
		RandomAccessFile f = new RandomAccessFile("wiki-horspool.txt", "r");
		FileChannel fc = f.getChannel();
		
		FileLock lock = fc.tryLock(0, 20, true);
		try {		
			while(lock == null) {
				lock =  fc.tryLock(0, 20, true);
			}
			System.out.println("is shared ? " + lock.isShared());
		}finally {
			lock.release();
			fc.close();
			f.close();
		}
	}
	
	
	/**
	 * 仅 filechannel有transferTo和transferFrom的功能；对于许多非阻塞通道，真正传输的数据量很可能和指定的值不一致
	 */
	private static void copyFile(String sourceFile, String destFile) {
		File from = new File(sourceFile);
		File to = new File(destFile);
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(from);
			out = new FileOutputStream(to);
			
			FileChannel fc1 = in.getChannel();
			FileChannel fc2 = out.getChannel();
			fc1.transferTo(0, fc1.size(), fc2);
			fc1.force(true);
			fc2.close();
			fc1.close();		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			} catch (IOException e) {}
		}
		
	}

}
