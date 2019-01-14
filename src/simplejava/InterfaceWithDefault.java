/**
 * 
 */
package simplejava;

/**
 * @title InterfaceWithDefault
 * @author lvzhaoyang
 * @date 2018年12月12日
 */
public interface InterfaceWithDefault {

	default String call(String s) {
		return s;
	}
}
