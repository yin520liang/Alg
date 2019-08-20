/**
 * 
 */
package test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @title
 * @description
 *
 */
public class TestMain {

	public static void main(String[] args) throws ParseException, MalformedURLException, UnsupportedEncodingException {
//		Scanner scan = new Scanner(System.in);
//		int input = 0;
//		while(input != -1) {
//			input = scan.nextInt();
//			System.out.println("read in " + input);
//		}
//		scan.close();
		
		String url = "http://a.b.com/aa bb cccc";
		System.out.println(URLEncoder.encode(url, "ISO-8859-1"));
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
