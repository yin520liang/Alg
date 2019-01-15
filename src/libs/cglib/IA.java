/**
 * 
 */
package libs.cglib;

/**
 * @Title IA
 * @Description 
 */
public interface IA {
	default void execute() {
		System.out.println("default implementation of IA::execute()");
	}
}
