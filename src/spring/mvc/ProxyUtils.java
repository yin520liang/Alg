/**
 * 
 */
package spring.mvc;

import java.lang.reflect.Field;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

/**
 * 在spring环境下，获取被代理对象
 * @title ProxyUtils
 * @author lvzhaoyang
 * @date 2018年12月12日
 */
public class ProxyUtils {
	
	public static Object getTarget(Object proxy) throws Exception {
		if (AopUtils.isAopProxy(proxy)) {			
			return AopUtils.isJdkDynamicProxy(proxy) ?				
							getJdkDynamicProxyTarget(proxy)
						  : getCglibProxyTarget(proxy);
		}
		return proxy;
	}

	/**
	 * @see org.springframework.aop.framework.CglibAopProxy
	 */
	private static Object getCglibProxyTarget(Object proxy) throws Exception {
		Field callback = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
		callback.setAccessible(true);
		Object dynamicAdvisedInterceptor = callback.get(proxy);
		return getTargetByAdvisedSupport(dynamicAdvisedInterceptor);
	}

	/**
	 * @see org.springframework.aop.framework.JdkDynamicAopProxy
	 */
	private static Object getJdkDynamicProxyTarget(Object proxy) throws Exception {
		Field invocationHandler = proxy.getClass().getSuperclass().getDeclaredField("h");
		invocationHandler.setAccessible(true);
		AopProxy aopProxy = (AopProxy) invocationHandler.get(proxy);
		return getTargetByAdvisedSupport(aopProxy);
	}
	
	private static Object getTargetByAdvisedSupport(Object obj) throws Exception {
		Field advised = obj.getClass().getDeclaredField("advised");
		advised.setAccessible(true);
		return ((AdvisedSupport) advised.get(obj)).getTargetSource().getTarget();
	}
	
}
