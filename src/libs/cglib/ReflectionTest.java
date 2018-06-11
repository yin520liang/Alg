/**
 * 
 */
package libs.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title ReflectionTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年5月8日
 */
public class ReflectionTest {

	/**
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年5月8日 
	 */
	public static void main(String[] args) {
		IA a = (IA) Proxy.newProxyInstance(IA.class.getClassLoader(), new Class[]{IA.class}, new InvocationHandler(){
			@Override
			public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
				if(method.getName().equals("execute")) {
					System.out.println("This is the implementation in proxy inner::invoke");
				}
				return null;
			}
			
		});
		a.execute();
	}

}
