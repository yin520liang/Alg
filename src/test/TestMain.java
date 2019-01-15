/**
 * 
 */
package test;

import java.text.ParseException;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @title
 * @description
 *
 */
public class TestMain {

	public static void main(String[] args) throws ParseException {

		
		B b = new B( );
		System.out.println(b.toString());
		b.printSuper();
	}

	
	static class A {
		public void hello() {
			System.out.println("hello");
		}
	}
	
	static class B extends A {
		public void bye() {
			System.out.println("bye");
		}
		
		public void printSuper() {
			System.out.println(super.toString());
		}
	}
}
