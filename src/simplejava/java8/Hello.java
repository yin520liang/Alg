/**
 * 
 */
package simplejava.java8;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月14日上午11:43:46
 */
public class Hello {
	Runnable r1 = () -> { System.out.println(this); };
	Runnable r2 = () -> { System.out.println(toString()); };

	public String toString() {
		return "Hello, world";
	}

	public static void main(String... args) {
		new Hello().r1.run();
		new Hello().r2.run();
	}
}
