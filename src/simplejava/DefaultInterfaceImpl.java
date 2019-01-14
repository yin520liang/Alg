/**
 * 
 */
package simplejava;

/**
 * @title DefaultInterfaceImpl
 * @author lvzhaoyang
 * @date 2018年12月12日
 */
public class DefaultInterfaceImpl implements InterfaceWithDefault {
	
	public static void main(String[] args) {
		DefaultInterfaceImpl impl = new DefaultInterfaceImpl();
		System.out.println( impl.call("123") );
	}

}
