/**
 * 
 */
package simplejava.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * @Title BufferTest
 * @Description 
 */
public class BufferTest {


	public static void main(String[] args) {
//		byteBuffer();
//		charBuffer();
		byteOrderTest();
		
	}
	
	private static void byteOrderTest() {
		ByteBuffer bb1 = ByteBuffer.allocate(10).order(ByteOrder.BIG_ENDIAN); // hello
		bb1.put((byte) 0);
		bb1.put((byte) 'h');
		bb1.put((byte) 0);
		bb1.put((byte) 'e');
		bb1.put((byte) 0);
		bb1.put((byte) 'l');
		bb1.put((byte) 0);
		bb1.put((byte) 'l');
		bb1.put((byte) 0);
		bb1.put((byte) 'o');
		bb1.flip();
		
		CharBuffer cb1 = bb1.asCharBuffer();
		print(cb1);
		
		ByteBuffer bb2 = ByteBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN); // hello	
		bb2.put((byte) 'h');
		bb2.put((byte) 0);
		bb2.put((byte) 'e');
		bb2.put((byte) 0);
		bb2.put((byte) 'l');
		bb2.put((byte) 0);
		bb2.put((byte) 'l');
		bb2.put((byte) 0);
		bb2.put((byte) 'o');
		bb2.put((byte) 0);
		bb2.flip();
		
		CharBuffer cb2 = bb2.asCharBuffer();
		print(cb2);
		
	}
	
	

	private static void charBuffer() {
		// charBuffer wrap -> readonly
		CharBuffer cb = CharBuffer.allocate(8);
		cb.put("Hello");
		cb.flip();
		print(cb);
		CharBuffer cb2 = cb.duplicate();
		cb2.put(0, 'M');	
		print(cb);
		
		CharBuffer cb3 = cb.slice();
		cb3.put(1, 'a');
		positionInfo("cb3", cb3);
		print(cb);
	}


	private static void positionInfo(String name, Buffer b) {
		System.out.println("Position Info:");
		System.out.printf("\tposition: %d, limit: %d, capacity: %d \n", 
				b.position(), b.limit(), b.capacity());
	}

	private static void print(CharBuffer cb) {
		char[] cs = new char[cb.remaining()];
		cb.mark();
		cb.get(cs);
		cb.reset();
		System.out.println(new String(cs));
	}



	private static void byteBuffer() {
		byte[] b = new byte[10];
		ByteBuffer buf = ByteBuffer.wrap(b);
		// put 'Hello'
		buf.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o');
		print(buf);
		
		// change to 'Mellow'
		buf.put(0, (byte) 'M').put((byte) 'w');
		print(buf);
		
		// release
		buf.flip();		// mark = -1, limit = position, position = 0;
		int count = buf.remaining(); // limit - position
		byte[] r = new byte[count];
		for(int i = 0; i < count; ++i) {
			r[i] = buf.get();
		}
//		buf.clear(); // mark = -1, position = 0, limit = capacity
		
		// compact
		buf.flip();
		buf.get(); // 'M'
		buf.get(); //'e'
		buf.compact();
		print(buf);
		
		// 填充大数组：需要制定长度
		byte[] largeArray = new byte[1000];
		System.out.println(buf.remaining());
		buf.get(largeArray, 0, buf.remaining());
		System.out.println(buf.remaining());
		
	}
	
	private static void print(ByteBuffer buf) {
		int position = buf.position();
		StringBuilder b = new StringBuilder(buf.remaining());
		for(int i = 0; i < position; ++i) {
			b.append((char) buf.get(i));
		}
		System.out.println(b.toString());
	}

}
