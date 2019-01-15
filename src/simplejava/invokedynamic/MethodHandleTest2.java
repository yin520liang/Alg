/**
 * 
 */
package simplejava.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @title
 * @description
 */
public class MethodHandleTest2 {
	public void doSomething() {
		System.out.println("WORK");
	}

	public void useMethodHandleProxy() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest2.class, 
				"doSomething", MethodType.methodType(void.class));
		mh = mh.bindTo(this);
		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		new Thread(runnable).start();
	}


	public static void main(String[] args) {
		MethodHandleTest2 mht = new MethodHandleTest2();
		try {
			mht.useMethodHandleProxy();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
