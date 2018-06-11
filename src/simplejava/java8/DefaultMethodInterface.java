/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月14日下午2:41:52
 */
public interface DefaultMethodInterface {

	default void sayHello() {
		System.out.println("hello");
	}

	interface MyInterface1 extends DefaultMethodInterface {
//		default void sayHello() {
//			System.out.println("hello");
//		}
	}

	interface MyInterface2 extends DefaultMethodInterface {
		default void sayHello() {
			System.out.println("hello 2");
		}
	}

}
