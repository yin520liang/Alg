/**
 * 
 */
package simplejava.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @Title IntChannelTest
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年2月23日
 */
public class IntChannelTest {

	public static void main(String[] args) throws FileNotFoundException {
		IntBuffer intBuffer = IntBuffer.allocate(2);
        intBuffer.put(1);
        intBuffer.put(2);
        intBuffer.flip();
        System.err.println(intBuffer.get());
        System.err.println("position: " + intBuffer.position());
        intBuffer.mark();
        System.err.println(intBuffer.get());

        System.err.println("position: " + intBuffer.position());
        intBuffer.reset();
        System.err.println("position: " + intBuffer.position());
        System.err.println(intBuffer.get());
        
        FileChannel ch = new RandomAccessFile("", "r").getChannel();

	}

}
