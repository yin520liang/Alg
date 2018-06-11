/**
 * 
 */
package simplejava.invokedynamic;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月19日下午2:21:40
 */
public class CallSiteTest {
	
	public void useConstantCallSite() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		ConstantCallSite callSite = new ConstantCallSite(mh);
		MethodHandle invoker = callSite.dynamicInvoker();
		String result = (String) invoker.invoke("Hello", 2, 3);
		System.out.println(result);
	}

	public void useMutableCallSite() throws Throwable {
		MethodType type = MethodType.methodType(int.class, int.class, int.class);
		MutableCallSite callSite = new MutableCallSite(type);
		MethodHandle invoker = callSite.dynamicInvoker();
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mhMax = lookup.findStatic(Math.class, "max", type);
		MethodHandle mhMin = lookup.findStatic(Math.class, "min", type);
		callSite.setTarget(mhMax);
		int result = (int) invoker.invoke(3, 5); // 值为5
		System.out.println(result);
		callSite.setTarget(mhMin);
		result = (int) invoker.invoke(3, 5); // 值为3
		System.out.println(result);
	}

	/**
	 * @Description
	 * @Author lvzhaoyang
	 * @CreatetionDate 2017年9月19日下午2:21:40
	 *
	 * @Return
	 */
	public static void main(String[] args) {
		CallSiteTest cst = new CallSiteTest();
		try {
			cst.useMutableCallSite();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
