/**
 * 
 */
package simplejava;

/**
 * @title DefaultInterfaceImpl
 */
public class DefaultInterfaceImpl implements InterfaceWithDefault {
	
	public static void main(String[] args) {
		DefaultInterfaceImpl impl = new DefaultInterfaceImpl();
		System.out.println( impl.call("123") );
	}

}
