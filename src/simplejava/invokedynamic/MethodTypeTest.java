/**
 * 
 */
package simplejava.invokedynamic;

import java.lang.invoke.MethodType;

/**
 * @title
 * @description 
 */
public class MethodTypeTest {

	public void generateMethodTypes() {
	    //String.length()
	    MethodType mt1 = MethodType.methodType(int.class);
	    //String.concat(String str)
	    MethodType mt2 = MethodType.methodType(String.class, String.class);
	    //String.getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
	    MethodType mt3 = MethodType.methodType(void.class, int.class, int.class, char[].class, int.class);
	    //String.startsWith(String prefix)
	    MethodType mt4 = MethodType.methodType(boolean.class, mt2);
	}
	
	public static void main(String[] args) {

	}

}
