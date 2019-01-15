/**
 * 
 */
package spring.mvc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title AProxy
 * @Description
 */
public class AProxy implements InvocationHandler {

	private A target;

	public AProxy(A a) {
		target = a;
	}

	public ExInterface createExProxy() {
		return (ExInterface) Proxy.newProxyInstance(target.getClass()
				.getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args)
			throws Throwable {
		if("execute".equals(method.getName())) {
			System.out.println("before...");
			Object res = method.invoke(target, args);
			System.out.println("after...");
			return res;
		}
		return method.invoke(target, args);
	}

}
