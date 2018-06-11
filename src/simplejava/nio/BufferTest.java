/**
 * 
 */
package simplejava.nio;

import java.nio.CharBuffer;

/**
 * @Title BufferTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年3月9日
 */
public class BufferTest {


	public static void main(String[] args) {
		// 1. create buffer
//		CharBuffer buff = CharBuffer.allocate(10);
		char[] cs = new char[10];
		CharBuffer buff = CharBuffer.wrap(cs);

	}

}
