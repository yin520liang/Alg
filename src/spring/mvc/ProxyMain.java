/**
 * 
 */
package spring.mvc;

/**
 * @Title ProxyMain
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月3日
 */
public class ProxyMain {

	/**
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年5月3日 
	 */
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
