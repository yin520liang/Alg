/**
 * 
 */
package simplejava;

/**
 * @title
 * @description
 */
public class OverridingAndOverloading {
	public static void main(String[] args) {
		B ab = new B();
		ab.sum(1, 2L);
	}
}

class A {
	long sum(long a, long b) {
		System.out.println("A");
		return a + b;
	}

	int sum(int a, int b) {
		System.out.println("A");
		return a + b;
	}
}

class B extends A {
	long sum(int a, long b) {
		System.out.println("B");
		return a+b;
	}
}
