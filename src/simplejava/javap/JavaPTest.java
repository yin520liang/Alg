/**
 * 
 */
package simplejava.javap;

/**
 * @title JavaPTest
 * @author lvzhaoyang
 * @date 2018年8月7日
 */
public class JavaPTest {

	public static void main(String[] args) {

	}

	public static String contactWithStringNoLoopNoPara() {
		String s = "This is " + " my " + "first JAVAP test code.";
		return s;
	}

	public static String contactWithStringNoLoop(int count) {
		String s = "This is " + " my " + count + "th JAVAP test code.";
		return s;
	}

	public static String contactWithStringLoop(int count) {
		String s = "";
		for (int i = 0; i < count; i++) {
			s += i;
		}
		return s;
	}

	public static String contactWithStringBufferLoop(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(i);
		}
		return sb.toString();
	}

}
