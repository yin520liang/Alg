/**
 * 
 */
package libs.cglib;

/**
 * @Title IA
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月8日
 */
public interface IA {
	default void execute() {
		System.out.println("default implementation of IA::execute()");
	}
}
