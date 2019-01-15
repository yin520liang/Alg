/**
 * 
 */
package simplejava.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @title
 * @description
 */
public class MethodHandleTest {

	public void normalMethod(String arg1, int arg2, int[] arg3) {
	}

	public void toBeSpreaded(String arg1, int arg2, int arg3, int arg4) {
	}

	public void asSpreader() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest.class, "toBeSpreaded",
				MethodType.methodType(void.class, String.class, int.class, int.class, int.class));
		mh = mh.asSpreader(int[].class, 3);
		mh.invoke(this, "Hello", new int[] { 3, 4, 5 });
	}

	public void varargsMethod(String arg1, int... args) {
	}

	public void asFixedArity() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest.class, "varargsMethod",
				MethodType.methodType(void.class, String.class, int[].class));
		mh = mh.asFixedArity();
		mh.invoke(this, "Hello", new int[] { 2, 4 });
	}

	public void asVarargsCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest.class, "normalMethod",
				MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asVarargsCollector(int[].class);
		mh.invoke(this, "Hello", 2, 3, 4, 5);
	}

	public void asCollector() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(MethodHandleTest.class, "normalMethod",
				MethodType.methodType(void.class, String.class, int.class, int[].class));
		mh = mh.asCollector(int[].class, 2);
		mh.invoke(this, "Hello", 2, 3, 4);
	}

	public void invokeExact() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType type = MethodType.methodType(String.class, int.class, int.class);
		MethodHandle mh = lookup.findVirtual(String.class, "substring", type);
		String str = (String) mh.invokeExact("Hello World", 1, 3);
		System.out.println(str); // 'el'
	}

	public void lookupMethod() throws NoSuchMethodException, IllegalAccessException {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		// 构造方法
		lookup.findConstructor(String.class, MethodType.methodType(void.class, byte[].class));
		// String.substring
		lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class, int.class));
		// String.format
		lookup.findStatic(String.class, "format", MethodType.methodType(String.class, String.class, Object[].class));
		// lookup.findSpecial(refc, name, type, specialCaller);

	}

	public void lookupFieldAccessor() throws NoSuchFieldException, IllegalAccessException {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		lookup.findGetter(Sample.class, "name", String.class);
		lookup.findSetter(Sample.class, "name", String.class);
		lookup.findStaticGetter(Sample.class, "value", int.class);
		lookup.findStaticSetter(Sample.class, "value", int.class);
	}


	public static void main(String[] args) {
		MethodHandleTest test = new MethodHandleTest();
		try {
			test.asSpreader();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
