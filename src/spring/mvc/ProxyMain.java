/**
 * 
 */
package spring.mvc;

/**
 * @Title ProxyMain
 * @Description 
 */
public class ProxyMain {

	public static void main(String[] args) {
		A a = new A();
		AProxy proxy = new AProxy(a);
		ExInterface exProxy = proxy.createExProxy();
		exProxy.execute();
		System.out.println(exProxy.getClass().getName());

	}
	
	private static void cglib() {
		ACGProxy proxy = new ACGProxy();
		A a = proxy.createProxy();
		a.execute();
		System.out.println(a.getClass().getName());
	}

}
