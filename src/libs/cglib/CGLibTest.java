/**
 * 
 */
package libs.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import libs.objenesis.Person;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

/**
 * @Title CGLibTest
 * @Description
 * @Author lvzhaoyang
 * @Date 2018年2月28日
 */
public class CGLibTest {

	public static void main(String[] args) throws Exception {
		reflect();
//		proxy();
		
	}
	
	public static void proxy() {
		// 代理Person类
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Person.class);
		enhancer.setCallback(new MethodInterceptor(){
			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				if("getName".equals(method.getName())) {
					return "Hello, " + proxy.invokeSuper(obj, args);
				}
				if("toString".equals(method.getName())) {
					return "InnerClass: " + proxy.invokeSuper(obj, args);
				}

				return proxy.invokeSuper(obj, args);
			}
			
		});
		// 被代理对象无默认构造器时
		// .create()生成的是一个继承了Person类的匿名类，包含一个MethodInterceptor对象，
		// 对匿名类对象所有方法(包括从Object继承的toString, hashCode等方法)的调用会被转发到MethodInterceptor对象的intercept方法
		// wait()等方法不会被转发，因为final方法无法被继承和重写
		Person p = (Person)enhancer.create(new Class[]{String.class}, new Object[]{"Peter"});
		System.out.println(p.getName());
		System.out.println(p.toString());

	}
	

	public static void reflect() throws InvocationTargetException {
		// 通过cglib创建实例
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		/*
		 * 创建fastClass实例,底层将根据Person.class字节码信息创建一个新的class:
		 * objenesis.Person$$FastClassByCGLIB$$8bdfd246,
		 * 此FastClassByCGLIB类被加载后,将会被缓存起来
		 * 缓存为一个二级map,key依次为classLoader/原始的类全名(比如com.test.TestObject)
		 */
		FastClass fastClass = FastClass.create(classLoader, Person.class);
		// -----------default constructor----------
		FastConstructor constructor = fastClass
				.getConstructor(new Class[] { String.class });
		Person p = (Person) constructor.newInstance(new Object[] { "123" });
		System.out.println(p.getName());
		
	}

}
