/**
 * 
 */
package libs.objenesis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import sun.reflect.ReflectionFactory;

/**
 * @Title ObjenesisTest
 * @Description 
 * @Author lvzhaoyang
 * @Date 2018年2月28日
 */
public class ObjenesisTest {

	/**
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @Description 
	 * @Author lvzhaoyang
	 * @Date 2018年2月28日 
	 */
	public static void main(String[] args) throws Exception {
//		Objenesis objenesis = new ObjenesisStd(true); // or ObjenesisSerializer
//		ObjectInstantiator instantiator = objenesis.getInstantiatorOf(Person.class);
//		Person p = (Person) instantiator.newInstance();
//		System.out.println(p);
		
		
//		ReflectionFactory reflectionFacotry = ReflectionFactory.getReflectionFactory();
//		Constructor<?> newConstructor = reflectionFacotry.newConstructorForSerialization(
//				Person.class, Object.class.getConstructor((Class[]) null));		
//		Person p = (Person) newConstructor.newInstance((Object[]) null);
//		
//		System.out.println(newConstructor.getDeclaringClass().getName());
//		System.out.println(newConstructor.getName());
//		System.out.println(p);
		
		
		Constructor<Person> cons = Person.class.getConstructor(String.class);
		Person p = cons.newInstance("tim");
		System.out.println(p);
//		System.out.println()
	}

}
