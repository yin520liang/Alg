/**
 * 
 */
package simplejava.java8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import simplejava.invokedynamic.MethodHandleTest;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月14日上午10:27:26
 */
public class Lambda {

	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException {
/*		// 示例1
		String[] a = { "1", "2", "3" };
		Arrays.sort(a, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}

		});

		// 示例2
		Callable<String> callable1 = () -> "done";
		Callable<String> callable2 = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "down";
			}
		};

		Object o = (Runnable) () -> {
			System.out.println("running...");
		};

		// 示例3
		List<Person> ps = Arrays.asList(new Person("Lily"), new Person("Bob"));
		Stream<String> names = ps.stream().map((Function<Person, String>) p -> p.getName());
		
		// 示例4
		IntFunction<int[]> array = (n) -> new int[n];
		int[] b = array.apply(10);
	*/	
		// 示例5
		List<String> tbbt = new ArrayList<>(
				Arrays.asList("Howard", "Amy", "Leonard", "Sheldon"));
		tbbt.removeIf(s -> s.contains("a"));
		System.out.println(tbbt.toString());
		
		MethodType mt = MethodType.methodType(String.class, int.class, int.class);
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(String.class, "substring", mt);
		System.out.println(mt.toMethodDescriptorString());
	}

}

class Person {
	String name;

	Person(String s) {
		name = s;
	}

	String getName() {
		return name;
	}
}
