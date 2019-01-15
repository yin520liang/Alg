/**
 * 
 */
package simplejava.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

/**
 * @title
 * @description
 */
public class MethodHandleMap {
	private static final MethodType typeCallback = 
			MethodType.methodType(Object.class, Object.class, int.class);

	public static void forEach(Object[] array, MethodHandle handle) throws Throwable {
		for (int i = 0, len = array.length; i < len; i++) {
			handle.invoke(array[i], i);
		}
	}

	public static Object[] map(Object[] array, MethodHandle handle) throws Throwable {
		Object[] result = new Object[array.length];
		for (int i = 0, len = array.length; i < len; i++) {
			result[i] = handle.invoke(array[i], i);
		}
		return result;
	}

	public static Object reduce(Object[] array, Object initalValue, MethodHandle handle) throws Throwable {
		Object result = initalValue;
		for (int i = 0, len = array.length; i < len; i++) {
			result = handle.invoke(result, array[i]);
		}
		return result;
	}
}
