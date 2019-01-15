/**
 * 
 */
package simplejava;

/**
 * @title InterfaceWithDefault
 */
public interface InterfaceWithDefault {

	default String call(String s) {
		return s;
	}
}
