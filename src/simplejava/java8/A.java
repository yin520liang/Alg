/**
 * 
 */
package simplejava.java8;

import simplejava.java8.DefaultMethodInterface.MyInterface1;
import simplejava.java8.DefaultMethodInterface.MyInterface2;

/**
 * @title
 * @description
 */
class A implements MyInterface1, MyInterface2 {

	public static void main(String[] args) {
		A a = new A();
		a.sayHello();
	}
}
