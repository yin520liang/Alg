package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 * 
 * @author MZCN501A0145
 *
 */
public class FileChannelTest {

	public static void main(String[] args) throws IOException {
//		fileChannelCopy("wiki-horspool.txt", "wiki-horspool-2.txt");
//		System.out.println(returnIncrement(1));
		fileChannelCopy2("wiki-horspool.txt", "wiki-horspool-2.txt");
	}
	
	/**
	 * test the use of channel and buffer
	 * @throws IOException
	 */
	public static void simpleTest() throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("file_channel.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		// capacity: 48 bytes
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead);
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}
			// next read op
			buf.clear();
			bytesRead = inChannel.read(buf);

		}
		buf.clear();
		buf = null;
		inChannel.close();
		aFile.close();	
	}
	
	/**
	 * implement file copy using channel
	 * @param fp1
	 * @param fp2
	 * @throws IOException
	 */
	public static void fileChannelCopy(String fp1, String fp2) throws IOException {
		// check with fp1, fp2: path syntax validation, file exist, can read/write
		// copy from f1 to f2
		RandomAccessFile f1 = new RandomAccessFile(fp1, "r");
		RandomAccessFile f2 = new RandomAccessFile(fp2, "rw");
		FileChannel fromChannel = f1.getChannel();
		FileChannel toChannel = f2.getChannel();
		
		int size = 1 * 1024; // 1k
		ByteBuffer buff =	ByteBuffer.allocate(size);
		int bytesRead;
		int bytesWrite;
		while((bytesRead = fromChannel.read(buff)) != -1) {
			// log
			System.out.println("read >>>" + bytesRead + " bytes.");
			buff.flip();
			bytesWrite = toChannel.write(buff);
			// log
			System.out.println("write <<<" + bytesWrite + " bytes.");
			buff.compact();
		}
		buff.clear();
		buff = null;
		toChannel.close();
		fromChannel.close();
		f2.close();
		f1.close();
	}
	
	public static void fileChannelCopy2(String fp1, String fp2) throws IOException {
		// check with fp1, fp2: path syntax validation, file exist, can read/write
		// copy from f1 to f2
		RandomAccessFile f1 = new RandomAccessFile(fp1, "r");
		RandomAccessFile f2 = new RandomAccessFile(fp2, "rw");
		FileChannel fromChannel = f1.getChannel();
		FileChannel toChannel = f2.getChannel();
		
		int pos = 0;
		int count = 4 * 1024; // transfer unit - 4k
		long bytesTransfer = 0;
		do {
			bytesTransfer = fromChannel.transferTo(pos, count, toChannel);
			pos += bytesTransfer;
		}while(bytesTransfer > 0);	
		
		toChannel.close();
		fromChannel.close();
		f2.close();
		f1.close();
	}

	
	/**
	 * test whether return op is also decsendent to i++
	 */
	public static int returnIncrement(int i) {
		return i++;
	}
}
