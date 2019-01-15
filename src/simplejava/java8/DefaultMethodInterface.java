/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
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
