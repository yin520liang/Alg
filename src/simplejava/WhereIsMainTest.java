/**
 * 
 */
package simplejava;

/**
 * @Title WhereIsMainTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月9日
 */
public class WhereIsMainTest {

	/**
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年5月9日 
	 */
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
