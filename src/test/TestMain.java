/**
 * 
 */
package test;

import java.text.ParseException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @title
 * @description
 *
 */
public class TestMain {

	public static void main(String[] args) throws ParseException {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		while(input != -1) {
			input = scan.nextInt();
			System.out.println("read in " + input);
		}
		scan.close();
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
