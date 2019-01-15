/**
 * 
 */
package simplejava;

/**
 * @Title WhereIsMainTest
 * @Description 
 */
public class WhereIsMainTest {

	public static void main(String[] args) {
		String a = null;
		StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			if ("main".equals(stackTraceElement.getMethodName())) {
				a =  stackTraceElement.getClassName();
				break;
			}
		}
		System.out.println(a);
	}

}
