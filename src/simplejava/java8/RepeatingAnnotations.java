/**
 * 
 */
package simplejava.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月13日下午5:33:43
 */
public class RepeatingAnnotations {
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Filters {
		Filter[] value();
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(Filters.class)
	public @interface Filter {
		String value();
	};

	@Filter("filter1")
	@Filter("filter2")
	public interface Filterable {
	}

	public static void main(String[] args) {
		for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
			System.out.println(filter.value());
		}
		// --编译错误
		// Filter filters = Filterable.class.getAnnotation(Filter.class);
		// System.out.println(filters.value());
		Filters filters = Filterable.class.getAnnotation(Filters.class);
		System.out.println(filters.value());
	}
}

class AnotherImpl {
	@interface A {
		B[] values();
	}

	@interface B {
		String value();
	}
	
	@A(values = {@B("1"), @B("2")})
	interface TestInterface {		
	}
}
