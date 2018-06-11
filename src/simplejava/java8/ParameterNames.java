/**
 * 
 */
package simplejava.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @title
 * @description
 * @author lvzhaoyang
 * @date 2017年9月15日上午11:42:23
 */
public class ParameterNames {
	
	public static void main(String[] args) throws Exception {
		Method method = ParameterNames.class.getMethod("main", String[].class);
		for (final Parameter parameter : method.getParameters()) {
			System.out.println("Parameter: " + parameter.getName());
		}
		
	}
	
}
