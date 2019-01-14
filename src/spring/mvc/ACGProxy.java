/**
 * 
 */
package spring.mvc;

import java.lang.reflect.Method;

import libs.objenesis.Person;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @Title ACGProxy
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年5月3日
 */
public class ACGProxy implements MethodInterceptor {

	public A createProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(A.class);
		enhancer.setCallback(this);
		return (A) enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		// 过滤不需要该业务的方法
		if ("execute".equals(method.getName())) {
			System.out.println("before...");
			Object result = proxy.invokeSuper(obj, args);
			System.out.println("after...");
			return result;
		}
		// 如果不需要增强直接执行原方法
		return proxy.invokeSuper(proxy, args);
	}

}
